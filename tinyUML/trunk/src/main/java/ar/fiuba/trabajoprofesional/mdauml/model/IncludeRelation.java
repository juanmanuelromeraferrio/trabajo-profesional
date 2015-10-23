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

}
