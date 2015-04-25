package ar.fiuba.trabajoprofesional.mdauml.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;

public class TreeDragger implements TreeDraggerListener {

  private UmlModelElement draggerElement;


  @Override
  public void setDraggerElement(UmlModelElement element) {
    this.draggerElement = element;
    System.out.println("Dragging:"+ draggerElement.getName());
  }


  @Override
  public void setReleasePoint(int x, int y) {
    System.out.println("Tree Release on " +x + " , "+y);
    
  }

}
