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

  public static StepType[] getValidTypesFor(StepType type, int childrens) {

    switch (type) {

      case ELSE:
        StepType[] valuesElse;
        if (childrens > 0) {
          valuesElse =
              new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR,
                  StepType.ENDIF};
        } else {
          valuesElse = new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR};
        }
        return valuesElse;
      case FOR:
        StepType[] valuesFor;

        if (childrens > 0) {
          valuesFor =
              new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR,
                  StepType.ENDFOR};
        } else {
          valuesFor = new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR};
        }


        return valuesFor;
      case IF:
        StepType[] valuesIF;

        if (childrens > 0) {
          valuesIF =
              new StepType[] {StepType.REGULAR, StepType.IF, StepType.ELSE, StepType.WHILE,
                  StepType.FOR, StepType.ENDIF};
        } else {
          valuesIF = new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR};
        }

        return valuesIF;
      case WHILE:
        StepType[] valuesWhile;

        if (childrens > 0) {
          valuesWhile =
              new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR,
                  StepType.ENDWHILE};
        } else {
          valuesWhile =
              new StepType[] {StepType.REGULAR, StepType.IF, StepType.WHILE, StepType.FOR};
        }

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
