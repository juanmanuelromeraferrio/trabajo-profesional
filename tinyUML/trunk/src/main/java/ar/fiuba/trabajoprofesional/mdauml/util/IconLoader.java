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
package ar.fiuba.trabajoprofesional.mdauml.util;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class accesses images for icons from the class path.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public final class IconLoader {

    private static IconLoader instance = new IconLoader();
    private Map<IconType, String> urlMap = new HashMap<IconType, String>();
    private Map<IconType, Icon> iconMap = new HashMap<IconType, Icon>();
    private Map<String, IconType> iconTypeMap = new HashMap<String, IconType>();

    /**
     * Private constructor.
     */
    private IconLoader() {
        for (IconType iconType : IconType.values()) {
            iconTypeMap.put(iconType.toString(), iconType);
        }
        urlMap
            .put(IconType.MOUSE_POINTER, "ar/fiuba/trabajoprofesional/mdauml/ui/mousepointer.png");
        urlMap.put(IconType.CLASS, "ar/fiuba/trabajoprofesional/mdauml/ui/class.png");
        urlMap.put(IconType.BOUNDARY, "ar/fiuba/trabajoprofesional/mdauml/ui/boundary.png");
        urlMap.put(IconType.CONTROL, "ar/fiuba/trabajoprofesional/mdauml/ui/control.png");
        urlMap.put(IconType.ENTITY, "ar/fiuba/trabajoprofesional/mdauml/ui/entity.png");
        urlMap.put(IconType.CLOSE_TAB, "ar/fiuba/trabajoprofesional/mdauml/ui/close-tab.png");
        urlMap.put(IconType.PACKAGE, "ar/fiuba/trabajoprofesional/mdauml/ui/package.png");
        urlMap.put(IconType.DEPENDENCY, "ar/fiuba/trabajoprofesional/mdauml/ui/dependency.png");
        urlMap.put(IconType.ASSOCIATION, "ar/fiuba/trabajoprofesional/mdauml/ui/association.png");
        urlMap.put(IconType.NOTE, "ar/fiuba/trabajoprofesional/mdauml/ui/note.png");
        urlMap.put(IconType.NOTE_CONNECTOR,
            "ar/fiuba/trabajoprofesional/mdauml/ui/note-connector.png");
        urlMap.put(IconType.AGGREGATION, "ar/fiuba/trabajoprofesional/mdauml/ui/aggregation.png");
        urlMap.put(IconType.COMPOSITION, "ar/fiuba/trabajoprofesional/mdauml/ui/composition.png");
        urlMap.put(IconType.INHERITANCE, "ar/fiuba/trabajoprofesional/mdauml/ui/inheritance.png");
        urlMap.put(IconType.INTERFACE_REALIZATION,
            "ar/fiuba/trabajoprofesional/mdauml/ui/interface-realization.png");
        urlMap.put(IconType.EXTEND, "ar/fiuba/trabajoprofesional/mdauml/ui/extend.png");
        urlMap.put(IconType.INCLUDE, "ar/fiuba/trabajoprofesional/mdauml/ui/include.png");
        urlMap.put(IconType.NEST, "ar/fiuba/trabajoprofesional/mdauml/ui/nest.png");
        urlMap.put(IconType.MESSAGE, "ar/fiuba/trabajoprofesional/mdauml/ui/message.png");
        urlMap.put(IconType.ACTOR, "ar/fiuba/trabajoprofesional/mdauml/ui/actor.png");
        urlMap.put(IconType.USE_CASE, "ar/fiuba/trabajoprofesional/mdauml/ui/usecase.png");

        urlMap.put(IconType.SYSTEM, "ar/fiuba/trabajoprofesional/mdauml/ui/system.png");

        urlMap.put(IconType.NEW, "org/fife/plaf/Office2003/new.gif");
        urlMap.put(IconType.OPEN, "org/fife/plaf/Office2003/open.gif");
        urlMap.put(IconType.SAVE, "org/fife/plaf/Office2003/save.gif");
        urlMap.put(IconType.CUT, "org/fife/plaf/Office2003/cut.gif");
        urlMap.put(IconType.COPY, "org/fife/plaf/Office2003/copy.gif");
        urlMap.put(IconType.PASTE, "org/fife/plaf/Office2003/paste.gif");
        urlMap.put(IconType.DELETE, "org/fife/plaf/Office2003/delete.gif");
        urlMap.put(IconType.UNDO, "org/fife/plaf/Office2003/undo.gif");
        urlMap.put(IconType.REDO, "org/fife/plaf/Office2003/redo.gif");
        urlMap.put(IconType.ABOUT, "org/fife/plaf/Office2003/about.gif");
        urlMap.put(IconType.CONVERT, "ar/fiuba/trabajoprofesional/mdauml/ui/convert.png");
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static IconLoader getInstance() {
        return instance;
    }

    /**
     * Returns the icon for the specified icon type.
     *
     * @param type the icon type
     * @return the icon
     */
    public Icon getIcon(IconType type) {
        if (!iconMap.containsKey(type)) {
            String urlstr = urlMap.get(type);
            if (urlstr != null) {
                iconMap.put(type, new ImageIcon(getClass().getClassLoader().getResource(urlstr)));
            }
        }
        return iconMap.get(type);
    }

    /**
     * Returns the icon for the specified icon type name.
     *
     * @param typeName the icon type name
     * @return the icon
     */
    public Icon getIcon(String typeName) {
        return getIcon(iconTypeMap.get(typeName));
    }

    /**
     * This enum type lists the available icon types.
     */
    public enum IconType {
        CLOSE_TAB,
        NEW, OPEN, SAVE, CUT, COPY, PASTE, DELETE, UNDO, REDO,
        ABOUT,
        MOUSE_POINTER,
        CLASS, PACKAGE, DEPENDENCY, ASSOCIATION, AGGREGATION,
        COMPOSITION, INHERITANCE, INTERFACE_REALIZATION, NOTE, NOTE_CONNECTOR,
        LIFELINE, MESSAGE, ACTOR, EXTEND, INCLUDE, USE_CASE, CONVERT, BOUNDARY, CONTROL, ENTITY, SYSTEM, NEST
    }
}
