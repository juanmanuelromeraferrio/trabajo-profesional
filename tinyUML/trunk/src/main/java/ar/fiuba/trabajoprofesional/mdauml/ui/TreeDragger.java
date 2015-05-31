package ar.fiuba.trabajoprofesional.mdauml.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import ar.fiuba.trabajoprofesional.mdauml.draw.Node;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlClass;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.AddNodeCommand;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.ClassElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.PackageElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.ActorElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseElement;

public class TreeDragger implements TreeDraggerListener {

    private UmlModelElement draggerElement;
    private ApplicationShell shell;


    @Override public void setDraggerElement(UmlModelElement element) {
        this.draggerElement = element;
        System.out.println("Dragging:" + draggerElement.getName());
    }


    @Override public void setReleasePoint(double x, double y) {
        if (draggerElement == null)
            return;
        DiagramEditor currentDiagramEditor = shell.getCurrentEditor();

        GeneralDiagram diagram = currentDiagramEditor.getDiagram();
        Point origin = currentDiagramEditor.getLocationOnScreen();

        double x1 = diagram.getAbsoluteX1() + origin.getX();
        double x2 = diagram.getAbsoluteX2() + origin.getX();
        double y1 = diagram.getAbsoluteX1() + origin.getY();
        double y2 = diagram.getAbsoluteY2() + origin.getY();



        if (x > x1 && x < x2 && y > y1 && y < y2) {

            Node element = null;
            if (draggerElement instanceof UmlActor) {
                element = (Node) ActorElement.getPrototype().clone();
                ActorElement actor = (ActorElement) element;
                actor.setModelElement((UmlActor) draggerElement);
            } else if (draggerElement instanceof UmlUseCase) {
                element = (Node) UseCaseElement.getPrototype().clone();
                UseCaseElement usecase = (UseCaseElement) element;
                usecase.setModelElement((UmlUseCase) draggerElement);
            } else if (draggerElement instanceof UmlClass) {
                element = (Node) ClassElement.getPrototype().clone();
                ClassElement clazz = (ClassElement) element;
                clazz.setModelElement((UmlClass) draggerElement);
            } else if (draggerElement instanceof UmlPackage) {
                element = (Node) PackageElement.getPrototype().clone();
                PackageElement packageElement = (PackageElement) element;
                packageElement.setModelElement((UmlPackage) draggerElement);
            } else
                return;



            AddNodeCommand createCommand =
                new AddNodeCommand(currentDiagramEditor, diagram, element, x - origin.getX(),
                    y - origin.getY());

            currentDiagramEditor.execute(createCommand);

        }


    }


    public void setShell(ApplicationShell shell) {
        this.shell = shell;

    }

}
