package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.ClassDiagramBuilder;

import ar.fiuba.trabajoprofesional.mdauml.conversion.model.Boundary;
import ar.fiuba.trabajoprofesional.mdauml.conversion.model.IConversionDiagram;
import ar.fiuba.trabajoprofesional.mdauml.conversion.model.SimpleClass;
import ar.fiuba.trabajoprofesional.mdauml.draw.DoubleDimension;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.ui.ApplicationState;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.ClassDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.ElementInserter;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.ClassDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.ClassElement;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.*;

public class ClassDiagramBuilderImpl implements ClassDiagramBuilder {

    private static final String CREATE_BOUNDARY = Msg.get("classtoolbar.boundary.command");
    private static final String CREATE_CONTROL = Msg.get("classtoolbar.control.command");
    private static final String CREATE_ENTITY = Msg.get("classtoolbar.entity.command");
    private static final double V_MARGIN = 20;
    private static final double H_MARGIN = 50;
    private static final double DIAGRAM_H_MARGIN = 20;
    private static final double DIAGRAM_V_MARGIN = 20;

    @Override
    public void buildClassDiagram(Project project,String diagramName, IConversionDiagram conversionModel) {
        ApplicationState appState = AppFrame.get().getAppState();
        appState.openNewClassEditor();
        ClassDiagramEditor diagramEditor = (ClassDiagramEditor) appState.getCurrentEditor();
        ClassDiagram diagram = (ClassDiagram) diagramEditor.getDiagram();
        diagram.setName(diagramName);
        Boundary boundary = conversionModel.getBoundaries().iterator().next();
        UmlBoundary umlBoundary = (UmlBoundary) UmlBoundary.getPrototype().clone();
        umlBoundary.setName(boundary.getName());
        List<UmlProperty> methods = new ArrayList<>();
        for(String method : boundary.getMethods()){
            UmlProperty umlMethod = (UmlProperty) UmlProperty.getPrototype().clone();
            umlMethod.setName(method);
            methods.add(umlMethod);
        }
        umlBoundary.setMethods(methods);
        ClassElement element = (ClassElement) diagram.getElementFactory().createNodeFromModel(umlBoundary);
        element.setParent(diagram);

        Point2D pos = new Point2D.Double(300,300);
        ElementInserter.insert(element,diagramEditor,pos);
    }

    private Map<ClassElement,Point2D> calculatePositions(Map<String,List<ClassElement>> boundaryMap, List<ClassElement> controls, Map<String,List<ClassElement>> entityMap){

        Map<ClassElement,Point2D> positions = new HashMap<>();

        Point2D boundaryOffset = new Point2D.Double(DIAGRAM_H_MARGIN,DIAGRAM_V_MARGIN);
        Point2D controlOffset = new Point2D.Double(DIAGRAM_H_MARGIN,DIAGRAM_V_MARGIN);
        Point2D entityOffset = new Point2D.Double(DIAGRAM_H_MARGIN,DIAGRAM_V_MARGIN);

        Set<ClassElement> allEntities = new HashSet<>();
        Set<ClassElement> allBoundaries = new HashSet<>();
        for(ClassElement control : controls) {
            List<ClassElement> boundaries = boundaryMap.get(control.getLabelText());
            allBoundaries.addAll(boundaries);
            List<ClassElement> entities = entityMap.get(control.getLabelText());
            allEntities.addAll(entities);
        }
        Dimension2D allBoundaryDimension = calculateDimension(allBoundaries);
        controlOffset.setLocation(controlOffset.getX()+allBoundaryDimension.getWidth()+H_MARGIN,controlOffset.getY());
        Dimension2D allControlDimension = calculateDimension(controls);
        entityOffset.setLocation(controlOffset.getX()+allControlDimension.getWidth()+H_MARGIN,entityOffset.getY());
        Dimension2D allEntitiesDimension = calculateDimension(allEntities);
        for(ClassElement control : controls){
            List<ClassElement> boundaries = boundaryMap.get(control.getLabelText());
            List<ClassElement> entities = entityMap.get(control.getLabelText());
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
                            controlDimension.getHeight() + entitiesDimension.getHeight()+ 2*H_MARGIN,
                    maxHeight);
            double boundaryVPadding = (boundariesDimension.getHeight() - maxHeight)/2.0;
            boundaryOffset.setLocation(boundaryOffset.getX(),boundaryOffset.getY()+boundaryVPadding);
            double controlVPadding = (controlDimension.getHeight() - maxHeight)/2.0;
            controlOffset.setLocation(controlOffset.getX(),controlOffset.getY()+controlVPadding);
            double entityVPadding = (entitiesDimension.getHeight() - maxHeight)/2.0;
            entityOffset.setLocation(entityOffset.getX(),entityOffset.getY()+entityVPadding);


            Point2D controlPos = (Point2D) controlOffset.clone();
            double controlHPadding =  (controlDimension.getWidth() - allControlDimension.getWidth())/2.0;
            controlPos.setLocation(controlPos.getX()+controlHPadding,controlPos.getY());
            positions.put(control, controlPos);
            controlOffset.setLocation(controlOffset.getX(),controlOffset.getY()+controlDimension.getHeight()+V_MARGIN);

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

            boundaryOffset.setLocation(boundaryOffset.getX(),boundaryOffset.getY()+boundaryVPadding);

        }
        return  null;
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

            dimension.setSize(dimensionWidth,dimensionHeight + elementHeight + V_MARGIN);

        }
        if(!elements.isEmpty())
            dimension.setSize(dimension.getWidth(),dimension.getHeight()-V_MARGIN);
        return dimension;
    }
}
