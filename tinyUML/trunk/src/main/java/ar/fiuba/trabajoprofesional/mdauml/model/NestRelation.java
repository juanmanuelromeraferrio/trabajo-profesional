package ar.fiuba.trabajoprofesional.mdauml.model;

public class NestRelation extends UmlRelation{

    public NestRelation(){
        super();
        this.setCanSetElement1Navigability(false);
        this.setCanSetElement2Navigability(false);
        this.setNavigableToElement1(false);
        this.setNavigableToElement2(false);

    }

    @Override
    public String getName(){
        return "";
    }


    public UmlModelElement getNesting(){return getElement2();}
    public UmlModelElement getNested(){return getElement1();}



}
