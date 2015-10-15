/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TinyUML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package test.mdauml.draw;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.List;

import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;
import org.jmock.cglib.MockObjectTestCase;
import ar.fiuba.trabajoprofesional.mdauml.draw.CompositeNode;
import ar.fiuba.trabajoprofesional.mdauml.draw.Connection;
import ar.fiuba.trabajoprofesional.mdauml.draw.Diagram;
import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramOperations;
import ar.fiuba.trabajoprofesional.mdauml.draw.Node;
import ar.fiuba.trabajoprofesional.mdauml.draw.SimpleConnection;
import ar.fiuba.trabajoprofesional.mdauml.draw.SimpleConnectionSelection;
import ar.fiuba.trabajoprofesional.mdauml.draw.SimpleLineConnectMethod;
import ar.fiuba.trabajoprofesional.mdauml.util.Command;

/**
 * Test class for SimpleConnectionSelection.
 * @author Wei-ju Wu
 * @version 1.0
 */
public class SimpleConnectionSelectionTest extends MockObjectTestCase {

  private Connection conn = new SimpleConnection();
  private Node node1 = new MyCompositeNode();
  private Node node2 = new MyCompositeNode();
  private CompositeNode parent = new MyAbsoluteParent();
  private DiagramOperations operations = new DiagramOperations() {
    public void moveElements(Command[] moveOperations) { }
    public void resizeElement(Node element, Point2D newpos,
      Dimension2D newsize) { }
    public void editProperties(DiagramElement element) { }
    public Diagram getDiagram() { return null; }
    public void setNewConnectionPoints(Connection conn, List<Point2D> points) {
      conn.setPoints(points);
    }
  };

  /**
   * Sets up a connection.
   * @return the selection
   */
  private SimpleConnectionSelection setupConnection() throws AddConnectionException {
    node1.setParent(parent);
    node1.setSize(80.0, 40.0);
    node2.setParent(parent);
    node2.setSize(80.0, 40.0);
    conn.setParent(parent);
    bindConnection(conn, node1, node2);
    Point2D center1 = new Point2D.Double(node1.getAbsCenterX(),
      node1.getAbsCenterY());
    Point2D center2 = new Point2D.Double(node2.getAbsCenterX(),
      node2.getAbsCenterY());
    SimpleLineConnectMethod.getInstance().generateAndSetPointsToConnection(
      conn, node1, node2, center1, center2);
    return (SimpleConnectionSelection)
      conn.getSelection(operations);        
  }
  
  /**
   * Binds the connection to the nodes.
   * @param conn the connection
   * @param node1 the first node
   * @param node2 the second node
   */
  private void bindConnection(Connection conn, Node node1, Node node2) throws AddConnectionException {
    conn.setNode1(node1);
    conn.setNode2(node2);
    node1.addConnection(conn);
    node2.addConnection(conn);
  }

  /**
   * Dragging a segment and hereby generating a new control point to drag
   * around, then cancel the drag.
   */
  public void testDragSegmentAndCancel() throws AddConnectionException {
    node1.setOrigin(10, 10); // (10, 10, 80, 40) -> max (90, 50)
    node2.setOrigin(120, 10); // (120, 10, 80, 40) -> max (200, 50)
    SimpleConnectionSelection selection = setupConnection();
    selection.startPressing(105, 30);
    selection.startDragging();
    Point2D oldp1 = conn.getEndPoint1();
    Point2D oldp2 = conn.getEndPoint2();
    assertEquals(3, selection.getEditPoints().size());
    assertTrue(selection.isDragging());
    selection.updatePosition(120, 50);
    selection.updatePosition(130, 70);
    selection.cancelDragging();
    assertFalse(selection.isDragging());
    assertEquals(2, conn.getPoints().size());
    assertEquals(oldp1, conn.getPoints().get(0));
    assertEquals(oldp2, conn.getPoints().get(1));
  }

  /**
   * Dragging a segment and hereby generating a new control point to drag
   * around, then stop the drag.
   */
  public void testDragSegmentAndStop() throws AddConnectionException {
    node1.setOrigin(10, 10); // (10, 10, 80, 40) -> max (90, 50)
    node2.setOrigin(120, 10); // (120, 10, 80, 40) -> max (200, 50)
    SimpleConnectionSelection selection = setupConnection();
    selection.startPressing(105, 30);
    selection.startDragging();
    assertEquals(3, selection.getEditPoints().size());
    assertTrue(selection.isDragging());
    selection.updatePosition(120, 50);
    selection.updatePosition(130, 70);
    selection.stopDragging(140, 90);
    assertFalse(selection.isDragging());
    assertEquals(3, conn.getPoints().size());
    // the position at stopDragging() has no effect !
    Point2D pNew = new Point2D.Double(130, 70);
    assertEquals(pNew, conn.getPoints().get(1));
  }

  /**
   * Dragging a segment and hereby generating a new control point to drag
   * around, then stop the drag at a meltin position.
   */
  public void testDragSegmentAndStopAtMeltinPosition() throws AddConnectionException {
    node1.setOrigin(10, 10); // (10, 10, 80, 40) -> max (90, 50)
    node2.setOrigin(120, 10); // (120, 10, 80, 40) -> max (200, 50)
    SimpleConnectionSelection selection = setupConnection();
    Point2D oldp1 = conn.getEndPoint1();
    Point2D oldp2 = conn.getEndPoint2();
    selection.startPressing(105, 30);
    selection.startDragging();
    assertEquals(3, selection.getEditPoints().size());
    assertTrue(selection.isDragging());
    selection.updatePosition(120, 50);
    selection.updatePosition(110, 40);
    selection.updatePosition(106, 31); // melting position
    selection.stopDragging(106, 31);
    assertFalse(selection.isDragging());
    assertEquals(2, conn.getPoints().size());
    assertEquals(oldp1, conn.getPoints().get(0));
    assertEquals(oldp2, conn.getPoints().get(1));
  }
  
  // ************************************************************************
  // ***** Dragging control point
  // *****************************************
  
  /**
   * Dragging a point and moving it around.
   */
  public void testDragPointAndStop() throws AddConnectionException {
    node1.setOrigin(10, 10); // (10, 10, 80, 40) -> max (90, 50)
    node2.setOrigin(120, 10); // (120, 10, 80, 40) -> max (200, 50)
    SimpleConnectionSelection selection = setupConnection();
    // insert a new control point to make a total of three
    conn.getPoints().add(1, new Point2D.Double(105, 90));
    selection.startPressing(105, 90);
    selection.startDragging();
    assertTrue(selection.isDragging());
    selection.updatePosition(120, 50);
    selection.updatePosition(130, 70);
    selection.updatePosition(140, 90);
    selection.stopDragging(140, 90);
    assertFalse(selection.isDragging());
    assertEquals(3, conn.getPoints().size());
    Point2D pNew = new Point2D.Double(140, 90);
    assertEquals(pNew, conn.getPoints().get(1));
  }

  /**
   * Dragging a point and moving it around and stopping at a meltin position.
   */
  public void testDragPointAndStopWithMeldin() throws AddConnectionException {
    node1.setOrigin(10, 10); // (10, 10, 80, 40) -> max (90, 50)
    node2.setOrigin(120, 10); // (120, 10, 80, 40) -> max (200, 50)
    SimpleConnectionSelection selection = setupConnection();
    // insert a new control point to make a total of three
    conn.getPoints().add(1, new Point2D.Double(105, 90));
    selection.startPressing(105, 90);
    selection.startDragging();
    assertTrue(selection.isDragging());
    selection.updatePosition(120, 50);
    selection.updatePosition(110, 40);
    selection.updatePosition(105, 31);
    selection.stopDragging(105, 31);
    assertFalse(selection.isDragging());
    assertEquals(2, conn.getPoints().size());
  }
}
