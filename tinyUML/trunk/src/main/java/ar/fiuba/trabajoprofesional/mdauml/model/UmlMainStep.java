package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UmlMainStep extends UmlStep {

  private Set<String> entities;
  private String type;

  protected UmlMainStep(String description) {
    super(description);
    this.setEntities(new HashSet<String>());
  }

  protected UmlMainStep(String description, String type) {
    this(description);
    this.setType(type);
  }


  public UmlMainStep(String description, String type, Set<String> entities) {
    this(description, type);
    this.setEntities(entities);
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

  @Override
  public String showDescription() {
    return super.getCompleteIndex() + ". " + type + ": " + super.getDescription().replace("@", "");
  }

  @Override
  public UmlStep clone() {

    Set<String> cloneEntities = new HashSet<String>(this.entities.size());
    for (String entity : this.entities) {
      cloneEntities.add(entity);
    }

    UmlStep cloned = new UmlMainStep(this.description, this.type, cloneEntities);
    cloned.index = this.index;

    List<UmlStep> cloneChildren = new ArrayList<UmlStep>(this.childrens.size());
    for (UmlStep step : this.childrens) {
      if (step instanceof UmlAlternativeStep) {
        UmlAlternativeStep umlAltStep = (UmlAlternativeStep) step;
        cloneChildren.add(umlAltStep.clone(cloned));
      }
    }

    cloned.childrens = cloneChildren;
    return cloned;
  }
}
