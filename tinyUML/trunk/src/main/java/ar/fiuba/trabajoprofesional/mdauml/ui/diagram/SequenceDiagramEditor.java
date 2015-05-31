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
package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.model.ElementType;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.util.MethodCall;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a specialized version of a DiagramEditor editing sequence
 * diagrams.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class SequenceDiagramEditor extends DiagramEditor {

    /**
     *
     */
    private static final long serialVersionUID = 5192396551407056080L;
    private static Map<String, MethodCall> selectorMap = new HashMap<String, MethodCall>();

    static {
        initSelectorMap();
    }

    /**
     * Constructor.
     *
     * @param aWindow  the main window
     * @param aDiagram the diagram
     */
    public SequenceDiagramEditor(Component aWindow, GeneralDiagram aDiagram) {
        super(aWindow, aDiagram);
    }

    /**
     * Initializes the selector map.
     */
    private static void initSelectorMap() {
        try {
            selectorMap.put("CREATE_LIFELINE", new MethodCall(
                SequenceDiagramEditor.class.getMethod("setCreationMode", ElementType.class),
                ElementType.LIFE_LINE));
            selectorMap.put("CREATE_MESSAGE", new MethodCall(SequenceDiagramEditor.class
                .getMethod("setCreateConnectionMode", RelationType.class), RelationType.MESSAGE));
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void editProperties(DiagramElement element) {
    }

    /**
     * {@inheritDoc}
     */
    @Override public void handleCommand(String command) {
        MethodCall methodcall = selectorMap.get(command);
        if (methodcall != null)
            methodcall.call(this);
        else
            super.handleCommand(command);
    }
}
