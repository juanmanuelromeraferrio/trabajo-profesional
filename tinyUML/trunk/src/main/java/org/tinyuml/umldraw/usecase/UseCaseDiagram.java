package org.tinyuml.umldraw.usecase;

import java.util.HashMap;
import java.util.Map;

import org.tinyuml.model.ElementType;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlModel;
import org.tinyuml.umldraw.shared.GeneralDiagram;
import org.tinyuml.umldraw.shared.UmlConnection;
import org.tinyuml.umldraw.shared.UmlDiagramElement;

/**
 * This class specializes on GeneralDiagram, providing the elements available in
 * a use case diagram.
 *
 * @author Juan Manuel Romera
 * @version 1.0
 */
public class UseCaseDiagram extends GeneralDiagram {

	private static final long serialVersionUID = -722188541879347330L;

	/**
	 * Constructor.
	 * 
	 * @param umlModel
	 *            the Uml model
	 */
	public UseCaseDiagram(UmlModel umlModel) {
		super(umlModel);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Map<ElementType, UmlDiagramElement> setupElementPrototypeMap() {
		Map<ElementType, UmlDiagramElement> elementPrototypes = new HashMap<ElementType, UmlDiagramElement>();

		ActorElement actorPrototype = (ActorElement) ActorElement
				.getPrototype().clone();
		elementPrototypes.put(ElementType.ACTOR, actorPrototype);
		return elementPrototypes;
	}

	/**
	 * {@inheritDoc}
	 */
	protected Map<RelationType, UmlConnection> setupConnectionPrototypeMap() {
		Map<RelationType, UmlConnection> connectionPrototypes = new HashMap<RelationType, UmlConnection>();
		// connectionPrototypes.put(RelationType.NOTE_CONNECTOR,
		// NoteConnection.getPrototype());
		// UmlRelation messageRelation = new UmlRelation();
		// messageRelation.setName("message()");
		// SynchronousMessageConnection msgConn = (SynchronousMessageConnection)
		// SynchronousMessageConnection.getPrototype().clone();
		// msgConn.setRelation(messageRelation);
		// connectionPrototypes.put(RelationType.MESSAGE, msgConn);
		return connectionPrototypes;
	}
}
