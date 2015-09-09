package ar.fiuba.trabajoprofesional.mdauml.persistence.serializer;

import java.beans.XMLDecoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;




import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.persistence.ModelPersistence;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlObjectSerializer;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public class ModelSerializer implements Serializer {

    private ObjectSerializer serializer;

    public ModelSerializer(ObjectSerializer serializer) {
        this.serializer=serializer;
    }



    @Override public void write(Project project) throws Exception {


        Set<UmlModelElement> elements = project.getModel().getElements();
        ModelPersistence modelPersistence = new ModelPersistence();

        modelPersistence.setElements(new HashSet<>(elements));


        serializer.writeObject(modelPersistence);


    }


    @Override public Object read() throws Exception {
        ModelPersistence modelSerializer = (ModelPersistence) serializer.readObject();
        return modelSerializer;
    }


}
