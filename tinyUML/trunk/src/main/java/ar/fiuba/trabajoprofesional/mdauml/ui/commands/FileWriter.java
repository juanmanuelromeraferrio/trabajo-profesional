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
package ar.fiuba.trabajoprofesional.mdauml.ui.commands;

import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.MessageFormat;

/**
 * This class is an abstract super class that provides useful functionality
 * for graphics exporter classes.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public abstract class FileWriter extends FileHandler {

    /**
     * Returns true if the file can be written. This method checks the existence
     * of the file and if it exists, the user will be asked whether to overwrite
     * the file.
     *
     * @param component the parent component for the message dialog
     * @param file      the file to write
     * @return true if the file does not yet exist or if the user confirmed
     * overwriting it, false if the file should not be written
     */
    protected boolean canWrite(Component component, File file) {
        if (file.exists()) {
            String message = Msg.get("dialog.replacefile.confirm.msg");
            message = MessageFormat.format(message, file.getName());
            String title = Msg.get("dialog.replacefile.confirm.title");
            return
                JOptionPane.showConfirmDialog(component, message, title, JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION;
        }
        return true;
    }
}
