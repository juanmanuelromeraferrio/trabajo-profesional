package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;


import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.ActorElement;

import java.util.List;

public class ViewXml {
    private ActorElement actorElement;

    /* private transient List<UmlDiagram> umlDiagrams;
     private transient List<UmlDiagram> openDiagrams;

     public List<UmlDiagram> getUmlDiagrams() {
         return umlDiagrams;
     }

     public void setUmlDiagrams(List<UmlDiagram> umlDiagrams) {
         this.umlDiagrams = umlDiagrams;
     }

     public List<UmlDiagram> getOpenDiagrams() {
         return openDiagrams;
     }

     public void setOpenDiagrams(List<UmlDiagram> openDiagrams) {
         this.openDiagrams = openDiagrams;
     }
 */
    public ActorElement getActorElement() {
        return actorElement;
    }

    public void setActorElement(ActorElement actorElement) {
        this.actorElement = actorElement;
    }
}
