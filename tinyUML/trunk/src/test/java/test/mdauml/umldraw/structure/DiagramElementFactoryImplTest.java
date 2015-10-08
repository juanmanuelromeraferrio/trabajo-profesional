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
package test.mdauml.umldraw.structure;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.Association;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.ClassElement;
import ar.fiuba.trabajoprofesional.mdauml.draw.Node;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.Dependency;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.DiagramElementFactory;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.NoteConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.NoteElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.PackageElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlNode;
import ar.fiuba.trabajoprofesional.mdauml.model.ElementType;
import ar.fiuba.trabajoprofesional.mdauml.model.Relation;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlClass;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlRelation;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.ClassDiagram;

/**
 * Tests the DiagramElementFactory part of StructureDiagram.
 * @author Wei-ju Wu
 * @version 1.0
 */
public class DiagramElementFactoryImplTest extends MockObjectTestCase {
  private Mock mockUmlModel = mock(UmlModel.class);
  private int notificationCounter;
  private ClassDiagram diagram =
    new ClassDiagram((UmlModel) mockUmlModel.proxy()) {
    public void nodeMoved(Node node) {
      notificationCounter++;
    }
  };
  private DiagramElementFactory factory;
  private UmlPackage pkg = (UmlPackage) UmlPackage.getPrototype().clone();
  private UmlClass clss = (UmlClass) UmlClass.getPrototype().clone();
  private Relation assoc = new UmlRelation();
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void setUp() {
   factory = diagram;
  }
  
  // ************************************************************************
  // ****** Nodes
  // *******************************
  
  /**
   * Tests node creation.
   */
  public void testCreateNode() {
    assertNotNull(factory.createNode(ElementType.NOTE));
    checkStdCreationConditions(factory.createNode(ElementType.PACKAGE));
    checkStdCreationConditions(factory.createNode(ElementType.COMPONENT));
    checkStdCreationConditions(factory.createNode(ElementType.CLASS));
  }

  /**
   * These conditions need to be true on PackageElement creation.
   */
  private void checkStdCreationConditions(UmlNode node) {
    assertNotNull(node.getModelElement());
    assertNull(node.getParent());
    // check if the diagram was added as a listener
    node.setParent(diagram);
    node.setAbsolutePos(1.0, 3.0);
    assertTrue(notificationCounter >= 1);
  }

  // ************************************************************************
  // ****** Connections
  // *******************************
  
  /**
   * Tests the creation of a Dependency given two Nodes.
   */
  public void testCreateDependency() {
    PackageElement source = (PackageElement)
      factory.createNode(ElementType.PACKAGE);
    PackageElement target = (PackageElement)
      factory.createNode(ElementType.PACKAGE);
    
    Dependency conn = (Dependency) factory.createConnection(
      RelationType.DEPENDENCY, source, target);
    assertStdConnectionConditions(conn, source, target);
    Relation relation = (Relation) conn.getModelElement();
    assertFalse(relation.canSetElement1Navigability());
    assertFalse(relation.canSetElement2Navigability());
  }
  
  /**
   * Tests the creation of an Association.
   */
  public void testCreateAssociation() {
    ClassElement class1 = (ClassElement) factory.createNode(ElementType.CLASS);
    ClassElement class2 = (ClassElement) factory.createNode(ElementType.CLASS);

    Association conn = (Association) factory.createConnection(
      RelationType.ASSOCIATION, class1, class2);
    assertStdConnectionConditions(conn, class1, class2);
    Relation relation = (Relation) conn.getModelElement();
    assertTrue(relation.canSetElement1Navigability());
    assertTrue(relation.canSetElement2Navigability());
  }
  
  /**
   * Tests the creation of a Composition.
   */
  public void testCreateComposition() {
    ClassElement class1 = (ClassElement) factory.createNode(ElementType.CLASS);
    ClassElement class2 = (ClassElement) factory.createNode(ElementType.CLASS);
    
    Association composition = (Association) factory.createConnection(
      RelationType.COMPOSITION, class1, class2);
    assertStdConnectionConditions(composition, class1, class2);
    Relation umlcomp = (Relation) composition.getModelElement();
    assertFalse(umlcomp.canSetElement1Navigability());
    assertTrue(umlcomp.canSetElement2Navigability());    
  }
  
  /**
   * Tests the creation of an Aggregation.
   */
  public void testCreateAggregation() {
    ClassElement class1 = (ClassElement) factory.createNode(ElementType.CLASS);
    ClassElement class2 = (ClassElement) factory.createNode(ElementType.CLASS);
    
    Association composition = (Association) factory.createConnection(
      RelationType.AGGREGATION, class1, class2);
    assertStdConnectionConditions(composition, class1, class2);
    Relation umlcomp = (Relation) composition.getModelElement();
    assertFalse(umlcomp.canSetElement1Navigability());
    assertTrue(umlcomp.canSetElement2Navigability());    
  }

  /**
   * Tests the creation of a note connection.
   */
  public void testCreateNoteConnection() {
    ClassElement clss = (ClassElement) factory.createNode(ElementType.CLASS);
    NoteElement note = (NoteElement) factory.createNode(ElementType.NOTE);
    NoteConnection conn = (NoteConnection)
      factory.createConnection(RelationType.NOTE_CONNECTOR, clss, note);
    assertStdConnectionConditions(conn, clss, note);
    assertNull(conn.getModelElement());
  }

  /**
   * Conditions that must hold true for every created connection type.
   * @param conn the connection
   * @param node1 the first node
   * @param node2 the second node
   */
  private void assertStdConnectionConditions(UmlConnection conn, UmlNode node1,
    UmlNode node2) {
    assertEquals("should be node1", node1, conn.getNode1());
    assertEquals("should be node2", node2, conn.getNode2());
    assertEquals(1, node1.getConnections().size());
    assertEquals(1, node2.getConnections().size());
    assertNull("parent should be null", conn.getParent());    
  }  
}
