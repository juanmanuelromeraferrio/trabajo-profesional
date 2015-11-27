package ar.fiuba.trabajoprofesional.mdauml.conversion.model;


import java.util.Set;

public interface  IConversionModel {

    Set<Boundary> getBoundaries();

    Set<Control> getControls();

    Set<Entity> getEntities();

    Set<SimpleRelation> getRelations();

}
