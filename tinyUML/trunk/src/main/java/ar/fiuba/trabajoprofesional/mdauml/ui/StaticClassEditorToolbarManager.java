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

import javax.swing.*;
import java.awt.*;

/**
 * This class manages the a toolbar for a static class dialog.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class StaticClassEditorToolbarManager extends AbstractToolbarManager {

    private ButtonGroup buttongroup;

    /**
     * Constructor.
     */
    public StaticClassEditorToolbarManager() {
        buttongroup = new ButtonGroup();
        JToolBar toolbar = getToolbar();
        toolbar.add(createToggleButtonWithName(buttongroup, "select"));
        toolbar.add(createToggleButtonWithName(buttongroup, "package"));
        toolbar.add(createToggleButtonWithName(buttongroup, "class"));
        toolbar.add(createToggleButtonWithName(buttongroup, "component"));
        toolbar.addSeparator(new Dimension(10, 10));
        toolbar.add(createToggleButtonWithName(buttongroup, "dependency"));
        toolbar.add(createToggleButtonWithName(buttongroup, "association"));
        toolbar.add(createToggleButtonWithName(buttongroup, "aggregation"));
        toolbar.add(createToggleButtonWithName(buttongroup, "composition"));
        toolbar.add(createToggleButtonWithName(buttongroup, "inheritance"));
        toolbar.add(createToggleButtonWithName(buttongroup, "interfreal"));
        toolbar.addSeparator(new Dimension(10, 10));
        toolbar.add(createToggleButtonWithName(buttongroup, "note"));
        toolbar.add(createToggleButtonWithName(buttongroup, "noteconnector"));
        doClick("SELECT_MODE");
    }

    /**
     * Creates the specified toggle button.
     *
     * @param aButtonGroup an optional ButtonGroup to add to
     * @param name         the toggle button name
     * @return the toggle button
     */
    private JToggleButton createToggleButtonWithName(ButtonGroup aButtonGroup, String name) {
        return createToggleButton(aButtonGroup, "statictoolbar." + name);
    }
}
