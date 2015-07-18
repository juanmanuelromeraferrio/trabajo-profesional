package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;



import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;


import java.util.HashSet;
import java.util.Set;



public class ModelXml {

    private Set<UmlModelElement> elements;

    public Set<UmlModelElement> getElements() {
        return elements;
    }

    public void setElements(Set<UmlModelElement> elements) {
        this.elements = elements;
    }

}
