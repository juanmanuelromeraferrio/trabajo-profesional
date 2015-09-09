package ar.fiuba.trabajoprofesional.mdauml.persistence;


import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.ActorElement;

import java.util.List;

public class ViewPersistence {

     private List<UmlDiagram> umlDiagrams;


     public List<UmlDiagram> getUmlDiagrams() {
         return umlDiagrams;
     }

     public void setUmlDiagrams(List<UmlDiagram> umlDiagrams) {
         this.umlDiagrams = umlDiagrams;
     }


}
