package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlSerializer2;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable.ObjectXmlizable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.fiuba.trabajoprofesional.mdauml.persistence.Constants;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public class ModelXmlSerializer implements XmlSerializer {

    public ModelXmlSerializer() {
    }



    @Override public void write(Project project, String path) throws Exception {
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        XMLEncoder xmlEncoder = new XMLEncoder(bos);

        Set<UmlModelElement> elements = project.getModel().getElements();
        ModelXml modelXml = new ModelXml();
        Set<UmlModelElement> elements2 = new HashSet<UmlModelElement>();
        elements2.addAll(elements);

        modelXml.setElements(elements2);

        xmlEncoder.writeObject(modelXml);

        xmlEncoder.close();

    }


    @Override public Object read(String path) throws Exception {

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));

        ModelXml modelXml = (ModelXml) decoder.readObject();
        decoder.close();
        return modelXml;
    }


}
