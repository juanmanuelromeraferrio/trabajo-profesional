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
package ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands;

import ar.fiuba.trabajoprofesional.mdauml.draw.Label;
import ar.fiuba.trabajoprofesional.mdauml.util.Command;

import javax.swing.undo.AbstractUndoableEdit;

/**
 * This class represents a reversible operation that sets a Label to a new
 * text.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class SetLabelTextCommand extends AbstractUndoableEdit implements Command {

    private Label label;
    private String text, oldText;

    /**
     * Constructor.
     *
     * @param aLabel the Label
     * @param aText  the new text
     */
    public SetLabelTextCommand(Label aLabel, String aText) {
        label = aLabel;
        text = aText;
        oldText = aLabel.getText();
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        label.setText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void redo() {
        super.redo();
        run();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void undo() {
        super.undo();
        label.setText(oldText);
    }
}
