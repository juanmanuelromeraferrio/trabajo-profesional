package ar.fiuba.trabajoprofesional.mdauml.ui;

import javax.swing.*;
import java.awt.*;

/**
 * A toolbar manager for the use case diagram editor.
 *
 * @author Juan Manuel Romera
 * @version 2.0
 */
public class UseCaseEditorToolbarManager extends AbstractToolbarManager {

    private ButtonGroup buttongroup;

    /**
     * Constructor.
     */
    public UseCaseEditorToolbarManager() {
        buttongroup = new ButtonGroup();
        JToolBar toolbar = getToolbar();
        toolbar.add(createToggleButtonWithName(buttongroup, "select"));
        toolbar.add(createToggleButtonWithName(buttongroup, "actor"));
        toolbar.add(createToggleButtonWithName(buttongroup, "usecase"));
        toolbar.addSeparator(new Dimension(10, 10));
        toolbar.add(createToggleButtonWithName(buttongroup, "association"));
        toolbar.add(createToggleButtonWithName(buttongroup, "inheritance"));
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
        return createToggleButton(aButtonGroup, "usecasetoolbar." + name);
    }
}
