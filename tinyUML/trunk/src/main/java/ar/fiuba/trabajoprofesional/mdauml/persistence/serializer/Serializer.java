package ar.fiuba.trabajoprofesional.mdauml.persistence.serializer;

import java.io.File;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface Serializer {

    public Object read() throws Exception;

    public void write(Project project) throws Exception;


}
