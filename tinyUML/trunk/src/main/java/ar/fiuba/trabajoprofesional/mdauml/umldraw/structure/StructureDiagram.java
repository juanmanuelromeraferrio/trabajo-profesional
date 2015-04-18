/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with TinyUML; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */
package ar.fiuba.trabajoprofesional.mdauml.umldraw.structure;

import java.util.HashMap;
import java.util.Map;

import ar.fiuba.trabajoprofesional.mdauml.model.ElementType;
import ar.fiuba.trabajoprofesional.mdauml.model.Relation;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlClass;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlComponent;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlRelation;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.NoteConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.NoteElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlDiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.Association.AssociationType;

/**
 * This class specializes the GeneralDiagram, at the same time it implements the
 * DiagramElementFactory interface, as an efficient method to share the state of factory and
 * diagram.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class StructureDiagram extends GeneralDiagram {

  /**
   * Constructor.
   * 
   * @param umlModel the UmlModel
   */
  public StructureDiagram(UmlModel umlModel) {
    super(umlModel);
  }

  /**
   * {@inheritDoc}
   */
  protected Map<ElementType, UmlDiagramElement> setupElementPrototypeMap() {
    Map<ElementType, UmlDiagramElement> elementPrototypes =
        new HashMap<ElementType, UmlDiagramElement>();

    NoteElement notePrototype = (NoteElement) NoteElement.getPrototype().clone();
    elementPrototypes.put(ElementType.NOTE, notePrototype);

    // Add package prototype
    UmlPackage pkg = (UmlPackage) UmlPackage.getPrototype().clone();
    PackageElement pkgPrototype = (PackageElement) PackageElement.getPrototype().clone();
    pkg.setName("Package 1");
    pkgPrototype.setUmlPackage(pkg);
    elementPrototypes.put(ElementType.PACKAGE, pkgPrototype);

    // add component prototype
    UmlComponent comp = (UmlComponent) UmlComponent.getPrototype().clone();
    comp.setName("Component 1");
    ComponentElement compElem = (ComponentElement) ComponentElement.getPrototype().clone();
    compElem.setModelElement(comp);
    compElem.addNodeChangeListener(this);
    elementPrototypes.put(ElementType.COMPONENT, compElem);

    // Add class prototype
    UmlClass clss = (UmlClass) UmlClass.getPrototype().clone();
    clss.setName("Class 1");
    ClassElement classElem = (ClassElement) ClassElement.getPrototype().clone();
    classElem.setModelElement(clss);
    classElem.addNodeChangeListener(this);
    elementPrototypes.put(ElementType.CLASS, classElem);

    return elementPrototypes;
  }

  /**
   * {@inheritDoc}
   */
  protected Map<RelationType, UmlConnection> setupConnectionPrototypeMap() {
    Map<RelationType, UmlConnection> connectionPrototypes =
        new HashMap<RelationType, UmlConnection>();

    UmlRelation notnavigable = new UmlRelation();
    notnavigable.setCanSetElement1Navigability(false);
    notnavigable.setCanSetElement2Navigability(false);
    UmlRelation fullnavigable = new UmlRelation();
    fullnavigable.setCanSetElement1Navigability(true);
    fullnavigable.setCanSetElement2Navigability(true);
    UmlRelation targetnavigable = new UmlRelation();
    targetnavigable.setCanSetElement1Navigability(false);
    targetnavigable.setCanSetElement2Navigability(true);

    Dependency depPrototype = (Dependency) Dependency.getPrototype().clone();
    depPrototype.setRelation((Relation) notnavigable.clone());
    connectionPrototypes.put(RelationType.DEPENDENCY, depPrototype);

    Association assocPrototype = (Association) RectilinearAssociation.getPrototype().clone();
    assocPrototype.setRelation((Relation) fullnavigable.clone());
    connectionPrototypes.put(RelationType.ASSOCIATION, assocPrototype);

    Association compPrototype = (Association) RectilinearAssociation.getPrototype().clone();
    compPrototype.setAssociationType(AssociationType.COMPOSITION);
    compPrototype.setRelation((Relation) targetnavigable.clone());
    connectionPrototypes.put(RelationType.COMPOSITION, compPrototype);

    Association aggrPrototype = (Association) RectilinearAssociation.getPrototype().clone();
    aggrPrototype.setAssociationType(AssociationType.AGGREGATION);
    aggrPrototype.setRelation((Relation) targetnavigable.clone());
    connectionPrototypes.put(RelationType.AGGREGATION, aggrPrototype);

    Inheritance inheritPrototype = (Inheritance) Inheritance.getPrototype().clone();
    inheritPrototype.setRelation((Relation) notnavigable.clone());
    connectionPrototypes.put(RelationType.INHERITANCE, inheritPrototype);

    Inheritance interfRealPrototype = (Inheritance) Inheritance.getPrototype().clone();
    interfRealPrototype.setRelation((Relation) notnavigable.clone());
    interfRealPrototype.setIsDashed(true);
    connectionPrototypes.put(RelationType.INTERFACE_REALIZATION, interfRealPrototype);

    connectionPrototypes.put(RelationType.NOTE_CONNECTOR, NoteConnection.getPrototype());
    return connectionPrototypes;
  }
}
