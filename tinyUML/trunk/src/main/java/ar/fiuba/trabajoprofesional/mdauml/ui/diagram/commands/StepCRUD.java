package ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands;


import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.EditStepMainFlowDialog;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class StepCRUD {


    private UmlUseCase useCase;
    private Flow flow;
    private Stack<UmlMainStep> fathers;
    private Window parent;
    private JList<String> stepList;
    private DefaultListModel<String> stepsModel;


    public StepCRUD(UmlUseCase usecase, Flow flow, Window window, JList<String> stepList) {
        this.useCase = usecase;
        this.flow = flow;
        this.fathers = new Stack<>();
        this.parent = window;
        this.stepList = stepList;
        this.stepsModel = (DefaultListModel<String>) stepList.getModel();
    }

    public void add( ){

        UmlMainStep father = fathers.isEmpty()?null:fathers.peek();
        
        EditStepMainFlowDialog dialog = new EditStepMainFlowDialog(parent, useCase, father);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        if (dialog.isOk()) {

            StepType stepType = dialog.getStepType();
            String stepDescription = dialog.getDescription();
            UmlStep step = null;

            switch (stepType) {
                case INCLUDE:{
                    String actor = dialog.getActor();
                    Set<String> entities = dialog.getEntities();
                    step = new IncludeStep(stepDescription, actor,  entities);
                    ((IncludeStep)step).setIncluded(dialog.getIncluded());
                    addNewStep(step);
                    break;
                }
                case REGULAR: {
                    String actor = dialog.getActor();
                    Set<String> entities = dialog.getEntities();
                    step = new UmlMainStep(stepDescription, actor, stepType, entities);
                    addNewStep(step);
                    break;
                }
                case IF:
                case WHILE:
                case FOR: {
                    step = new UmlMainStep(stepDescription, stepType);
                    addNewStep(step);
                    break;
                }
                case ELSE: {
                    step = new UmlMainStep(stepDescription, stepType);
                    fathers.pop();
                    addNewStep(step);
                    break;
                }
                case ENDIF:
                case ENDWHILE:
                case ENDFOR: {
                    fathers.pop();
                    break;
                }
                default:
                    break;
            }
        }
        read();

    }


    @SuppressWarnings("Duplicates")
    public void edit() {

        if (stepsModel.isEmpty())
            return;

        int selectedStep = stepList.getSelectedIndex();

        if (selectedStep == -1) {
            selectedStep = stepsModel.getSize() - 1;
        }

        UmlMainStep step = (UmlMainStep) flow.getStep(selectedStep);
        UmlMainStep father = (UmlMainStep) step.getFather();

        EditStepMainFlowDialog dialog = new EditStepMainFlowDialog(parent, useCase, father, step);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);


        if (!dialog.isOk())
            return;

        String stepDescription = dialog.getDescription();
        StepType stepType = dialog.getStepType();

        int indexReal = step.getIndex();
        int indexFlow = flow.getFlowIndex(step);

        if (father != null) {
            selectedStep = selectedStep - flow.getFlow().indexOf(father) - 1;
        }

        // Borro el Step Original
        flow.removeStep(step);

        // Creo uno nuevo
        UmlStep newStep;
        if (stepType.equals(StepType.REGULAR)) {
            String actor = dialog.getActor();
            Set<String> entities = dialog.getEntities();
            newStep = new UmlMainStep(stepDescription, actor, stepType, entities);
        } else if (stepType.equals(StepType.INCLUDE)) {
            String actor = dialog.getActor();
            Set<String> entities = dialog.getEntities();
            newStep = new IncludeStep(stepDescription, actor, entities);
            ((IncludeStep) newStep).setIncluded(dialog.getIncluded());
        } else {
            newStep = new UmlMainStep(stepDescription, stepType);
        }

        if (father != null) {
            flow.addChildrenStep(father, newStep, selectedStep);
        } else {
            flow.addStep(newStep, indexReal, indexFlow);
        }

        // Add Children
        for (UmlStep children : step.getChildren()) {
            flow.addChildrenStep(newStep, children);
        }

        UmlMainStep umlMainStep = (UmlMainStep) newStep;
        if (umlMainStep.isFatherType()) {
            int index = fathers.indexOf(step);
            if (index != -1) {
                fathers.remove(index);
                fathers.insertElementAt(umlMainStep, index);
            }
        }
        read();

    }
    private void addNewStep(UmlStep step) {
        UmlMainStep father = fathers.isEmpty()?null:fathers.peek();
        if (father != null) {
            flow.addChildrenStep(father, step);
        } else {
            flow.addStep(step);
        }

        UmlMainStep umlMainStep = (UmlMainStep) step;
        if (umlMainStep.isFatherType()) {
            fathers.push(umlMainStep);
        }

        String informationStep = step.showDescription();
        stepsModel.addElement(informationStep);
    }


    public void remove(){

        if (stepsModel.isEmpty())
            return;

        int selectedStep = stepList.getSelectedIndex();

        if (selectedStep == -1) {
            selectedStep = stepsModel.getSize() - 1;
        }

        UmlStep step = flow.getStep(selectedStep);

        UmlStep father = step.getFather();
        if (father != null && father.getChildren().size() == 1) {

            String msg = null;
            switch (((UmlMainStep) father).getType()) {
                case ELSE:
                    msg = Msg.get("editstepmainflow.error.delete.else.step.text");
                    break;
                case FOR:
                    msg = Msg.get("editstepmainflow.error.delete.for.step.text");
                    break;
                case IF:
                    msg = Msg.get("editstepmainflow.error.delete.if.step.text");
                    break;
                case WHILE:
                    msg = Msg.get("editstepmainflow.error.delete.while.step.text");
                    break;
            }

            JOptionPane.showMessageDialog(parent, msg, Msg.get("editstepmainflow.error.title"),
                    JOptionPane.INFORMATION_MESSAGE);

            return;
        }



        flow.removeStep(step);
        removeFathers((UmlMainStep)step);
        read();

    }

    private void removeFathers(UmlMainStep umlsMainStep) {
        if (umlsMainStep.isFatherType()) {
            fathers.remove(umlsMainStep);
        }

        for (UmlStep umlStep : umlsMainStep.getChildren()) {
            removeFathers((UmlMainStep) umlStep);
        }

    }

    public void read() {
        java.util.List<UmlStep> steps = flow.getFlow();
        stepsModel.clear();
        for (UmlStep step : steps) {

            java.util.List<String> completeDescription = step.getCompleteDescription();
            for (String element : completeDescription) {
                stepsModel.addElement(element);
            }
        }
    }
}


