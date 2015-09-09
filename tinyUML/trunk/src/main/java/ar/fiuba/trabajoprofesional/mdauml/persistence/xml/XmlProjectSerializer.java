package ar.fiuba.trabajoprofesional.mdauml.persistence.xml;

import ar.fiuba.trabajoprofesional.mdauml.exception.ObjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.persistence.serializer.ObjectSerializer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.serializer.ProjectSerializer;

/**
 * Created by ferro on 5/9/2015.
 */
public class XmlProjectSerializer extends ProjectSerializer {


    public XmlProjectSerializer(String path) throws ObjectSerializerException {
        super(path);
    }

    @Override
    protected ObjectSerializer buildObjectSerializer(String path ) throws ObjectSerializerException {
        return new XmlObjectSerializer(path);
    }



}
