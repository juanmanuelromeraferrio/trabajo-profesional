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
package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.draw.RectilinearConnection;
import ar.fiuba.trabajoprofesional.mdauml.draw.SimpleConnection;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.ConvertConnectionTypeCommand;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.SetConnectionNavigabilityCommand;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.Association;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.ClassElement;
import ar.fiuba.trabajoprofesional.mdauml.util.MethodCall;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the most common setup for a diagram editor component.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class StructureDiagramEditor extends DiagramEditor {

  private static Map<String, MethodCall> selectorMap =
    new HashMap<String, MethodCall>();

  static {
   initSelectorMap();
  }

  /**
   * Constructor.
   * @param aWindow the main window
   * @param aDiagram the diagram
   */
  public StructureDiagramEditor(Component aWindow, GeneralDiagram aDiagram) {
    super(aWindow, aDiagram);
  }

  /**
   * Initializes the selector map.
   */
  private static void initSelectorMap() {
    try {
      selectorMap.put("CREATE_PACKAGE", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreationMode",
        ElementType.class), ElementType.PACKAGE));
      selectorMap.put("CREATE_COMPONENT", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreationMode",
        ElementType.class), ElementType.COMPONENT));
      selectorMap.put("CREATE_CLASS", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreationMode",
        ElementType.class), ElementType.CLASS));
      selectorMap.put("CREATE_DEPENDENCY", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
        RelationType.class), RelationType.DEPENDENCY));
      selectorMap.put("CREATE_ASSOCIATION", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
        RelationType.class), RelationType.ASSOCIATION));
      selectorMap.put("CREATE_COMPOSITION", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
        RelationType.class), RelationType.COMPOSITION));
      selectorMap.put("CREATE_AGGREGATION", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
        RelationType.class), RelationType.AGGREGATION));
      selectorMap.put("CREATE_INHERITANCE", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
        RelationType.class), RelationType.INHERITANCE));
      selectorMap.put("CREATE_INTERFACE_REALIZATION", new MethodCall(
        StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
        RelationType.class), RelationType.INTERFACE_REALIZATION));
      selectorMap.put("RESET_POINTS", new MethodCall(
        StructureDiagramEditor.class.getMethod("resetConnectionPoints")));
      selectorMap.put("RECT_TO_DIRECT", new MethodCall(
        StructureDiagramEditor.class.getMethod("rectilinearToDirect")));
      selectorMap.put("DIRECT_TO_RECT", new MethodCall(
        StructureDiagramEditor.class.getMethod("directToRectilinear")));
      selectorMap.put("NAVIGABLE_TO_SOURCE", new MethodCall(
        StructureDiagramEditor.class.getMethod("setNavigability",
        RelationEndType.class), RelationEndType.SOURCE));
      selectorMap.put("NAVIGABLE_TO_TARGET", new MethodCall(
        StructureDiagramEditor.class.getMethod("setNavigability",
        RelationEndType.class), RelationEndType.TARGET));
    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void editProperties(DiagramElement element) {
    Window window = (mainWindow instanceof Window) ? ((Window) mainWindow) :
        null;
    if (element instanceof ClassElement) {
      ClassElement classElement = (ClassElement) element;
      UmlClass umlclass = (UmlClass) classElement.getModelElement();
      EditClassDialog dialog = new EditClassDialog(window, classElement,
        true);
      dialog.setLocationRelativeTo(mainWindow);
      dialog.setVisible(true);
      if (dialog.isOk()) {
        umlclass.setAbstract(dialog.classIsAbstract());
        classElement.setShowOperations(dialog.showOperations());
        classElement.setShowAttributes(dialog.showAttributes());
        classElement.setShowStereotypes(dialog.showStereotypes());
        umlclass.setName(dialog.getName());
        umlclass.setMethods(dialog.getMethods());
        umlclass.setAttributes(dialog.getAttributes());
        umlclass.setStereotypes(dialog.getStereotypes());
        redraw();
      }
    } else if (element instanceof Association) {
      Association association = (Association) element;
      EditAssociationDialog dialog = new EditAssociationDialog(window,
        association, true);
      dialog.setLocationRelativeTo(mainWindow);
      dialog.setVisible(true);
      redraw();
    }
  }

  /**
   * Switches a rectilinear connection to a direct one.
   */
  public void rectilinearToDirect() {
    if (getSelectedElements().size() > 0 &&
        getSelectedElements().get(0) instanceof UmlConnection) {
      UmlConnection conn = (UmlConnection) getSelectedElements().get(0);
      execute(new ConvertConnectionTypeCommand(this, conn,
        new SimpleConnection()));
       // we can only tell the selection handler to forget about the selection
      selectionHandler.deselectAll();
    }
  }

  /**
   * Switches a direct connection into a rectilinear one.
   */
  public void directToRectilinear() {
    if (getSelectedElements().size() > 0 &&
        getSelectedElements().get(0) instanceof UmlConnection) {
      UmlConnection conn = (UmlConnection) getSelectedElements().get(0);
      execute(new ConvertConnectionTypeCommand(this, conn,
        new RectilinearConnection()));
       // we can only tell the selection handler to forget about the selection
      selectionHandler.deselectAll();
    }
  }

  /**
   * Sets the end type navigability of the current selected connection.
   * @param endType the RelationEndType
   */
  public void setNavigability(RelationEndType endType) {
    if (getSelectedElements().size() > 0 &&
      getSelectedElements().get(0) instanceof UmlConnection) {
      UmlConnection conn = (UmlConnection) getSelectedElements().get(0);
      Relation relation = (Relation) conn.getModelElement();
      // Setup a toggle
      if (endType == RelationEndType.SOURCE) {
        execute(new SetConnectionNavigabilityCommand(this, conn, endType,
          !relation.isNavigableToElement1()));
      }
      if (endType == RelationEndType.TARGET) {
        execute(new SetConnectionNavigabilityCommand(this, conn, endType,
          !relation.isNavigableToElement2()));
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleCommand(String command) {
    MethodCall methodcall = selectorMap.get(command);
    if (methodcall != null) methodcall.call(this);
    else super.handleCommand(command);
  }
}
