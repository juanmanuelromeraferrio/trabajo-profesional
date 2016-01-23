package ar.fiuba.trabajoprofesional.mdauml.model;

import ar.fiuba.trabajoprofesional.mdauml.exception.ElementNameAlreadyExist;

import java.util.ArrayList;
import java.util.List;

public abstract class UmlStereotypedClass extends UmlClass implements NameChangeListener{



    public UmlStereotypedClass(){
        UmlStereotype stereotype = new UmlStereotype();
        stereotype.setName(getStereotype());
        stereotype.addNameChangeListener(this);
        ArrayList<UmlStereotype> list = new ArrayList<>();
        list.add(stereotype);
        super.setStereotypes(list);
    }

    public abstract String getStereotype();



    public void nameChanged(NamedElement element) throws ElementNameAlreadyExist{
        if(!element.getName().equals(getStereotype()))
            element.setName(getStereotype());
    }


    @Override
    public void setStereotypes(List<UmlStereotype> stereotypes){
        UmlStereotype myStereotype = new UmlStereotype();
        myStereotype.setName(getStereotype());
        if(!stereotypes.contains(myStereotype)){
            myStereotype.addNameChangeListener(this);
            stereotypes.add(0,myStereotype);
        }else{
            stereotypes.get(stereotypes.indexOf(myStereotype)).addNameChangeListener(this);
        }
        super.setStereotypes(stereotypes);
    }

}
