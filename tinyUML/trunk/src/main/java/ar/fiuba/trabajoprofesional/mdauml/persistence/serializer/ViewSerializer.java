package ar.fiuba.trabajoprofesional.mdauml.persistence.serializer;


import java.util.List;
import java.util.Map;

import ar.fiuba.trabajoprofesional.mdauml.model.ElementType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.persistence.ViewPersistence;
import ar.fiuba.trabajoprofesional.mdauml.ui.ElementNameGenerator;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;


public class ViewSerializer implements Serializer {

    private ObjectSerializer serializer;


    public ViewSerializer(ObjectSerializer serializer ) {
        this.serializer = serializer;

    }



    @Override public void write(Project project) throws Exception {


        List<UmlDiagram> umlDiagrams = project.getModel().getDiagrams();
        ViewPersistence viewPersistence = new ViewPersistence();
        viewPersistence.setUmlDiagrams(umlDiagrams);

        serializer.writeObject(viewPersistence);


    }

    @Override public Object read() throws Exception {
        ViewPersistence viewPersistence = (ViewPersistence)serializer.readObject();
        Map<ElementType, Integer> map = ElementNameGenerator.getNameMap();


        return viewPersistence;
    }


}
