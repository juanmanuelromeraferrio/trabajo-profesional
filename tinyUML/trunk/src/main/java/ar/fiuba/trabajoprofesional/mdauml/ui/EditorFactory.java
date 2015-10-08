/**
 * Copyright 2007 Wei-ju Wu
 * <p/>
 * This file is part of TinyUML.
 * <p/>
 * TinyUML is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p/>
 * TinyUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with TinyUML; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */
package ar.fiuba.trabajoprofesional.mdauml.ui;

import ar.fiuba.trabajoprofesional.mdauml.model.NameChangeListener;
import ar.fiuba.trabajoprofesional.mdauml.model.NamedElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.ClassDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.UseCaseDiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.ClassDiagram;
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

    private ApplicationState appState;
    private JTabbedPane tabbedPane;
    private int structureCounter = 1, sequenceCounter = 1, useCaseCounter = 1;

    /**
     * Constructor.
     *
     */
    public EditorFactory() {
        appState = AppFrame.get().getAppState();
        tabbedPane = appState.getTabbedPane();
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
        GeneralDiagram diagram = new ClassDiagram(umlModel);
        diagram.setLabelText(
            ApplicationResources.getInstance().getString("stdcaption.structurediagram") + " "
                + (structureCounter++));
        umlModel.addDiagram(diagram);
        return createEditorPanel(new ClassDiagramEditor( diagram),
            new StaticClassEditorToolbarManager());
    }

    /**
     * Opens a structure editor for an existing diagram.
     *
     * @param diagram the diagram
     * @return the editor panel
     */
    public EditorPanel openStructureEditor(GeneralDiagram diagram   ) {
        return createEditorPanel(new ClassDiagramEditor(diagram),
            new StaticClassEditorToolbarManager());
    }

    /**
     * Opens a use case editor for an existing diagram.
     *
     * @param diagram the diagram
     * @return the editor panel
     */
    public EditorPanel openUseCaseEditor(UseCaseDiagram diagram) {
        return createEditorPanel(new UseCaseDiagramEditor(diagram),
                new UseCaseEditorToolbarManager());
    }

    /**
     * Creates an editor for the specified diagram and adds it to the tabbed pane.
     *
     * @param diagramEditor  the diagram editor
     * @param toolbarManager the ToolbarManager
     * @return the StructureEditor panel
     */
    private EditorPanel createEditorPanel(DiagramEditor diagramEditor,
        ToolbarManager toolbarManager) {
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
     * Creates a new Use Case editor.
     *
     * @param umlModel the UmlModel
     * @return the editor panel
     */
    public EditorPanel openNewUseCaseEditor(UmlModel umlModel) {
        GeneralDiagram diagram = new UseCaseDiagram(umlModel);
        diagram.setLabelText(
            ApplicationResources.getInstance().getString("stdcaption.usecasediagram") + " "
                + (useCaseCounter++));
        umlModel.addDiagram(diagram);
        return createEditorPanel(new UseCaseDiagramEditor( diagram),
            new UseCaseEditorToolbarManager());
    }


}
