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
    private boolean isInitialized(){
        return !(getElement2()==null || getElement1()==null );
    }
    @Override
    public int hashCode(){
        if(!isInitialized())
            return 1;
        return (getElement1().getName()+getElement2().getName()).hashCode();
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof InheritanceRelation))
            return false;
        InheritanceRelation other = (InheritanceRelation) object;
        if(!isInitialized()|| !other.isInitialized())
            return false;

        return getElement1()==other.getElement1() && getElement2()== other.getElement2();

    }


    public UmlModelElement getParent(){return getElement2();}
    public UmlModelElement getChild(){return getElement1();}





}
