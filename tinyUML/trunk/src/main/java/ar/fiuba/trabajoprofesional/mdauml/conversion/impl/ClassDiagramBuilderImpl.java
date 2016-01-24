package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.ClassDiagramBuilder;

import ar.fiuba.trabajoprofesional.mdauml.conversion.model.*;
import ar.fiuba.trabajoprofesional.mdauml.draw.DoubleDimension;
import ar.fiuba.trabajoprofesional.mdauml.draw.DrawingContext;
import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.ui.ApplicationState;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.ClassDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.ElementInserter;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.ClassDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.ClassElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlNode;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class ClassDiagramBuilderImpl implements ClassDiagramBuilder {

    private static final String CREATE_BOUNDARY = Msg.get("classtoolbar.boundary.command");
    private static final String CREATE_CONTROL = Msg.get("classtoolbar.control.command");
    private static final String CREATE_ENTITY = Msg.get("classtoolbar.entity.command");
    private static final double V_MARGIN = 10;
    private static final double H_MARGIN = 250;
    private static final double DIAGRAM_H_MARGIN = 30;
    private static final double DIAGRAM_V_MARGIN = 20;

    @Override
    public void buildClassDiagram(Map<Class<? extends UmlClass>,List<UmlClass>> classModel,String diagramName, ConversionModel conversionModel) {
        ApplicationState appState = AppFrame.get().getAppState();
        appState.openNewClassEditor();
        ClassDiagramEditor diagramEditor = (ClassDiagramEditor) appState.getCurrentEditor();
        ClassDiagram diagram = (ClassDiagram) diagramEditor.getDiagram();
        diagram.setName(diagramName);

        List<ClassElement> controls = toElements(classModel,conversionModel.getControls(),diagramEditor);
        List<ClassElement> entities = toElements(classModel,conversionModel.getEntities(),diagramEditor);
        List<ClassElement> boundaries = toElements(classModel,conversionModel.getBoundaries(),diagramEditor);
        Map<ClassElement,List<ClassElement>> controlEntities = new HashMap<>();
        Map<ClassElement,List<ClassElement>> controlBoundaries = new HashMap<>();
        initControlMaps(controlBoundaries,controlEntities,controls,entities,boundaries,conversionModel.getRelations());
        Map<ClassElement,Point2D> positions = calculatePositions(controlBoundaries,controls,controlEntities);
        for(ClassElement element : positions.keySet()){
            Point2D pos = positions.get(element);
            ElementInserter.insert(element,diagramEditor,pos);
        }
        for(SimpleRelation relation:conversionModel.getRelations()){
            SimpleClass noControl;
            SimpleClass aControl;
            if (relation.getClass1() instanceof Control) {
                noControl = relation.getClass2();
                aControl = relation.getClass1();
            }
            else {
                noControl = relation.getClass1();
                aControl  = relation.getClass2();
            }
            UmlNode source = findElement(controls,aControl.getName());
            ClassElement elem;
            if(noControl instanceof Boundary)
                elem = findElement(boundaries,noControl.getName());
            else
                elem = findElement(entities,noControl.getName());
            try {
                ElementInserter.insertConnection(source,elem,diagramEditor,RelationType.ASSOCIATION,new Point2D.Double(source.getAbsCenterX(),source.getAbsCenterY()),new Point2D.Double(elem.getAbsCenterX(),elem.getAbsCenterY()));
            } catch (AddConnectionException e) {
                    e.printStackTrace();
            }
        }

    }

    private void initControlMaps(Map<ClassElement, List<ClassElement>> controlBoundaries,
                                 Map<ClassElement, List<ClassElement>> controlEntities,
                                 List<ClassElement> controls,
                                 List<ClassElement> entities,
                                 List<ClassElement> boundaries,
                                 Set<SimpleRelation> relations) {
        for(ClassElement control:controls){
            controlBoundaries.put(control,new ArrayList<ClassElement>());
            List<ClassElement> boundaryList = controlBoundaries.get(control);
            controlEntities.put(control,new ArrayList<ClassElement>());
            List<ClassElement> entityList = controlEntities.get(control);
            for(SimpleRelation relation : relations) {
                SimpleClass noControl;
                SimpleClass aControl;
                if (relation.getClass1() instanceof Control) {
                    noControl = relation.getClass2();
                    aControl = relation.getClass1();
                }
                else {
                    noControl = relation.getClass1();
                    aControl  = relation.getClass2();
                }
                if(! control.getLabelText().equals(aControl.getName()))
                    continue;
                if (noControl instanceof Boundary)
                    boundaryList.add(findElement(boundaries, noControl.getName()));
                else
                    entityList.add(findElement(entities, noControl.getName()));
            }
        }

    }

    private List<ClassElement> toElements(Map<Class<? extends UmlClass>,List<UmlClass>> classModel,Set<? extends SimpleClass> simpleClasses, ClassDiagramEditor diagramEditor) {
        ClassDiagram diagram = (ClassDiagram) diagramEditor.getDiagram();
        List<ClassElement> elements = new ArrayList<>();
        for(SimpleClass simpleClass : simpleClasses){
            UmlClass umlClass = null;
            if(simpleClass instanceof  Boundary) {
                for(UmlClass umlBoundary : classModel.get(UmlBoundary.class))
                    if(umlBoundary.getName().equals(simpleClass.getName()))
                        umlClass=umlBoundary;
            }else if (simpleClass instanceof Control) {
                for(UmlClass umlControl : classModel.get(UmlControl.class))
                    if(umlControl.getName().equals(simpleClass.getName()))
                        umlClass=umlControl;
            }else {
                for(UmlClass umlEntity : classModel.get(UmlEntity.class))
                    if(umlEntity.getName().equals(simpleClass.getName()))
                        umlClass=umlEntity;
            }


            ClassElement element = (ClassElement) diagram.getElementFactory().createNodeFromModel(umlClass);
            ArrayList<Boolean> methodVisibility = new ArrayList<>();
            for(UmlMethod method : umlClass.getMethods()){
                if(simpleClass.getMethods().contains(method.getName()))
                    methodVisibility.add(true);
                else
                    methodVisibility.add(false);
            }
            element.setMethodVisibility(methodVisibility);
            element.setParent(diagram);
            DrawingContext drawingContext = diagramEditor.getDrawingContext();
            Rectangle clipBounds = new Rectangle();
            Graphics g = AppFrame.get().getGraphics();
            g.getClipBounds(clipBounds);
            drawingContext.setGraphics2D((Graphics2D) g,clipBounds );
            element.recalculateSize(drawingContext);

            elements.add(element);
        }
        return elements;
    }

    private Map<ClassElement,Point2D> calculatePositions(Map<ClassElement,List<ClassElement>> boundaryMap, List<ClassElement> controls, Map<ClassElement,List<ClassElement>> entityMap){

        Map<ClassElement,Point2D> positions = new HashMap<>();

        Point2D boundaryOffset = new Point2D.Double(DIAGRAM_H_MARGIN,DIAGRAM_V_MARGIN);
        Point2D controlOffset = new Point2D.Double(DIAGRAM_H_MARGIN,DIAGRAM_V_MARGIN);
        Point2D entityOffset = new Point2D.Double(DIAGRAM_H_MARGIN,DIAGRAM_V_MARGIN);

        Set<ClassElement> allEntities = new HashSet<>();
        Set<ClassElement> allBoundaries = new HashSet<>();
        for(ClassElement control : controls) {
            List<ClassElement> boundaries = boundaryMap.get(control);
            allBoundaries.addAll(boundaries);
            List<ClassElement> entities = entityMap.get(control);
            allEntities.addAll(entities);
        }
        Dimension2D allBoundaryDimension = calculateDimension(allBoundaries);
        controlOffset.setLocation(controlOffset.getX()+allBoundaryDimension.getWidth()+H_MARGIN,controlOffset.getY());
        Dimension2D allControlDimension = calculateDimension(controls);
        entityOffset.setLocation(controlOffset.getX()+allControlDimension.getWidth()+H_MARGIN,entityOffset.getY());
        Dimension2D allEntitiesDimension = calculateDimension(allEntities);
        for(ClassElement control : controls){
            List<ClassElement> boundaries = boundaryMap.get(control);
            List<ClassElement> entities = entityMap.get(control);
            for(ClassElement element : positions.keySet()){
                boundaries.remove(element);
                entities.remove(element);
            }
            Dimension2D controlDimension = control.getSize();
            Dimension2D boundariesDimension = calculateDimension(boundaries);
            Dimension2D entitiesDimension = calculateDimension(entities);
            Dimension2D groupDimension = new DoubleDimension(0,0);
            double maxHeight = controlDimension.getHeight();
            if(boundariesDimension.getHeight() > maxHeight)
                maxHeight = boundariesDimension.getHeight();
            if(entitiesDimension.getHeight() > maxHeight)
                maxHeight = entitiesDimension.getHeight();
            groupDimension.setSize( boundariesDimension.getWidth() +
                            controlDimension.getWidth() + entitiesDimension.getWidth()+ 2*H_MARGIN,
                    maxHeight);
            double boundaryVPadding = (maxHeight - boundariesDimension.getHeight() )/2.0;
            boundaryOffset.setLocation(boundaryOffset.getX(),boundaryOffset.getY()+boundaryVPadding);
            double controlVPadding = (maxHeight - controlDimension.getHeight() )/2.0;
            controlOffset.setLocation(controlOffset.getX(),controlOffset.getY()+controlVPadding);
            double entityVPadding = (maxHeight - entitiesDimension.getHeight() )/2.0;
            entityOffset.setLocation(entityOffset.getX(),entityOffset.getY()+entityVPadding);

            ////// Setting control position
            Point2D controlPos = (Point2D) controlOffset.clone();
            double controlHPadding =  (controlDimension.getWidth() - allControlDimension.getWidth())/2.0;
            controlPos.setLocation(controlPos.getX()+controlHPadding,controlPos.getY());
            positions.put(control, controlPos);
            controlOffset.setLocation(controlOffset.getX(),controlOffset.getY()+controlDimension.getHeight()+controlVPadding+V_MARGIN);

            ////// Setting boundaries position
            for(ClassElement boundary: boundaries){
                Point2D boundaryPos = (Point2D) boundaryOffset.clone();
                double boundaryHPadding =  (boundary.getSize().getWidth() - allBoundaryDimension.getWidth())/2.0;
                boundaryPos.setLocation(boundaryPos.getX()+boundaryHPadding,boundaryPos.getY());
                positions.put(boundary, boundaryPos);
                boundaryOffset.setLocation(boundaryOffset.getX(),
                        boundaryOffset.getY()+boundary.getSize().getHeight()+V_MARGIN);
            }
            if(!boundaries.isEmpty())
                boundaryOffset.setLocation(boundaryOffset.getX(),boundaryOffset.getY() - V_MARGIN);

            boundaryOffset.setLocation(boundaryOffset.getX(),boundaryOffset.getY()+boundaryVPadding+V_MARGIN);

            ////// Setting entities position
            for(ClassElement entity: entities){
                Point2D entityPos = (Point2D) entityOffset.clone();
                double entityHPadding =  (entity.getSize().getWidth() - allEntitiesDimension.getWidth())/2.0;
                entityPos.setLocation(entityPos.getX()+entityHPadding,entityPos.getY());
                positions.put(entity, entityPos);
                entityOffset.setLocation(entityOffset.getX(),
                        entityOffset.getY()+entity.getSize().getHeight()+V_MARGIN);
            }
            if(!entities.isEmpty())
                entityOffset.setLocation(entityOffset.getX(),entityOffset.getY() - V_MARGIN);

            entityOffset.setLocation(entityOffset.getX(),entityOffset.getY()+entityVPadding+V_MARGIN);

        }
        return  positions;
    }

    private ClassElement findElement(List<ClassElement> elements, String name) {
        for(ClassElement element : elements){
            if(element.getLabelText().equals(name))
                return element;
        }
        return null;
    }

    private Dimension2D calculateDimension(Collection<ClassElement> elements) {
        Dimension2D dimension = new DoubleDimension(0,0);
        for(ClassElement element : elements){
            double elementWidth = element.getSize().getWidth();
            double elementHeight = element.getSize().getHeight();
            double dimensionWidth = dimension.getWidth();
            double dimensionHeight = dimension.getHeight();

            if(elementWidth > dimensionWidth)
                dimension.setSize(elementWidth,dimensionHeight);

            dimension.setSize(dimension.getWidth(),dimensionHeight + elementHeight + V_MARGIN);

        }
        if(!elements.isEmpty())
            dimension.setSize(dimension.getWidth(),dimension.getHeight()-V_MARGIN);
        return dimension;
    }
}
