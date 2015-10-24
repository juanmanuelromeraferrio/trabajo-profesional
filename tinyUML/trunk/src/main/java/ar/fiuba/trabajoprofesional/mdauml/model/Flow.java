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

    UmlStep father = step.getFather();
    for (int i = index + 1; i < flow.size(); i++) {
      // Si tienen Padre solo incremento el index de
      // los que son hijos del mismo padre.
      if (father == null) {
        UmlStep umlStep = flow.get(i);
        if (umlStep.getFather() == null) {
          umlStep.incrementIndex();
        }
      }
    }

  }

  public UmlStep getStep(int index) {
    return flow.get(index);
  }

  public void removeStep(UmlStep step) {
    int index = flow.indexOf(step);
    UmlStep father = step.getFather();
    for (int i = index + 1; i < flow.size(); i++) {

      // Si no tiene padre Decremento el index
      if (father == null) {
        UmlStep umlStep = flow.get(i);
        if (umlStep.getFather() == null) {
          umlStep.decrementIndex();
        }
      }
    }

    flow.remove(step);

    // Remove Step To Father
    if (father != null) {
      father.removeChildrenStep(step);
    }

    // Remove Childrens to Flow
    removeChildrenToFlow(step);
  }

  private void removeChildrenToFlow(UmlStep umlStep) {
    for (UmlStep childrenStep : umlStep.getChildrens()) {
      removeChildrenToFlow(childrenStep);
      flow.remove(childrenStep);
    }
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

  private void addChildrenToFlow(UmlStep umlStep, int selectedStep) {
    flow.add(selectedStep, umlStep);
    for (UmlStep childrenStep : umlStep.getChildrens()) {
      addChildrenToFlow(childrenStep);
    }
  }

  private void addChildrenToFlow(UmlStep umlStep) {
    flow.add(umlStep);
    for (UmlStep childrenStep : umlStep.getChildrens()) {
      addChildrenToFlow(childrenStep);
    }
  }

  public void addChildrenStep(UmlStep fatherStep, UmlStep childrenStep) {
    fatherStep.addChildrenStep(childrenStep);
    addChildrenToFlow(childrenStep);
  }

  public void addChildrenStep(UmlStep fatherStep, UmlStep childrenStep, int selectedStep) {
    fatherStep.addChildrenStep(childrenStep, selectedStep);
    int flowIndex = flow.indexOf(fatherStep) + selectedStep + 1;
    addChildrenToFlow(childrenStep, flowIndex);
  }

  public void removeChildrenStep(UmlStep selectedStep, int selectedAlternativeStep) {
    selectedStep.removeChildrenStepByPosition(selectedAlternativeStep);
    flow.remove(selectedStep);
  }
}
