package ar.fiuba.trabajoprofesional.mdauml.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import ar.fiuba.trabajoprofesional.mdauml.draw.Node;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.AddNodeCommand;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.ActorElement;

public class TreeDragger implements TreeDraggerListener {

  private UmlModelElement draggerElement;
  private ApplicationShell shell;


  @Override
  public void setDraggerElement(UmlModelElement element) {
    this.draggerElement = element;
    System.out.println("Dragging:"+ draggerElement.getName());
  }


  @Override
  public void setReleasePoint(double x, double y) {
    if(draggerElement==null)
      return;
    DiagramEditor currentDiagramEditor=shell.getCurrentEditor();
    
    GeneralDiagram diagram = currentDiagramEditor.getDiagram();
    Point origin= currentDiagramEditor.getLocationOnScreen();

    double x1 = diagram.getAbsoluteX1()+origin.getX();
    double x2 = diagram.getAbsoluteX2()+origin.getX();
    double y1 = diagram.getAbsoluteX1()+origin.getY();
    double y2 = diagram.getAbsoluteY2()+origin.getY();
    
    
    
    if(x > x1 && x < x2 && y > y1 && y < y2){
      
      Node element=null;
      if(draggerElement instanceof UmlActor){
        element= ActorElement.getPrototype();
        ActorElement actor = (ActorElement) element;
        actor.setModelElement((UmlActor) draggerElement);
      
      }
      
      AddNodeCommand createCommand = new AddNodeCommand(currentDiagramEditor,
          diagram, element, x-origin.getX(), y-origin.getY());
      
      currentDiagramEditor.execute(createCommand);
      
      System.out.println("Tree Release inside ");
    }
    
    System.out.println("Tree Release outside");
    
  }


  public void setShell(ApplicationShell shell) {
    this.shell=shell;
    
  }

}
