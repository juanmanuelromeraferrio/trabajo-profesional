package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;

public class Flow {
  private List<UmlStep> flow = new ArrayList<UmlStep>();

  public List<UmlStep> getFlow() {
    return flow;
  }

  public void setFlow(List<UmlStep> flow) {
    this.flow = new ArrayList<UmlStep>(flow);
  }

  public void addStep(UmlStep step) {
    int size = flow.size();
    step.setIndex(size + 1);
    flow.add(step);
  }

  public void addStep(UmlStep step, int index) {
    step.setIndex(index + 1);
    flow.add(index, step);
    for (int i = index + 1; i < flow.size(); i++) {
      flow.get(i).incrementIndex();
    }

  }

  public UmlStep getStep(int index) {
    return flow.get(index);
  }

  public void removeStep(UmlStep step) {
    int index = flow.indexOf(step);
    for (int i = index + 1; i < flow.size(); i++) {
      flow.get(i).decrementIndex();
    }

    flow.remove(step);
  }

  @Override
  public Object clone() {
    Flow cloned = new Flow();
    cloned.flow = new ArrayList<UmlStep>(this.flow.size());
    for (UmlStep item : this.flow) {
      cloned.flow.add(item.clone());
    }

    return cloned;

  }

  public void addChildrenStep(UmlStep step, UmlStep childrenStep) {
    step.addChildrenStep(childrenStep);
  }

  public void removeChildrenStep(UmlStep selectedStep, int selectedAlternativeStep) {
    selectedStep.removeChildrenStepByPosition(selectedAlternativeStep);
  }
}
