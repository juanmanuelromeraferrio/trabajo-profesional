package ar.fiuba.trabajoprofesional.mdauml.model;

import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import java.util.ArrayList;
import java.util.List;

public enum StepType {
  REGULAR("editstepmainflow.regular.type"),
  IF("editstepmainflow.if.type"),
  ELSE("editstepmainflow.else.type"),
  WHILE("editstepmainflow.while.type"),
  FOR("editstepmainflow.for.type"),
  ENDIF("editstepmainflow.endif.type"),
  ENDWHILE("editstepmainflow.endwhile.type"),
  ENDFOR("editstepmainflow.endfor.type"),
  INCLUDE("editstepmainflow.include.type");

  private String value;

  private StepType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return Msg.get(value);
  }

  public static StepType[] getValidTypesFor(UmlMainStep father, boolean hasChildren, boolean hasInlcude) {

    List<StepType> validStepTypes = new ArrayList<>();
    validStepTypes.add(StepType.REGULAR);
    validStepTypes.add(StepType.IF);
    validStepTypes.add(StepType.WHILE);
    validStepTypes.add(StepType.FOR);

    if (hasInlcude)
      validStepTypes.add(StepType.INCLUDE);

    if (!hasChildren)
      return validStepTypes.toArray(new StepType[(validStepTypes.size())]);

    switch (father.getType()) {
      case ELSE:
        validStepTypes.add(StepType.ENDIF);
        break;
      case FOR:
        validStepTypes.add(StepType.ENDFOR);
        break;
      case IF:
        validStepTypes.add(StepType.ELSE);
        validStepTypes.add(StepType.ENDIF);
        break;
      case WHILE:
        validStepTypes.add(ENDWHILE);
        break;
      default:
        return null;
    }
    return validStepTypes.toArray(new StepType[(validStepTypes.size())]);
  }

}
