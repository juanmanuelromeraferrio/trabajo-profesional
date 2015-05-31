package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase.Flow.Step;

/**
 * This class represents an UML UseCase
 *
 * @author Juan Manuel Romera
 */
public class UmlUseCase extends AbstractUmlModelElement {


    /**
     *
     */
    private static final long serialVersionUID = -8599134739003834715L;
    private static UmlUseCase prototype;
    private String description = "";
    private Set<UmlActor> mainActors = new HashSet<UmlActor>();
    private Set<UmlActor> secondaryActors = new HashSet<UmlActor>();
    private Set<UmlActor> umlActors = new HashSet<UmlActor>();
    private Flow mainFLow = new Flow();
    private List<Flow> alternativeFlows = new ArrayList<Flow>();
    private List<String> preconditions = new ArrayList<String>();
    private List<String> postconditions = new ArrayList<String>();

    /**
     * Constructor.
     */
    private UmlUseCase() {
    }

    ;

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static UmlUseCase getPrototype() {
        if (prototype == null)
            prototype = new UmlUseCase();
        return prototype;
    }

    public Flow getMainFLow() {
        return mainFLow;
    }

    public void setMainFLow(Flow mainFLow) {
        this.mainFLow = mainFLow;
    }

    public void addMainFlowStep(Step step) {
        this.mainFLow.addStep(step);
    }

    public void removeMainFlowStep(Step step) {
        this.mainFLow.removeStep(step);
    }

    public List<Flow> getAlternativeFlows() {
        return alternativeFlows;
    }

    public void setAlternativeFlows(List<Flow> alternativeFlows) {
        this.alternativeFlows = new ArrayList<Flow>(alternativeFlows);
    }

    public void addAlternativeFlow(Flow alternativeFlow) {
        this.alternativeFlows.add(alternativeFlow);
    }

    public void removeAlternativeFlow(Flow alternativeFlow) {
        this.alternativeFlows.remove(alternativeFlow);
    }

    public List<String> getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(List<String> preconditions) {
        this.preconditions = new ArrayList<String>(preconditions);
    }

    public void addPrecondition(String precondition) {
        this.preconditions.add(precondition);
    }

    public void removePrecondition(String precondition) {
        this.preconditions.remove(precondition);
    }

    public List<String> getPostconditions() {
        return postconditions;
    }

    public void setPostconditions(List<String> postconditions) {
        this.postconditions = new ArrayList<String>(postconditions);
    }

    public void addPostcondition(String postcondition) {
        this.postconditions.add(postcondition);
    }

    public void removePostcondition(String postcondition) {
        this.postconditions.remove(postcondition);
    }

    public Set<UmlActor> getMainActors() {
        return mainActors;
    }

    public void setMainActors(Set<UmlActor> mainActors) {
        this.mainActors = new HashSet<UmlActor>(mainActors);
    }

    public void addMainActor(UmlActor actor) {
        this.mainActors.add(actor);
        this.umlActors.add(actor);
        this.secondaryActors.remove(actor);
    }

    public void removeMainActor(UmlActor actor) {
        this.mainActors.remove(actor);
    }

    public Set<UmlActor> getSecondaryActors() {
        return secondaryActors;
    }

    public void setSecondaryActors(Set<UmlActor> secondaryActors) {
        this.secondaryActors = new HashSet<UmlActor>(secondaryActors);
    }

    public void addSecondaryActor(UmlActor actor) {
        this.secondaryActors.add(actor);
        this.umlActors.add(actor);
        this.mainActors.remove(actor);
    }

    public void removeSecondaryActor(UmlActor actor) {
        this.secondaryActors.remove(actor);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UmlActor> getUmlActors() {
        return umlActors;
    }

    public void setUmlActors(Set<UmlActor> umlActors) {

        this.umlActors = umlActors;
    }

    public void addUmlActor(UmlActor actor) {

        this.umlActors.add(actor);
    }

    public void removeUmlActor(UmlActor actor) {

        this.umlActors.remove(actor);
    }

    @Override public Object clone() {
        UmlUseCase cloned = (UmlUseCase) super.clone();
        cloned.alternativeFlows = new ArrayList<>(this.alternativeFlows);
        cloned.description = this.description;
        cloned.mainActors = new HashSet<>(this.mainActors);
        cloned.secondaryActors = new HashSet<>(this.secondaryActors);
        cloned.umlActors = new HashSet<>(this.umlActors);
        cloned.mainFLow = (Flow) this.mainFLow.clone();
        cloned.preconditions = new ArrayList<>(this.preconditions);
        cloned.postconditions = new ArrayList<>(this.postconditions);
        return cloned;

    }


    public class Flow {
        private List<Step> flow = new ArrayList<Step>();

        public List<Step> getFlow() {
            return flow;
        }

        public void setFlow(List<Step> flow) {
            this.flow = new ArrayList<Step>(flow);
        }

        public void addStep(Step step) {
            flow.add(step);
        }

        public void removeStep(Step step) {
            flow.remove(step);
        }

        @Override public Object clone() {
            Flow cloned = new Flow();
            cloned.flow = new ArrayList<>(this.flow);
            return cloned;
        }


        public class Step {
            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }


    }

}
