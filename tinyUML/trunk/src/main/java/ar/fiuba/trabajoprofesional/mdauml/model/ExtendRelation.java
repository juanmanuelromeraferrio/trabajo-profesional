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