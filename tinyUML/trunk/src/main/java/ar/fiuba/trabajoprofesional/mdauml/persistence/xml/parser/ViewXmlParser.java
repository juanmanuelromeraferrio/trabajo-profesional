package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.parser;

import java.io.File;

import org.w3c.dom.Document;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public class ViewXmlParser implements XmlParser {

    private Project project;

    public ViewXmlParser() {
    }

    public ViewXmlParser(Project project) {
        this.project = project;
    }

    @Override public Document generateXml(Project project) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Project parse(File file) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


}
