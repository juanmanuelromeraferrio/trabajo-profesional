package ar.fiuba.trabajoprofesional.mdauml.draw;

import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.Association;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.Inheritance;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.Nest;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.NoteConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.Extend;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.Include;

public interface ConnectionVisitor {



    void addConcreteConnection(Connection connection) throws AddConnectionException;
    void addConcreteConnection(Nest connection) throws AddConnectionException;
    void addConcreteConnection(Inheritance connection) throws AddConnectionException;
    void addConcreteConnection(Association connection) throws AddConnectionException;
    void addConcreteConnection(Extend connection) throws AddConnectionException;
    void addConcreteConnection(Include connection) throws AddConnectionException;
    void addConcreteConnection(NoteConnection connection) throws AddConnectionException;

    boolean allowsConnection(Connection connection);
    boolean allowsConnection(Nest connection);
    boolean allowsConnection(Inheritance connection);
    boolean allowsConnection(Include connection);
    boolean allowsConnection(Association connection);
    boolean allowsConnection(Extend connection);
    boolean allowsConnection(NoteConnection connection);


    void removeConcreteConnection(Connection connection);
    void removeConcreteConnection(Nest connection);
    void removeConcreteConnection(Inheritance connection);
    void removeConcreteConnection(Association connection);
    void removeConcreteConnection(Extend connection);
    void removeConcreteConnection(Include connection);
    void removeConcreteConnection(NoteConnection connection);



}
