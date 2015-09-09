package ar.fiuba.trabajoprofesional.mdauml.persistence;



import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;


import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;



public class ModelPersistence {

    private Set<UmlModelElement> elements;

    public Set<UmlModelElement> getElements() {
        return elements;
    }

    public void setElements(Set<UmlModelElement> elements) {
        this.elements = elements;
    }


}
