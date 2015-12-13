package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;

public class UmlControl extends UmlClass {

    private static UmlControl prototype;
    /**
     * Constructor.
     */
    private UmlControl() {
        super();
        UmlStereotype stereotype = new UmlStereotype();
        stereotype.setName("<<control>>");
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
            prototype = new UmlControl();
        return prototype;
    }

    @Override
    public void setStereotypes(List<UmlStereotype> stereotypes){

    }



}
