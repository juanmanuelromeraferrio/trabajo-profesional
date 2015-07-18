package ar.fiuba.trabajoprofesional.mdauml.persistence;

import ar.fiuba.trabajoprofesional.mdauml.exception.ProjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface ProjectPersister {

    public void save(Project project, String path) throws ProjectSerializerException;

    public Project load(String path) throws ProjectSerializerException;


}
