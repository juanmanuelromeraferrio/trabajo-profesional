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
package ar.fiuba.trabajoprofesional.mdauml.umldraw.sequence;

import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class specializes on GeneralDiagram, providing the elements available
 * in a sequence diagram.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class SequenceDiagram extends GeneralDiagram {

  /**
   * Constructor.
   * @param umlModel the Uml model
   */
  public SequenceDiagram(UmlModel umlModel) {
    super(umlModel);
  }

  /**
   * {@inheritDoc}
   */
  protected Map<ElementType, UmlDiagramElement> setupElementPrototypeMap() {
    Map<ElementType, UmlDiagramElement> elementPrototypes =
      new HashMap<ElementType, UmlDiagramElement>();

    NoteElement notePrototype = (NoteElement)
      NoteElement.getPrototype().clone();
    elementPrototypes.put(ElementType.NOTE, notePrototype);
    LifeLineElement lifeLinePrototype = (LifeLineElement)
      LifeLineElement.getPrototype().clone();
    UmlLifeLine umlLifeLine = (UmlLifeLine) UmlLifeLine.getPrototype().clone();
    lifeLinePrototype.setModelElement(umlLifeLine);
    umlLifeLine.setName("Lifeline 1");
    elementPrototypes.put(ElementType.LIFE_LINE, lifeLinePrototype);
    return elementPrototypes;
  }

  /**
   * {@inheritDoc}
   */
  protected Map<RelationType, UmlConnection> setupConnectionPrototypeMap() {
    Map<RelationType, UmlConnection> connectionPrototypes =
      new HashMap<RelationType, UmlConnection>();
    connectionPrototypes.put(RelationType.NOTE_CONNECTOR,
      NoteConnection.getPrototype());
    UmlRelation messageRelation = new UmlRelation();
    messageRelation.setName("message()");
    SynchronousMessageConnection msgConn = (SynchronousMessageConnection)
      SynchronousMessageConnection.getPrototype().clone();
    msgConn.setRelation(messageRelation);
    connectionPrototypes.put(RelationType.MESSAGE, msgConn);
    return connectionPrototypes;
  }
}
