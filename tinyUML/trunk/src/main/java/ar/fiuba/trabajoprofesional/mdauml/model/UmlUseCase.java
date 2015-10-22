package ar.fiuba.trabajoprofesional.mdauml.model;

import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.Extend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents an UML UseCase
 *
 * @author Juan Manuel Romera
 */
public class UmlUseCase extends PackageableUmlModelElement {


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

  private Set<ExtendRelation> extendRelations = new HashSet<>();
  private Set<IncludeRelation> includeRelations = new HashSet<>();


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

  public void addMainFlowStep(UmlStep step) {
    this.mainFlow.addStep(step);
  }

  public void removeMainFlowStep(UmlStep step) {
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
    this.mainActors.remove(actor);
    this.secondaryActors.remove(actor);
  }


  @Override
  public ElementType getElementType() {
    return ElementType.USE_CASE;
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

  public void addExtend(ExtendRelation extend) {
    extendRelations.add(extend);
  }

  public void removeExtend(ExtendRelation extend) {
    extendRelations.remove(extend);
  }
  public boolean isExtending(){
    return !extendRelations.isEmpty();
  }

  public void addInclude(IncludeRelation include) {
    includeRelations.add(include);
  }

  public void removeInclude(IncludeRelation include) {
    includeRelations.remove(include);
  }
  public boolean isIncluding(){
    return !includeRelations.isEmpty();
  }


}
