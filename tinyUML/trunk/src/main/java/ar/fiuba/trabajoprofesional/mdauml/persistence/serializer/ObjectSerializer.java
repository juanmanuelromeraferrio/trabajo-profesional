package ar.fiuba.trabajoprofesional.mdauml.persistence.serializer;


public interface ObjectSerializer {

    public Object readObject( ) throws Exception;
    public void writeObject(Object obj) throws Exception;

}
