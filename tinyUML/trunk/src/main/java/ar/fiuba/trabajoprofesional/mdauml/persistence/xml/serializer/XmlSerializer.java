package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;

import java.io.File;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface XmlSerializer {

    public Object read(String path) throws Exception;

    public void write(Project project, String path) throws Exception;


}
