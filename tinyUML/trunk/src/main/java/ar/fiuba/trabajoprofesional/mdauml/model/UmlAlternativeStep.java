package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;

import org.jmock.util.NotImplementedException;

public class UmlAlternativeStep extends UmlStep {

  public UmlAlternativeStep(String description) {
    super(description);
  }

  @Override
  public String showDescription() {
    return super.getCompleteIndex() + ". " + super.getDescription();
  }


  public UmlStep clone(UmlStep father) {

    UmlStep cloned = new UmlAlternativeStep(this.description);
    cloned.father = father;
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

  @Override
  public UmlStep clone() {
    throw new NotImplementedException();
  }

}
