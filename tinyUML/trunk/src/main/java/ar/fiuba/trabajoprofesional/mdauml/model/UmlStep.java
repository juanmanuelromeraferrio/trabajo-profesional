package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;


public abstract class UmlStep {

  protected UmlStep father;
  protected List<UmlStep> children;
  protected Integer index;

  protected String description;

  protected UmlStep(String description) {
    this.description = description;
    this.children = new ArrayList<UmlStep>();
  }

  public void setFather(UmlStep father) {
    this.father = father;
  }

  public UmlStep getFather() {
    return father;
  }

  public Integer getIndex() {
    return index;
  }

  public Integer getRealIndex() {
    if (father != null) {
      return father.getRealIndex() + index;
    } else {
      return index;
    }
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void incrementIndex() {
    this.index++;
  }

  public void decrementIndex() {
    this.index--;
  }

  public String getCompleteIndex() {
    if (this.father != null) {
      return father.getCompleteIndex() + "." + index.toString();
    } else {
      return index.toString();
    }
  }


  public void setIndex(int i) {
    this.index = i;

  }

  public void addChild(UmlStep step) {
    step.setFather(this);
    int size = children.size();
    step.setIndex(size + 1);
    children.add(step);
  }

  public void setChildren(List<UmlStep> children) {
    this.children.clear();
    for(UmlStep child : children)
      addChild(child);
  }

  public void addChild(UmlStep step, int selectedStep) {
    step.setFather(this);
    step.setIndex(selectedStep + 1);
    children.add(selectedStep, step);

    for (int i = selectedStep + 1; i < children.size(); i++) {
      children.get(i).incrementIndex();
    }
  }

  public void removeChild(UmlStep step) {
    int index = children.indexOf(step);
    for (int i = index + 1; i < children.size(); i++) {
      children.get(i).decrementIndex();
    }

    children.remove(step);
  }

  public void removeChildAt(int selectedAlternativeStep) {
    UmlStep umlStep = children.get(selectedAlternativeStep);
    this.removeChild(umlStep);
  }

  public UmlStep getChild(int index) {
    return children.get(index);
  }

  public List<UmlStep> getChildren() {
    return children;
  }

  public List<UmlStep> getDescendants() {
    List<UmlStep> result = new ArrayList<UmlStep>();

    for (UmlStep step : children) {
      result.add(step);
      result.addAll(step.getDescendants());
    }

    return result;
  }

  public abstract String showDescription();

  @Override
  public abstract UmlStep clone();

  public List<String> getCompleteDescription() {
    List<String> result = new ArrayList<String>();
    result.add(showDescription());
    for (UmlStep umlStep : children) {
      result.addAll(umlStep.getCompleteDescription());
    }

    return result;

  }


  public UmlStep findByIndex(int index, int count) {
    if (index == count) {
      return this;
    }

    count++;

    for (UmlStep step : children) {
      int totalSize = count + step.getTotalSize();
      if (totalSize <= index) {
        count = totalSize;
        continue;
      }
      return step.findByIndex(index, count);
    }

    return null;
  }

  public int getTotalSize() {
    int size = 1;
    for (UmlStep step : children) {
      size += step.getTotalSize();
    }

    return size;
  }


}
