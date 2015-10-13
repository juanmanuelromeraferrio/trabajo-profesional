package ar.fiuba.trabajoprofesional.mdauml.model;

public class IncludeRelation extends UmlRelation {

    private static final String LABEL = "<<include>>";


    public IncludeRelation(){
        super();
        this.setCanSetElement1Navigability(false);
        this.setCanSetElement2Navigability(false);
        this.setNavigableToElement1(false);
        this.setNavigableToElement2(true);

    }

    @Override
    public String getName(){
        return LABEL;
    }
    private boolean isInitialized(){
        return !(getElement2()==null || getElement1()==null );
    }
    @Override
    public int hashCode(){
        if(!isInitialized())
            return 1;
        return (getElement2().getName()+getElement1().getName()).hashCode();
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof IncludeRelation))
            return false;
        IncludeRelation other = (IncludeRelation) object;
        if(!isInitialized()|| !other.isInitialized())
            return false;

        return getElement2()==other.getElement2() && getElement1()== other.getElement1();
    }

}

