package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.List;
import java.util.Set;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.ActorElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseDiagram;

public class ViewXmlSerializer implements XmlSerializer {


    public ViewXmlSerializer() {
    }



    @Override public void write(Project project, String path) throws Exception {
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        XMLEncoder xmlEncoder = new XMLEncoder(bos);

        List<UmlDiagram> umlDiagrams = project.getModel().getDiagrams();
        ViewXml viewXml = new ViewXml();
        //viewXml.setUmlDiagrams(umlDiagrams);
        //viewXml.setOpenDiagrams(project.getOpenDiagrams());
        viewXml.setActorElement(
            (ActorElement) ((UseCaseDiagram) (umlDiagrams.get(0))).getChildren().get(0));

        ActorMock actor = new ActorMock();
        actor.setActor(
            ((ActorElement) (((UseCaseDiagram) (umlDiagrams.get(0))).getChildren().get(0)))
                .getActor());

        //xmlEncoder.writeObject(viewXml);
        xmlEncoder.writeObject(actor);

        xmlEncoder.close();

    }

    @Override public Object read(String path) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));

        ViewXml viewXml = (ViewXml) decoder.readObject();
        decoder.close();

        return viewXml;
    }


}
