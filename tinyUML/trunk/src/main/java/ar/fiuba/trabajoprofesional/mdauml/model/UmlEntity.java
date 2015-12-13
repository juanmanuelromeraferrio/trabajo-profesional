package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;

public class UmlEntity extends UmlClass {

    private static UmlEntity prototype;
    /**
     * Constructor.
     */
    private UmlEntity() {
        super();
        UmlStereotype stereotype = new UmlStereotype();
        stereotype.setName("<<entity>>");
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
            prototype = new UmlEntity();
        return prototype;
    }

    @Override
    public void setStereotypes(List<UmlStereotype> stereotypes){

    }



}
