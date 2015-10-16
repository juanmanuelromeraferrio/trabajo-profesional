package ar.fiuba.trabajoprofesional.mdauml.model;


public class InheritanceRelation extends UmlRelation {

    public InheritanceRelation(){
        super();
        this.setCanSetElement1Navigability(false);
        this.setCanSetElement2Navigability(false);
        this.setNavigableToElement1(false);
        this.setNavigableToElement2(true);

    }

    @Override
    public String getName(){
        return "";
    }


    public UmlModelElement getParent(){return getElement2();}
    public UmlModelElement getChild(){return getElement1();}





}
