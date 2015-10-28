package ar.fiuba.trabajoprofesional.mdauml.model;

import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

public enum StepType {
  REGULAR("editstepmainflow.regular.type"), IF("editstepmainflow.if.type"), ELSE(
      "editstepmainflow.else.type"), WHILE("editstepmainflow.while.type"), FOR(
      "editstepmainflow.for.type"), ENDIF("editstepmainflow.endif.type"), ENDWHILE(
      "editstepmainflow.endwhile.type"), ENDFOR("editstepmainflow.endfor.type");

  private String value;

  private StepType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return ApplicationResources.getInstance().getString(value);
  }

  public static StepType[] getValidTypesFor(StepType type) {

    switch (type) {

      case ELSE:
        StepType[] valuesElse =
            {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR, StepType.ENDIF};
        return valuesElse;
      case FOR:
        StepType[] valuesFor =
            {StepType.REGULAR, StepType.IF, StepType.ELSE, StepType.WHILE, StepType.FOR,
                StepType.ENDFOR};
        return valuesFor;
      case IF:
        StepType[] valuesIF =
            {StepType.REGULAR, StepType.IF, StepType.ELSE, StepType.WHILE, StepType.FOR,
                StepType.ENDIF};
        return valuesIF;
      case WHILE:
        StepType[] valuesWhile =
            {StepType.REGULAR, StepType.IF, StepType.ELSE, StepType.WHILE, StepType.FOR,
                StepType.ENDWHILE};
        return valuesWhile;
      default:
        return null;

    }

  }

  public static StepType[] getValidTypesWithoutFather() {
    StepType[] values = {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR};
    return values;
  }
}
