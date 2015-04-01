/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with TinyUML; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */
package ar.fiuba.trabajoprofesional.mdauml.ui;

import ar.fiuba.trabajoprofesional.mdauml.model.NameChangeListener;
import ar.fiuba.trabajoprofesional.mdauml.model.NamedElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.SequenceDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.StructureDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.UseCaseDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.sequence.SequenceDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.StructureDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseDiagram;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

import javax.swing.*;
import java.awt.*;

/**
 * A manager class for the available structure diagrams in the application. It maintains the UI
 * context for in order to properly create the editor.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class EditorFactory {

  private ApplicationShell shell;
  private ApplicationState appState;
  private JTabbedPane tabbedPane;
  private int structureCounter = 1, sequenceCounter = 1;

  /**
   * Constructor.
   *
   * @param aShell the application shell
   * @param anAppState the application state object
   * @param aTabbedPane the tabbed pane
   */
  public EditorFactory(ApplicationShell aShell, ApplicationState anAppState, JTabbedPane aTabbedPane) {
    shell = aShell;
    appState = anAppState;
    tabbedPane = aTabbedPane;
  }

  /**
   * Resets the internal counter.
   */
  public void reset() {
    structureCounter = 1;
  }

  /**
   * Opens a new structure editor and displays it in the editor area.
   * 
   * @param umlModel the UmlModel the diagram belongs to
   * @return the editor panel
   */
  public EditorPanel openNewStructureEditor(UmlModel umlModel) {
    GeneralDiagram diagram = new StructureDiagram(umlModel);
    diagram.setLabelText(ApplicationResources.getInstance()
        .getString("stdcaption.structurediagram") + " " + (structureCounter++));
    umlModel.addDiagram(diagram);
    return createEditorPanel(new StructureDiagramEditor(shell.getShellComponent(), diagram),
        new StaticStructureEditorToolbarManager());
  }

  /**
   * Opens a structure editor for an existing diagram.
   * 
   * @param diagram the diagram
   * @return the editor panel
   */
  public EditorPanel openStructureEditor(GeneralDiagram diagram) {
    return createEditorPanel(new StructureDiagramEditor(shell.getShellComponent(), diagram),
        new StaticStructureEditorToolbarManager());
  }

  /**
   * Creates an editor for the specified diagram and adds it to the tabbed pane.
   * 
   * @param diagramEditor the diagram editor
   * @param toolbarManager the ToolbarManager
   * @return the StructureEditor panel
   */
  private EditorPanel createEditorPanel(DiagramEditor diagramEditor, ToolbarManager toolbarManager) {
    EditorPanel editor = new EditorPanel(diagramEditor, toolbarManager);
    GeneralDiagram diagram = diagramEditor.getDiagram();
    final Component comp = tabbedPane.add(diagram.getLabelText(), editor);
    final int index = tabbedPane.indexOfComponent(comp);
    tabbedPane.setToolTipTextAt(index, diagram.getLabelText());
    diagram.addNameChangeListener(new NameChangeListener() {
      /** {@inheritDoc} */
      public void nameChanged(NamedElement element) {
        tabbedPane.setTitleAt(index, element.getName());
        tabbedPane.setToolTipTextAt(index, element.getName());
      }
    });
    tabbedPane.setSelectedComponent(editor);
    tabbedPane.setTabComponentAt(index, new ClosableTabComponent(tabbedPane));
    editor.getDiagramEditor().addFocusListener(appState);
    editor.getDiagramEditor().addUndoableEditListener(appState.getUndoManager());
    return editor;
  }

  /**
   * Creates a new Sequence editor.
   * 
   * @param umlModel the UmlModel
   * @return the editor panel
   */
  public EditorPanel openNewSequenceEditor(UmlModel umlModel) {
    GeneralDiagram diagram = new SequenceDiagram(umlModel);
    diagram.setLabelText(ApplicationResources.getInstance().getString("stdcaption.sequencediagram")
        + " " + (sequenceCounter++));
    umlModel.addDiagram(diagram);
    return createEditorPanel(new SequenceDiagramEditor(shell.getShellComponent(), diagram),
        new SequenceEditorToolbarManager());
  }

  /**
   * Creates a new Use Case editor.
   * 
   * @param umlModel the UmlModel
   * @return the editor panel
   */
  public EditorPanel openNewUseCaseEditor(UmlModel umlModel) {
    GeneralDiagram diagram = new UseCaseDiagram(umlModel);
    diagram.setLabelText(ApplicationResources.getInstance().getString("stdcaption.usecasediagram")
        + " " + (sequenceCounter++));
    umlModel.addDiagram(diagram);
    return createEditorPanel(new UseCaseDiagramEditor(shell.getShellComponent(), diagram),
        new UseCaseEditorToolbarManager());
  }


}
