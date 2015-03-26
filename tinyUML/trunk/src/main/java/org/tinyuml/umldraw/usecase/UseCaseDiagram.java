package org.tinyuml.umldraw.usecase;

import java.util.HashMap;
import java.util.Map;

import org.tinyuml.model.ElementType;
import org.tinyuml.model.Relation;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlActor;
import org.tinyuml.model.UmlModel;
import org.tinyuml.model.UmlRelation;
import org.tinyuml.model.UmlUseCase;
import org.tinyuml.umldraw.shared.GeneralDiagram;
import org.tinyuml.umldraw.shared.UmlConnection;
import org.tinyuml.umldraw.shared.UmlDiagramElement;
import org.tinyuml.umldraw.structure.Association;
import org.tinyuml.umldraw.structure.SimpleAssociation;

/**
 * This class specializes on GeneralDiagram, providing the elements available in a use case diagram.
 *
 * @author Juan Manuel Romera
 * @version 1.0
 */
public class UseCaseDiagram extends GeneralDiagram {

  private static final long serialVersionUID = -722188541879347330L;

  /**
   * Constructor.
   * 
   * @param umlModel the Uml model
   */
  public UseCaseDiagram(UmlModel umlModel) {
    super(umlModel);
  }

  /**
   * {@inheritDoc}
   */
  protected Map<ElementType, UmlDiagramElement> setupElementPrototypeMap() {
    Map<ElementType, UmlDiagramElement> elementPrototypes =
        new HashMap<ElementType, UmlDiagramElement>();

    // Add actor prototype
    UmlActor actor = (UmlActor) UmlActor.getPrototype().clone();
    ActorElement actorPrototype = (ActorElement) ActorElement.getPrototype().clone();
    actor.setName("Actor 1");
    actorPrototype.setModelElement(actor);
    elementPrototypes.put(ElementType.ACTOR, actorPrototype);

    // Add useCase prototype
    UmlUseCase useCase = (UmlUseCase) UmlUseCase.getPrototype().clone();
    UseCaseElement useCasePrototype = (UseCaseElement) UseCaseElement.getPrototype().clone();
    useCase.setName("Use Case 1");
    useCasePrototype.setModelElement(useCase);
    elementPrototypes.put(ElementType.USE_CASE, useCasePrototype);

    return elementPrototypes;
  }

  /**
   * {@inheritDoc}
   */
  protected Map<RelationType, UmlConnection> setupConnectionPrototypeMap() {
    Map<RelationType, UmlConnection> connectionPrototypes =
        new HashMap<RelationType, UmlConnection>();

    UmlRelation fullnavigable = new UmlRelation();
    fullnavigable.setCanSetElement1Navigability(true);
    fullnavigable.setCanSetElement2Navigability(true);

    Association assocPrototype = (Association) SimpleAssociation.getPrototype().clone();
    assocPrototype.setRelation((Relation) fullnavigable.clone());
    connectionPrototypes.put(RelationType.ASSOCIATION, assocPrototype);

    return connectionPrototypes;
  }
}
