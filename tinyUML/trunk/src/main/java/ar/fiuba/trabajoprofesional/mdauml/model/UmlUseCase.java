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
  private Flow mainFlow = new Flow();
  private List<Flow> alternativeFlows = new ArrayList<Flow>();
  private List<String> preconditions = new ArrayList<String>();
  private List<String> postconditions = new ArrayList<String>();

  /**
   * Constructor.
   */
  public UmlUseCase() {}

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
    return mainFlow;
  }

  public void setMainFLow(Flow mainFLow) {
    this.mainFlow = mainFLow;
  }

  public void addMainFlowStep(Step step) {
    this.mainFlow.addStep(step);
  }

  public void removeMainFlowStep(Step step) {
    this.mainFlow.removeStep(step);
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



  @Override
  public Object clone() {
    UmlUseCase cloned = (UmlUseCase) super.clone();
    cloned.alternativeFlows = new ArrayList<>(this.alternativeFlows);
    cloned.description = this.description;
    cloned.mainActors = new HashSet<>(this.mainActors);
    cloned.secondaryActors = new HashSet<>(this.secondaryActors);
    cloned.umlActors = new HashSet<>(this.umlActors);
    cloned.mainFlow = (Flow) this.mainFlow.clone();
    cloned.preconditions = new ArrayList<>(this.preconditions);
    cloned.postconditions = new ArrayList<>(this.postconditions);
    return cloned;

  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof UmlUseCase && ((UmlUseCase) obj).getName().equals(this.getName()))
      return true;
    return false;
  }


  public static class Flow {
    private List<Step> flow = new ArrayList<Step>();

    public List<Step> getFlow() {
      return flow;
    }

    public void setFlow(List<Step> flow) {
      this.flow = new ArrayList<Step>(flow);
    }

    public void addStep(Step step) {
      int size = flow.size();
      step.setIndex(size + 1);
      flow.add(step);
    }

    public void addStep(Step step, int index) {
      step.setIndex(index + 1);
      flow.add(index, step);
      for (int i = index + 1; i < flow.size(); i++) {
        flow.get(i).incrementIndex();
      }

    }

    public Step getStep(int index) {
      return flow.get(index);
    }

    public void removeStep(Step step) {
      int index = flow.indexOf(step);
      for (int i = index + 1; i < flow.size(); i++) {
        flow.get(i).decrementIndex();
      }

      flow.remove(step);
    }

    @Override
    public Object clone() {
      Flow cloned = new Flow();
      cloned.flow = new ArrayList<>(this.flow);
      return cloned;
    }


    public static class Step {
      private Set<String> entities;
      private String type;
      private String description;
      private Integer index;


      public Step(String type, String description, Set<String> entities) {
        super();
        this.entities = entities;
        this.type = type;
        this.description = description;
      }

      public Step(String type, String description, Set<String> entities, Integer index) {
        super();
        this.entities = entities;
        this.type = type;
        this.description = description;
        this.index = index;
      }

      public Set<String> getEntities() {
        return entities;
      }

      public void setEntities(Set<String> entities) {
        this.entities = entities;
      }

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }

      public Integer getIndex() {
        return index;
      }

      public void setIndex(Integer index) {
        this.index = index;
      }

      public void incrementIndex() {
        this.index++;
      }

      public void decrementIndex() {
        this.index--;
      }

      public String showStep() {
        String filterDescription = this.getDescription().replace("@", "");
        return this.getIndex() + "- " + this.getType() + ": " + filterDescription;
      }


    }


  }

}
