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
package ar.fiuba.trabajoprofesional.mdauml.model;

import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlDiagramElement;

import java.util.List;

/**
 * An interface to represent UML diagrams. Defining the UML within the model
 * offers the advantage that the UmlModel instance can be used as the root
 * of serialization.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface UmlDiagram extends NamedElement {
    List<UmlDiagramElement> getElements();
}
