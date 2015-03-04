package org.tinyuml.ui;

import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * A toolbar manager for the use case diagram editor.
 *
 * @author Juan Manuel Romera
 * @version 1.0
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
    toolbar.addSeparator(new Dimension(10, 10));
    toolbar.add(createToggleButtonWithName(buttongroup, "usecase"));
    doClick("SELECT_MODE");
  }

  /**
   * Creates the specified toggle button.
   * @param aButtonGroup an optional ButtonGroup to add to
   * @param name the toggle button name
   * @return the toggle button
   */
  private JToggleButton createToggleButtonWithName(ButtonGroup aButtonGroup,
    String name) {
    return createToggleButton(aButtonGroup, "usecasetoolbar." + name);
  }
}
