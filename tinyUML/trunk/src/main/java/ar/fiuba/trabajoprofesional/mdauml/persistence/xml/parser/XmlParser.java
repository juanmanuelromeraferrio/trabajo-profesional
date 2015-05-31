package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.parser;

import java.io.File;

import org.w3c.dom.Document;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface XmlParser {

    public Project parse(File file) throws Exception;

    public Document generateXml(Project project) throws Exception;


}
