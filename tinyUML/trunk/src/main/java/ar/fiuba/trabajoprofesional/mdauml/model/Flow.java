package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flow {
  protected List<UmlStep> flow = new ArrayList<UmlStep>();

  public List<UmlStep> getFlow() {
    return flow;
  }

  public void setFlow(List<UmlStep> flow) {
    this.flow = new ArrayList<UmlStep>(flow);
  }


  public void addStep(UmlStep step) {
    int index = getIndex();
    step.setIndex(index + 1);
    flow.add(step);
  }

  private int getIndex() {
    int index = 0;
    for (UmlStep step : flow) {
      if (step.getFather() == null) {
        index = step.getIndex();
      }
    }

    return index;
  }

  public void addStep(UmlStep step, int indexReal, int indexFlow) {
    step.setIndex(indexReal);
    flow.add(indexFlow, step);

    UmlStep father = step.getFather();
    for (int i = indexFlow + 1; i < flow.size(); i++) {
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

  public int getSize(){
    int size = 0;
    for (UmlStep step : flow)
      size += step.getTotalSize();

    return size;
  }

  public UmlStep getStep(int index) {
    UmlStep stepResult = null;
    int size = 0;
    for (UmlStep step : flow) {
      int totalSize = size + step.getTotalSize();
      if (totalSize <= index) {
        size = totalSize;
        continue;
      }
      return step.findByIndex(index, size);
    }

    return stepResult;
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
      father.removeChild(step);
    }

    // Remove Childrens to Flow
    removeChildrenToFlow(step);
  }

  private void removeChildrenToFlow(UmlStep umlStep) {
    for (UmlStep childrenStep : umlStep.getChildren()) {
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

  public List<UmlStep> getAllSteps(){
    List<UmlStep> steps = new ArrayList<>();
    for(UmlStep step : flow){
      steps.add(step);
      steps.addAll(step.getDescendants());
    }
    return steps;
  }

  private void addChildrenToFlow(UmlStep umlStep, int selectedStep) {
    // flow.add(selectedStep, umlStep);
    // for (UmlStep childrenStep : umlStep.getChildren()) {
    // int index = getFlowIndexForChildrenStep(childrenStep);// childrenStep.getRealIndex() - 1;
    // addChildrenToFlow(childrenStep, index);
    // }
  }

  public void addChildrenStep(UmlStep fatherStep, UmlStep childrenStep) {
    fatherStep.addChild(childrenStep);
    int index = getFlowIndexForChildrenStep(childrenStep);// childrenStep.getRealIndex() - 1;
    addChildrenToFlow(childrenStep, index);
  }

  private int getFlowIndexForChildrenStep(UmlStep searchStep) {
    UmlStep father = searchStep.getFather();
    int fatherIndex = flow.indexOf(father);
    return indexOfStep(searchStep, father, fatherIndex);
  }


  public int getFlowIndex(UmlStep searchStep) {

    return flow.indexOf(searchStep);
    // int index = start;
    // for (UmlStep umlStep : flow) {
    // if (umlStep.equals(searchStep)) {
    // return index;
    // }
    //
    // index++;
    // index = getFlowIndex(searchStep, start);
    // }
    //
    // return index;
  }

  private int indexOfStep(UmlStep searchStep, UmlStep father, int start) {
    int result = start;
    for (UmlStep children : father.getChildren()) {
      if (children.equals(searchStep)) {
        return result + 1;
      }
      result++;
      result = indexOfStep(searchStep, children, result);
    }
    return result;
  }

  public void addChildrenStep(UmlStep fatherStep, UmlStep childrenStep, int selectedStep) {
    fatherStep.addChild(childrenStep, selectedStep);
    int flowIndex = flow.indexOf(fatherStep) + selectedStep + 1;
    addChildrenToFlow(childrenStep, flowIndex);
  }

  public void removeChildrenStep(UmlStep selectedStep, int selectedAlternativeStep) {
    selectedStep.removeChildAt(selectedAlternativeStep);
    flow.remove(selectedStep);
  }

  public Set<String> getAllEntities() {
    return getAllEntities(flow);
  }

  public Set<String> getAllEntities(List<UmlStep> steps) {
    Set<String> allEntities = new HashSet<>();
    for (UmlStep step : steps) {
      if (step instanceof UmlMainStep) {
        Set<String> stepEntites = ((UmlMainStep) step).getEntities();
        allEntities.addAll(stepEntites);
      }
      allEntities.addAll(getAllEntities(step.getChildren()));
    }
    return allEntities;
  }

  public void replaceEntity(String original, String replacement) {
    replaceEntity(original, replacement, flow);
  }

  public void replaceEntity(String original, String replacement, List<UmlStep> steps) {
    for (UmlStep step : steps) {
      if (step instanceof UmlMainStep) {
        Set<String> replacedEntities = new HashSet<>();
        for (String entity : ((UmlMainStep) step).getEntities()) {
          if (entity.equals(original))
            replacedEntities.add(replacement);
          else
            replacedEntities.add(entity);
        }
        ((UmlMainStep) step).setEntities(replacedEntities);
      }
      replaceEntity(original, replacement, step.getChildren());
    }
  }

  public void replaceInclude(UmlModelElement included){
    List<UmlStep> replacedFlow = new ArrayList<>();
    replaceInclude(included,flow,replacedFlow);
    this.flow=replacedFlow;
  }
  public void replaceInclude(UmlModelElement included, List<UmlStep> steps, List<UmlStep> newSteps) {

    for (UmlStep step : steps) {
      UmlStep newStep = step.clone();
      if (step instanceof IncludeStep) {
        if(((IncludeStep) step).getIncluded().equals(included)) {
          newStep =((IncludeStep) step).convertToRegular();
        }
      }
      newSteps.add(newStep);
      List<UmlStep> newChildren = new ArrayList<>();
      newStep.setChildren(newChildren);
      replaceInclude(included,step.getChildren(),newStep.getChildren());

    }

  }
}
