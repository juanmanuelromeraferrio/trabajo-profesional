package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;


public abstract class UmlStep {

  protected UmlStep father;
  protected List<UmlStep> childrens;
  protected Integer index;

  protected String description;

  protected UmlStep(String description) {
    this.description = description;
    this.childrens = new ArrayList<UmlStep>();
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

  public void addChildrenStep(UmlStep step) {
    step.setFather(this);
    int size = childrens.size();
    step.setIndex(size + 1);
    childrens.add(step);
  }

  public void addChildrenStep(UmlStep step, int selectedStep) {
    step.setFather(this);
    step.setIndex(selectedStep + 1);
    childrens.add(selectedStep, step);

    for (int i = selectedStep + 1; i < childrens.size(); i++) {
      childrens.get(i).incrementIndex();
    }
  }

  public void removeChildrenStep(UmlStep step) {
    int index = childrens.indexOf(step);
    for (int i = index + 1; i < childrens.size(); i++) {
      childrens.get(i).decrementIndex();
    }

    childrens.remove(step);
  }

  public void removeChildrenStepByPosition(int selectedAlternativeStep) {
    UmlStep umlStep = childrens.get(selectedAlternativeStep);
    this.removeChildrenStep(umlStep);
  }

  public UmlStep getChildren(int index) {
    return childrens.get(index);
  }

  public List<UmlStep> getChildrens() {
    return childrens;
  }

  public List<UmlStep> getCompleteStepsChildrens() {
    List<UmlStep> result = new ArrayList<UmlStep>();

    for (UmlStep step : childrens) {
      result.add(step);
      result.addAll(step.getCompleteStepsChildrens());
    }

    return result;
  }

  public abstract String showDescription();

  @Override
  public abstract UmlStep clone();

  public List<String> getCompleteDescription() {
    List<String> result = new ArrayList<String>();
    result.add(showDescription());
    for (UmlStep umlStep : childrens) {
      result.addAll(umlStep.getCompleteDescription());
    }

    return result;

  }


  public UmlStep findByIndex(int index, int count) {
    if (index == count) {
      return this;
    }

    count++;

    for (UmlStep step : childrens) {
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
    for (UmlStep step : childrens) {
      size += step.getTotalSize();
    }

    return size;
  }


}
