package ar.fiuba.trabajoprofesional.mdauml.ui;

import java.awt.*;
import java.awt.dnd.DragSource;

import ar.fiuba.trabajoprofesional.mdauml.draw.Node;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.AddNodeCommand;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

import javax.swing.*;

public class TreeDragger implements TreeDraggerListener {

    private AbstractUmlModelElement draggerElement;


    @Override public void setDraggerElement(AbstractUmlModelElement element) {
        this.draggerElement = element;
        if(element!=null) {
            AppFrame.get().setCursor(DragSource.DefaultMoveDrop);
            ApplicationState.TREE_DRAGING=true;
        }
    }


    @Override public void setReleasePoint(double x, double y) {
        if (draggerElement == null)
            return;
        AppFrame.get().setCursor(Cursor.getDefaultCursor());
        ApplicationState.TREE_DRAGING=false;
        DiagramEditor currentDiagramEditor = AppFrame.get().getCurrentEditor();
        if(currentDiagramEditor == null)
            return;

        GeneralDiagram diagram = currentDiagramEditor.getDiagram();
        Point origin = currentDiagramEditor.getLocationOnScreen();

        if (isInside(currentDiagramEditor,x,y)) {
            Node element;
            try {
                element = diagram.createNodeFromModel(draggerElement);
            }catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(AppFrame.get(), getResourceString("error.dragger.wrongDiagram.message"),
                        getResourceString("error.dragger.wrongDiagram.title"), JOptionPane.ERROR_MESSAGE);
                return;
            }

            AddNodeCommand createCommand =
                new AddNodeCommand(currentDiagramEditor, diagram, element, x - origin.getX(),
                    y - origin.getY());

            currentDiagramEditor.execute(createCommand);

        }


    }

    private boolean isInside(DiagramEditor diagramEditor,double x,double y){
        GeneralDiagram diagram = diagramEditor.getDiagram();
        Point origin = diagramEditor.getLocationOnScreen();

        double x1 = diagram.getAbsoluteX1() + origin.getX();
        double x2 = diagram.getAbsoluteX2() + origin.getX();
        double y1 = diagram.getAbsoluteX1() + origin.getY();
        double y2 = diagram.getAbsoluteY2() + origin.getY();



        return x > x1 && x < x2 && y > y1 && y < y2;

    }

    private String getResourceString(String property) {
        return ApplicationResources.getInstance().getString(property);
    }

}
