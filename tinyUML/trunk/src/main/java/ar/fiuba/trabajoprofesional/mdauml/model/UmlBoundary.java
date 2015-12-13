package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;

public class UmlBoundary extends UmlClass {

    private static UmlBoundary prototype;
    /**
     * Constructor.
     */
    private UmlBoundary() {
        super();
        UmlStereotype stereotype = new UmlStereotype();
        stereotype.setName("<<boundary>>");
        ArrayList<UmlStereotype> list = new ArrayList<>();
        list.add(stereotype);
        super.setStereotypes(list);
    }

    /**
     * Returns the Prototype instance of the UmlClass.
     *
     * @return the Prototype instance
     */
    public static UmlClass getPrototype() {
        if (prototype == null)
            prototype = new UmlBoundary();
        return prototype;
    }

    @Override
    public void setStereotypes(List<UmlStereotype> stereotypes){

    }



}
