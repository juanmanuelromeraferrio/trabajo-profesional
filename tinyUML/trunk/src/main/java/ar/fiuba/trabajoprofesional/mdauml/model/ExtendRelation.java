package ar.fiuba.trabajoprofesional.mdauml.model;


public class ExtendRelation extends UmlRelation {

    private static final String LABEL = "<<extend>>";
    private UmlStep extentionPoint;
    private String condition;

    public ExtendRelation(){
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
        if(!(object instanceof ExtendRelation))
            return false;
        ExtendRelation other = (ExtendRelation) object;
        if(!isInitialized()|| !other.isInitialized())
            return false;

        return getElement2()==other.getElement2() && getElement1()== other.getElement1();
    }

    
    public UmlStep getExtentionPoint() {
        return extentionPoint;
    }

    public void setExtentionPoint(UmlStep extentionPoint) {
        this.extentionPoint = extentionPoint;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
