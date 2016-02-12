package ar.fiuba.trabajoprofesional.mdauml.model;


public class AlternativeFlow extends Flow {

    private UmlStep entryStep;
    private String enrtyCondition;
    private UmlStep returnStep;
    private String name;



    @Override
    public String toString(){
        return getName();
    }


    public UmlStep getEntryStep() {
        return entryStep;
    }

    public void setEntryStep(UmlStep entryStep) {
        this.entryStep = entryStep;
    }

    public String getEnrtyCondition() {
        return enrtyCondition;
    }

    public void setEnrtyCondition(String enrtyCondition) {
        this.enrtyCondition = enrtyCondition;
    }

    public UmlStep getReturnStep() {
        return returnStep;
    }

    public void setReturnStep(UmlStep returnStep) {
        this.returnStep = returnStep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
