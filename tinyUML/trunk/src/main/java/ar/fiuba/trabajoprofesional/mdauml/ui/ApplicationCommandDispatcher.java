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

import ar.fiuba.trabajoprofesional.mdauml.exception.ProjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer.ProjectXmlSerializer;
import ar.fiuba.trabajoprofesional.mdauml.ui.commands.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.AppCommandListener;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;
import ar.fiuba.trabajoprofesional.mdauml.util.MethodCall;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the application global command handling. This was outfactored from
 * ApplicationState in order to keep it smaller.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ApplicationCommandDispatcher implements AppCommandListener {

    private ApplicationState appState;
    private Map<String, MethodCall> selectorMap = new HashMap<String, MethodCall>();

    /**
     * Constructor.
     *
     * @param anAppState the application state this dispatcher belongs to
     */
    public ApplicationCommandDispatcher(ApplicationState anAppState) {
        appState = anAppState;
        initSelectorMap();
    }

    /**
     * Initializes the selector map.
     */
    private void initSelectorMap() {
        try {
            selectorMap.put("NEW_MODEL", new MethodCall(getClass().getMethod("newModel")));
            selectorMap.put("NEW_STRUCTURE_DIAGRAM",
                new MethodCall(getClass().getMethod("openNewStructureEditor")));
            selectorMap.put("NEW_USE_CASE_DIAGRAM",
                new MethodCall(getClass().getMethod("openNewUseCaseEditor")));
            selectorMap.put("OPEN_MODEL", new MethodCall(getClass().getMethod("openModel")));
            selectorMap.put("SAVE_AS", new MethodCall(getClass().getMethod("saveAs")));
            selectorMap.put("SAVE", new MethodCall(getClass().getMethod("save")));
            selectorMap.put("EXPORT_GFX", new MethodCall(getClass().getMethod("exportGfx")));
            selectorMap.put("REDO", new MethodCall(getClass().getMethod("redo")));
            selectorMap.put("UNDO", new MethodCall(getClass().getMethod("undo")));
            selectorMap.put("DELETE", new MethodCall(getClass().getMethod("delete")));
            selectorMap.put("EDIT_SETTINGS", new MethodCall(getClass().getMethod("editSettings")));
            selectorMap.put("QUIT", new MethodCall(getClass().getMethod("quitApplication")));
            selectorMap.put("ABOUT", new MethodCall(getClass().getMethod("about")));
            selectorMap
                .put("HELP_CONTENTS", new MethodCall(getClass().getMethod("displayHelpContents")));
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void handleCommand(String command) {
        MethodCall methodcall = selectorMap.get(command);
        if (methodcall != null) {
            methodcall.call(this);
        }
    }

    /**
     * Creates a new model.
     */
    public void newModel() {
        if (canCreateNewModel()) {
            appState.newProject();
        }
    }

    /**
     * Determines if a new model can be created.
     *
     * @return true the model can be created, false otherwise
     */
    private boolean canCreateNewModel() {
        if (appState.isModified()) {
            return JOptionPane.showConfirmDialog(getShellComponent(),
                ApplicationResources.getInstance().getString("confirm.new.message"),
                ApplicationResources.getInstance().getString("confirm.new.title"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        }
        return true;
    }

    /**
     * Opens a new structure editor.
     */
    public void openNewStructureEditor() {
        appState.openNewStructureEditor();
    }

    /**
     * Opens a new sequence editor.
     */
    public void openNewSequenceEditor() {
        appState.openNewSequenceEditor();
    }

    /**
     * Opens a new use case editor.
     */
    public void openNewUseCaseEditor() {
        appState.openNewUseCaseEditor();
    }

    /**
     * Undo operation.
     */
    public void undo() {
        appState.getUndoManager().undo();
    }

    /**
     * Redo operation.
     */
    public void redo() {
        appState.getUndoManager().redo();
    }

    /**
     * Quits the application.
     */
    public void quitApplication() {
        appState.getShell().quitApplication();
    }

    /**
     * Opens the settings editor.
     */
    public void editSettings() {
        System.out.println("EDIT SETTINGS");
    }

    /**
     * Exports graphics as SVG.
     */
    public void exportGfx() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(getResourceString("dialog.exportgfx.title"));
        FileNameExtensionFilter svgFilter =
            new FileNameExtensionFilter("Scalable Vector Graphics file (*.svg)", "svg");
        FileNameExtensionFilter pngFilter =
            new FileNameExtensionFilter("Portable Network Graphics file (*.png)", "png");
        fileChooser.addChoosableFileFilter(svgFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showSaveDialog(getShellComponent()) == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getFileFilter() == svgFilter) {
                try {
                    SvgExporter exporter = new SvgExporter();
                    exporter.writeSVG(appState.getCurrentEditor(), fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(getShellComponent(), ex.getMessage(),
                        getResourceString("error.exportgfx.title"), JOptionPane.ERROR_MESSAGE);
                }
            } else if (fileChooser.getFileFilter() == pngFilter) {
                try {
                    PngExporter exporter = new PngExporter();
                    exporter.writePNG(appState.getCurrentEditor(), fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(getShellComponent(), ex.getMessage(),
                        getResourceString("error.exportgfx.title"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Returns the FileFilter for the TinyUML serialized model files.
     *
     * @return the FileFilter
     */
    private FileNameExtensionFilter createModelFileFilter() {
        return new FileNameExtensionFilter("TinyUML serialized model file (*.tsm)", "tsm");
    }

    /**
     * Opens a MdaUML model.
     */
    public void openModel() {
        if (canOpen()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(getResourceString("dialog.openmodel.title"));
            fileChooser.addChoosableFileFilter(createModelFileFilter());
            if (fileChooser.showOpenDialog(getShellComponent()) == JFileChooser.APPROVE_OPTION) {
                try {
                    File currentFile = fileChooser.getSelectedFile();
                    Project openProject = (Project) ProjectXmlSerializer.getInstace()
                        .read(currentFile.getAbsolutePath());
                    appState.restoreFromProject(openProject);
                    appState.setCurrentFile(currentFile);
                } catch (ProjectSerializerException e) {
                    JOptionPane.showMessageDialog(getShellComponent(), e.getMessage(),
                        getResourceString("error.loadproject.title"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Checks if application can be quit safely.
     *
     * @return true if can quit safely, false otherwise
     */
    private boolean canOpen() {
        if (appState.isModified()) {
            return JOptionPane.showConfirmDialog(getShellComponent(),
                ApplicationResources.getInstance().getString("confirm.open.message"),
                ApplicationResources.getInstance().getString("confirm.open.title"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        }
        return true;
    }

    /**
     * Saves the model with a file chooser.
     */
    public void saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(getResourceString("dialog.saveas.title"));
        fileChooser.addChoosableFileFilter(createModelFileFilter());
        if (fileChooser.showSaveDialog(getShellComponent()) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File currentFile = saveModelFile(selectedFile);
            appState.setCurrentFile(currentFile);
        }
    }

    /**
     * Saves immediately if possible.
     */
    public void save() {
        if (appState.getCurrentFile() == null) {
            saveAs();
        } else {
            saveModelFile(appState.getCurrentFile());
        }
    }

    /**
     * Writes the current model file. The returned file is different if the input file does not have
     * the proper extension.
     *
     * @param file the file to write
     * @return the file that was written
     */
    private File saveModelFile(File file) {
        try {
            ProjectXmlSerializer.getInstace()
                .write(appState.createProjectForWrite(), file.getAbsolutePath());
            appState.getUndoManager().discardAllEdits();
            appState.updateMenuAndToolbars();
        } catch (ProjectSerializerException e) {
            JOptionPane.showMessageDialog(getShellComponent(), e.getMessage(),
                getResourceString("error.saveproject.title"), JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String projectPath =
            ProjectXmlSerializer.getInstace().getProjectPath(file.getAbsolutePath());
        return new File(projectPath);
    }

    /**
     * Deletes the current selection.
     */
    public void delete() {
        if (appState.getCurrentFocusedComponent() instanceof DiagramTree) {
            DiagramTree tree = (DiagramTree) appState.getCurrentFocusedComponent();
            if (tree.getSelectionPath() != null) {
                DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                DeleteDiagramCommand command = new DeleteDiagramCommand(appState.getUmlModel(),
                    (UmlDiagram) node.getUserObject());
                appState.execute(command);
            }
        } else if (appState.getCurrentEditor() != null) {
            appState.getCurrentEditor().deleteSelection();
        }
    }

    /**
     * Shows the about dialog.
     */
    public void about() {
        JOptionPane.showMessageDialog(getShellComponent(), getResourceString("dialog.about.text"),
            getResourceString("dialog.about.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the help contents.
     */
    public void displayHelpContents() {
        try {
            URI helpUri = new URI("http://www.tinyuml.org/Wikka/UserDocs");
            Desktop.getDesktop().browse(helpUri);
        } catch (IOException ex) {
            JOptionPane
                .showMessageDialog(getShellComponent(), getResourceString("error.nohelp.message"),
                    getResourceString("error.nohelp.title"), JOptionPane.ERROR_MESSAGE);
        } catch (URISyntaxException ignore) {
            ignore.printStackTrace();
        }
    }

    // ************************************************************************
    // ******* General helpers
    // *************************************

    /**
     * Returns the shell component.
     *
     * @return the shell component
     */
    private Component getShellComponent() {
        return appState.getShell().getShellComponent();
    }

    /**
     * Returns the specified resource as a String object.
     *
     * @param property the property name
     * @return the property value
     */
    private String getResourceString(String property) {
        return ApplicationResources.getInstance().getString(property);
    }
}
