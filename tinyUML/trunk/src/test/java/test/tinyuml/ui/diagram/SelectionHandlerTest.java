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
package test.tinyuml.ui.diagram;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.tinyuml.ui.diagram.DiagramEditor;
import org.tinyuml.ui.diagram.SelectionHandler;
import org.tinyuml.umldraw.shared.GeneralDiagram;

/**
 * A test class for SelectionHandler.
 * @author Wei-ju Wu
 * @version 1.0
 */
public class SelectionHandlerTest extends MockObjectTestCase {
  
  private SelectionHandler handler;
  private Mock mockEditor = mock(DiagramEditor.class);
  private Mock mockDiagram = mock(GeneralDiagram.class);

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setUp() throws Exception {
    mockEditor.expects(once()).method("getDiagram")
      .will(returnValue(mockDiagram.proxy()));
    handler = new SelectionHandler((DiagramEditor) mockEditor.proxy());
  }
  
  public void testIt() {
    
  }
}
