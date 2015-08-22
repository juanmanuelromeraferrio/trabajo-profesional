package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;

import java.beans.XMLDecoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;




import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlObjectSerializer;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public class ModelXmlSerializer implements XmlSerializer {

    public ModelXmlSerializer() {
    }



    @Override public void write(Project project, String path) throws Exception {


        Set<UmlModelElement> elements = project.getModel().getElements();
        ModelXml modelXml = new ModelXml();
        Set<UmlModelElement> elements2 = new HashSet<UmlModelElement>();
        elements2.addAll(elements);

        modelXml.setObject(new ArrayList<List<String>>());
        modelXml.init();

        modelXml.setElements(elements2);

        XmlObjectSerializer xmlSerializer = new XmlObjectSerializer(path);

        xmlSerializer.writeObject(modelXml);


    }


    @Override public Object read(String path) throws Exception {

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));

        ModelXml modelXml = (ModelXml) decoder.readObject();
        decoder.close();
        return modelXml;
    }


}
