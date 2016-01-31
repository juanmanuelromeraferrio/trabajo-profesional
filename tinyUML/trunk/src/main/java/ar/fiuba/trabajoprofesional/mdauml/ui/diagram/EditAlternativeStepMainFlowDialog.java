package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import ar.fiuba.trabajoprofesional.mdauml.model.Flow;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlAlternativeStep;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlStep;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;


public class EditAlternativeStepMainFlowDialog extends javax.swing.JDialog {

  private Boolean isOk = Boolean.FALSE;
  private JList<String> entities;
  private JComboBox<String> comboEntities;
  private JComboBox<String> comboSteps;
  private JTextPane stepDescription;
  private JTextField alternativeStepTextField;


  private Flow myFlow;
  private ArrayList<UmlStep> actualSteps;
  private UmlStep selectedStep;
  private JList<String> jListAlternativeSteps;
  private EditAlternativeStepsAction alternativeStepEditListAction;

  /**
   * Creates new form EditUseCaseDialog
   *
   * @param parent the parent frame
   * @wbp.parser.constructor
   */
  public EditAlternativeStepMainFlowDialog(java.awt.Window parent, Flow flow) {
    super(parent, ModalityType.APPLICATION_MODAL);
    this.myFlow = (Flow) flow.clone();
    initComponents();

  }

  // public EditAlternativeStepMainFlowDialog(java.awt.Window parent, Uml alternativeStep) {
  // super(parent, ModalityType.APPLICATION_MODAL);
  //
  // this.alternativeStep = alternativeStep;
  // initComponents();
  // myPostInit();
  // }



  private void myPostInit() {
    // String description = step.getDescription();
    // Set<String> entities = step.getEntities();
    // String type = step.getType();
    //
    // // Guardo Descripcion
    // this.stepDescription.setText(description);
    //
    // // Guardo lista de Entidades Posibles
    // String[] split = description.split(" ");
    // List<String> entitiesList = Arrays.asList(split);
    // List<String> entityListFinal = new ArrayList<String>();
    // for (String entity : entitiesList) {
    //
    // if (entity.startsWith("@")) {
    // continue;
    // }
    //
    // String entityFormated = entity.substring(0, 1).toUpperCase() + entity.substring(1);
    // if (!entityListFinal.contains(entityFormated)) {
    // entityListFinal.add(entityFormated);
    // }
    // }
    // Collections.sort(entityListFinal);
    // comboEntities.setModel(new DefaultComboBoxModel(entityListFinal.toArray()));
    //
    // // Guardo las entidades seleccionadas
    // DefaultListModel<String> model = (DefaultListModel<String>) this.entities.getModel();
    // for (String entity : entities) {
    // model.addElement(entity);
    // }
    //
    // // Marco el tipo seleccionado
    // comboSteps.setSelectedItem(type);
  }

  private void initComponents() {
    setResizable(false);
    setSize(new Dimension(480, 402));
    setTitle(Msg.get("editstepalternativeflow.title"));
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JScrollPane mainScrollPanel = new JScrollPane();
    mainScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
    mainScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JButton btnCancel =
        new JButton(Msg.get("stdcaption.cancel"));
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isOk = false;
        dispose();
      }
    });

    JButton btnOk = new JButton(Msg.get("stdcaption.ok"));
    btnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isOk = true;
        dispose();
      }
    });

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(groupLayout
        .createParallelGroup(Alignment.TRAILING)
        .addGroup(
            groupLayout.createSequentialGroup().addContainerGap(343, Short.MAX_VALUE)
                .addComponent(btnOk).addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnCancel).addContainerGap())
        .addComponent(mainScrollPanel, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
        groupLayout
            .createSequentialGroup()
            .addComponent(mainScrollPanel, GroupLayout.PREFERRED_SIZE, 342,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnCancel)
                    .addComponent(btnOk)).addContainerGap()));

    JPanel generalPanel = new JPanel();
    mainScrollPanel.setViewportView(generalPanel);

    JPanel stepPanel = new JPanel();
    stepPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        Msg.get(
            "editstepalternativeflow.alternativestep.label"), TitledBorder.LEADING,
        TitledBorder.TOP, null, null));


    JButton addASButton = new JButton("Add");
    addASButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        String alternativeStepDescription = alternativeStepTextField.getText();
        if (!alternativeStepDescription.isEmpty()) {

          UmlStep alternativeStep = new UmlAlternativeStep(alternativeStepDescription);
          myFlow.addChildrenStep(selectedStep, alternativeStep);

          ((DefaultListModel<String>) jListAlternativeSteps.getModel()).addElement(alternativeStep
              .showDescription());
          alternativeStepTextField.setText("");
        }
      }

    });

    JButton editASButton = new JButton("Edit");
    alternativeStepEditListAction = new EditAlternativeStepsAction("Edit");
    editASButton.setAction(alternativeStepEditListAction);
    JButton deleteASButton = new JButton("Delete");
    deleteASButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        DefaultListModel<String> listModel =
            ((DefaultListModel<String>) jListAlternativeSteps.getModel());

        if (listModel.isEmpty())
          return;

        int selectedAlternativeStep = jListAlternativeSteps.getSelectedIndex();

        if (selectedAlternativeStep == -1) {
          selectedAlternativeStep = listModel.getSize() - 1;
        }

        myFlow.removeChildrenStep(selectedStep, selectedAlternativeStep);
        refreshModel(listModel);

      }
    });

    alternativeStepTextField = new JTextField();
    alternativeStepTextField.setColumns(10);



    JLabel stepLabel =
        new JLabel(Msg.get(
            "editstepalternativeflow.step.label"));


    List<String> completeSteps = new ArrayList<String>();
    actualSteps = new ArrayList<UmlStep>();
    List<UmlStep> steps = myFlow.getFlow();
    for (UmlStep step : steps) {
      completeSteps.add(step.showDescription());
      actualSteps.add(step);
      for (UmlStep chlidrenSteps : step.getDescendants()) {
        completeSteps.add(chlidrenSteps.showDescription());
        actualSteps.add(chlidrenSteps);
      }
    }


    String[] arrayCompleteSteps = new String[completeSteps.size()];
    completeSteps.toArray(arrayCompleteSteps);

    this.selectedStep = actualSteps.get(0);

    ComboBoxModel<String> typeComboBoxModel = new DefaultComboBoxModel<String>(arrayCompleteSteps);
    comboSteps = new JComboBox<String>();
    comboSteps.setModel(typeComboBoxModel);
    comboSteps.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        ((DefaultListModel<String>) jListAlternativeSteps.getModel()).clear();

        String selectedEntity = (String) comboSteps.getSelectedItem();

        for (UmlStep step : actualSteps) {
          if (step.showDescription().equals(selectedEntity)) {
            selectedStep = step;
          }
        }
        
        alternativeStepEditListAction.setSelectedStep(selectedStep);

        for (UmlStep obj : selectedStep.getChildren()) {
          ((DefaultListModel<String>) jListAlternativeSteps.getModel()).addElement(obj
              .showDescription());
        }

      }
    });

    JScrollPane scrollPaneStep = new JScrollPane();

    GroupLayout gropuLayoutStepPanel = new GroupLayout(stepPanel);
    gropuLayoutStepPanel.setHorizontalGroup(gropuLayoutStepPanel.createParallelGroup(
        Alignment.TRAILING).addGroup(
        gropuLayoutStepPanel
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.LEADING)
                    .addGroup(
                        gropuLayoutStepPanel
                            .createSequentialGroup()
                            .addGroup(
                                gropuLayoutStepPanel
                                    .createParallelGroup(Alignment.LEADING)
                                    .addComponent(alternativeStepTextField,
                                        GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                                    .addComponent(scrollPaneStep, GroupLayout.PREFERRED_SIZE, 321,
                                        GroupLayout.PREFERRED_SIZE))
                            .addGap(18)
                            .addGroup(
                                gropuLayoutStepPanel
                                    .createParallelGroup(Alignment.TRAILING)
                                    .addComponent(editASButton, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deleteASButton, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 63, Short.MAX_VALUE)
                                    .addComponent(addASButton, GroupLayout.PREFERRED_SIZE, 63,
                                        GroupLayout.PREFERRED_SIZE)))
                    .addGroup(
                        gropuLayoutStepPanel
                            .createSequentialGroup()
                            .addComponent(stepLabel)
                            .addGap(18)
                            .addComponent(comboSteps, GroupLayout.PREFERRED_SIZE, 313,
                                GroupLayout.PREFERRED_SIZE))).addContainerGap()));
    gropuLayoutStepPanel.setVerticalGroup(gropuLayoutStepPanel.createParallelGroup(
        Alignment.LEADING).addGroup(
        gropuLayoutStepPanel
            .createSequentialGroup()
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(stepLabel)
                    .addComponent(comboSteps, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(alternativeStepTextField, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(addASButton))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.TRAILING)
                    .addComponent(scrollPaneStep, GroupLayout.PREFERRED_SIZE, 93,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(
                        gropuLayoutStepPanel.createSequentialGroup().addComponent(editASButton)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(deleteASButton))).addContainerGap(20, Short.MAX_VALUE)));

    jListAlternativeSteps = new JList<String>();
    jListAlternativeSteps.setModel(new DefaultListModel<String>());
    alternativeStepEditListAction.setData(selectedStep, jListAlternativeSteps);

    scrollPaneStep.setViewportView(jListAlternativeSteps);
    stepPanel.setLayout(gropuLayoutStepPanel);

    JPanel entitiesPanel = new JPanel();
    entitiesPanel.setBorder(new TitledBorder(null, Msg.get(
        "editstepmainflow.entities.label"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

    GroupLayout firstLayout = new GroupLayout(generalPanel);
    firstLayout.setHorizontalGroup(firstLayout.createParallelGroup(Alignment.LEADING).addGroup(
        firstLayout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                firstLayout
                    .createParallelGroup(Alignment.LEADING)
                    .addGroup(
                        firstLayout
                            .createSequentialGroup()
                            .addComponent(stepPanel, GroupLayout.PREFERRED_SIZE, 426,
                                GroupLayout.PREFERRED_SIZE).addContainerGap())
                    .addGroup(
                        firstLayout.createSequentialGroup()
                            .addComponent(entitiesPanel, 0, 0, Short.MAX_VALUE).addGap(18)))));
    firstLayout.setVerticalGroup(firstLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(
            firstLayout
                .createSequentialGroup()
                .addGap(6)
                .addComponent(stepPanel, GroupLayout.PREFERRED_SIZE, 196,
                    GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(entitiesPanel, GroupLayout.PREFERRED_SIZE, 82,
                    GroupLayout.PREFERRED_SIZE).addGap(38)));

    JScrollPane scrollPaneEntity = new JScrollPane();

    comboEntities = new JComboBox();

    JButton addEntity = new JButton(Msg.get("stdcaption.add"));
    addEntity.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        String selectedEntity = (String) comboEntities.getSelectedItem();
        DefaultListModel<String> entityModelList = (DefaultListModel<String>) entities.getModel();

        if (!entityModelList.contains(selectedEntity)) {
          ((DefaultListModel<String>) entities.getModel()).addElement(selectedEntity);
        }
      }
    });

    JButton deleteEntity =
        new JButton(Msg.get("stdcaption.delete"));
    deleteEntity.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        DefaultListModel<String> entityModelList = ((DefaultListModel<String>) entities.getModel());

        if (entityModelList.isEmpty())
          return;

        int selectedEntity = entities.getSelectedIndex();

        if (selectedEntity == -1) {
          selectedEntity = entityModelList.getSize() - 1;
        }

        entityModelList.remove(selectedEntity);

      }
    });
    GroupLayout groupLayoutEntityPanel = new GroupLayout(entitiesPanel);
    groupLayoutEntityPanel.setHorizontalGroup(groupLayoutEntityPanel.createParallelGroup(
        Alignment.LEADING).addGroup(
        Alignment.TRAILING,
        groupLayoutEntityPanel
            .createSequentialGroup()
            .addContainerGap()
            .addComponent(scrollPaneEntity, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                groupLayoutEntityPanel
                    .createParallelGroup(Alignment.LEADING, false)
                    .addGroup(
                        groupLayoutEntityPanel
                            .createSequentialGroup()
                            .addComponent(comboEntities, GroupLayout.PREFERRED_SIZE, 120,
                                GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(addEntity, GroupLayout.PREFERRED_SIZE, 58,
                                GroupLayout.PREFERRED_SIZE))
                    .addComponent(deleteEntity, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    groupLayoutEntityPanel.setVerticalGroup(groupLayoutEntityPanel.createParallelGroup(
        Alignment.LEADING).addGroup(
        groupLayoutEntityPanel
            .createSequentialGroup()
            .addGap(6)
            .addGroup(
                groupLayoutEntityPanel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(scrollPaneEntity, GroupLayout.PREFERRED_SIZE, 52,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(
                        groupLayoutEntityPanel
                            .createSequentialGroup()
                            .addGroup(
                                groupLayoutEntityPanel
                                    .createParallelGroup(Alignment.BASELINE)
                                    .addComponent(comboEntities, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addEntity))
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(deleteEntity)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    entities = new JList<>(new DefaultListModel<String>());
    entities.setVisibleRowCount(3);

    scrollPaneEntity.setViewportView(entities);
    entitiesPanel.setLayout(groupLayoutEntityPanel);
    generalPanel.setLayout(firstLayout);
    getContentPane().setLayout(groupLayout);

  }


  private void refreshModel(DefaultListModel<String> listModel) {
    listModel.clear();
    for (UmlStep obj : selectedStep.getChildren()) {
      listModel.addElement(obj.showDescription());
    }
  }

  public String getDescription() {
    return this.stepDescription.getText();
  }

  public Set<String> getEntities() {
    DefaultListModel<String> model = (DefaultListModel<String>) entities.getModel();
    Enumeration<String> elements = model.elements();
    Set<String> set = new HashSet<String>(Collections.list(elements));
    return set;
  }

  public String getStepType() {
    return (String) this.comboSteps.getSelectedItem();
  }

  public boolean isOk() {
    return isOk;
  }

  public Flow getUpdatedFlow() {
    return myFlow;
  }
}
