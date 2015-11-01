package ar.fiuba.trabajoprofesional.mdauml.draw;

import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;

import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.Dependency;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.*;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.Extend;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.Include;

public interface ConnectionVisitor {


    void addConcreteConnection(Connection connection);
    void addConcreteConnection(Nest connection);
    void addConcreteConnection(Inheritance connection);
    void addConcreteConnection(Association connection);
    void addConcreteConnection(Extend connection);
    void addConcreteConnection(Include connection);
    void addConcreteConnection(NoteConnection connection);
    void addConcreteConnection(Dependency connection);



    void removeConcreteConnection(Connection connection);
    void removeConcreteConnection(Nest connection);
    void removeConcreteConnection(Inheritance connection);
    void removeConcreteConnection(Association connection);
    void removeConcreteConnection(Extend connection);
    void removeConcreteConnection(Include connection);
    void removeConcreteConnection(NoteConnection connection);
    void removeConcreteConnection(Dependency connection);


    boolean acceptsConnectionAsSource(RelationType relationType);
    void validateConnectionAsTarget(RelationType relationType,UmlNode node)throws AddConnectionException;
}
