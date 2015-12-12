/**
 * Copyright 2007 Wei-ju Wu
 * <p/>
 * This file is part of TinyUML.
 * <p/>
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p/>
 * TinyUML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with TinyUML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ar.fiuba.trabajoprofesional.mdauml.ui;

import ar.fiuba.trabajoprofesional.mdauml.conversion.impl.ConverterImpl;
import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionException;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * This class implements the Application frame. The top-level UI elements are
 * created here. Application events that affect the entire application are
 * handled here, local event handlers are also installed.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class AppFrame extends JFrame implements ApplicationShell {

    private transient ApplicationState appState;
    private static  AppFrame instance= null;
    public static AppFrame get(){
        if(instance==null) {
            instance = new AppFrame();
            instance.init();
        }

        return instance;

    }

    /**
     * Creates a new instance of AppFrame.
     */
    private AppFrame() {
        setTitle(Msg.get("application.title"));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        addWindowListener(new WindowAdapter() {
            /**
             * {@inheritDoc}
             */
            public void windowClosing(WindowEvent e) {
                quitApplication();
            }
        });


        //setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void init(){
        appState = new ApplicationState();
        appState.init();
        pack();
        appState.scheduleMemTimer();
    }


    /**
     * {@inheritDoc}
     */
    public DiagramEditor getCurrentEditor() {
        return appState.getCurrentEditor();
    }

    /**
     * {@inheritDoc}
     */
    public MenuManager getMenuManager() {
        return appState.getMenuManager();
    }

    /**
     * {@inheritDoc}
     */
    public Component getShellComponent() {
        return this;
    }


    // ************************************************************************
    // **** Event listeners
    // *****************************************

    /**
     * Call this method to exit this application in a clean way.
     */
    public void quitApplication() {
        if (canQuit()) {
            appState.stopThreads();
            dispose();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Checks if application can be quit safely.
     *
     * @return true if can quit safely, false otherwise
     */
    private boolean canQuit() {
//        try {
//            new ConverterImpl().convert(appState.createProjectForWrite());
//        } catch (ConversionException e) {
//            e.printStackTrace();
//        }
        if (appState.isModified()) {
            return JOptionPane.showConfirmDialog(this,
                Msg.get("confirm.quit.message"),
                Msg.get("confirm.quit.title"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void setTitle(String projectName ) {
        super.setTitle(projectName + " - " + Msg.get("application.title"));

    }

    public ApplicationState getAppState() {
        return appState;
    }
}
