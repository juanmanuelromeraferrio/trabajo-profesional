package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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

import ar.fiuba.trabajoprofesional.mdauml.model.StepType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlMainStep;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

public class EditStepMainFlowDialog extends javax.swing.JDialog {


  private Boolean isOk = Boolean.FALSE;
  private JList<String> entities;
  private JComboBox<String> comboEntities;
  private JComboBox<StepType> comboTypesStep;
  private JTextPane stepDescription;
  private UmlMainStep step;
  private UmlMainStep father;

  private JComboBox<String> comboActorsStep;
  private JTextField textFieldCondition;

  private JLabel lblCondition;

  private JPanel entitiesPanel;

  private JButton editStep;

  private JButton saveStep;

  /**
   * Creates new form EditUseCaseDialog
   *
   * @param parent the parent frame
   * @wbp.parser.constructor
   */
  public EditStepMainFlowDialog(java.awt.Window parent, UmlMainStep father) {
    super(parent, ModalityType.APPLICATION_MODAL);
    this.father = father;
    initComponents();

  }

  public EditStepMainFlowDialog(java.awt.Window parent, UmlMainStep father, UmlMainStep step) {
    super(parent, ModalityType.APPLICATION_MODAL);
    this.father = father;
    this.step = step;
    initComponents();
    myPostInit();
  }

  private void myPostInit() {

    String actor = step.getActor();
    StepType type = step.getType();

    // Marco el actor seleccionado
    comboActorsStep.setSelectedItem(actor);
    // Marco el tipo seleccionado
    comboTypesStep.setSelectedItem(type);
    comboTypesStep.setEnabled(false);

    setDescription(step);
    setEntities(step.getDescription(), step.getEntities());

  }

  private void initComponents() {
    setResizable(false);
    setSize(new Dimension(526, 376));
    setTitle(ApplicationResources.getInstance().getString("editstepmainflow.title"));
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JScrollPane mainScrollPanel = new JScrollPane();
    mainScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
    mainScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JButton btnCancel =
        new JButton(ApplicationResources.getInstance().getString("stdcaption.cancel"));
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isOk = false;
        dispose();
      }
    });

    JButton btnOk = new JButton(ApplicationResources.getInstance().getString("stdcaption.ok"));
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
            .addComponent(mainScrollPanel, GroupLayout.PREFERRED_SIZE, 313,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnCancel)
                    .addComponent(btnOk)).addContainerGap()));

    JPanel generalPanel = new JPanel();
    mainScrollPanel.setViewportView(generalPanel);

    final JPanel stepPanel = new JPanel();
    stepPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        ApplicationResources.getInstance().getString("editstepmainflow.step.label"),
        TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JScrollPane scrollPaneStep = new JScrollPane();

    saveStep = new JButton(ApplicationResources.getInstance().getString("stdcaption.save"));
    saveStep.addActionListener(new ActionListener() {
      @SuppressWarnings({"unchecked", "rawtypes"})
      public void actionPerformed(ActionEvent arg0) {

        StepType stepType = getStepType();
        switch (stepType) {
          case IF:
          case WHILE: {
            textFieldCondition.setEnabled(false);
            break;
          }
          case REGULAR: {
            entities.setModel(new DefaultListModel<String>());

            List<String> entityModel = new ArrayList<String>();
            String description = stepDescription.getText();
            String[] split = description.split(" ");

            List<String> entitiesList = Arrays.asList(split);
            List<String> entitiesSelected = new ArrayList<String>();

            for (String entity : entitiesList) {

              if (entity.startsWith("@")) {
                String entityFormatedSelected =
                    entity.substring(1, 2).toUpperCase() + entity.substring(2);
                if (!entitiesSelected.contains(entityFormatedSelected)) {
                  entitiesSelected.add(entityFormatedSelected);
                }
              } else {
                String entityFormated = entity.substring(0, 1).toUpperCase() + entity.substring(1);

                if (!entityModel.contains(entityFormated)) {
                  entityModel.add(entityFormated);
                }
              }
            }

            Collections.sort(entityModel);
            Collections.sort(entitiesSelected);
            comboEntities.setModel(new DefaultComboBoxModel(entityModel.toArray()));

            for (String entitySelect : entitiesSelected) {
              ((DefaultListModel<String>) entities.getModel()).addElement(entitySelect);
            }
            stepDescription.setEnabled(false);
            break;
          }
          default: {
            break;
          }

        }
      }
    });

    editStep = new JButton(ApplicationResources.getInstance().getString("stdcaption.edit"));
    editStep.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        StepType stepType = getStepType();

        switch (stepType) {
          case IF:
          case WHILE: {
            textFieldCondition.setEnabled(true);
            break;
          }
          case REGULAR: {
            stepDescription.setEnabled(true);
            break;
          }
          default: {
            break;
          }

        }
      }
    });

    JLabel typeLabel =
        new JLabel(ApplicationResources.getInstance().getString("editstepmainflow.type.label"));

    ComboBoxModel<StepType> typeComboBoxModel;
    if (father != null) {
      StepType[] values = StepType.getValidTypesFor(father.getType());
      typeComboBoxModel = new DefaultComboBoxModel<StepType>(values);
    } else {
      typeComboBoxModel = new DefaultComboBoxModel<StepType>(StepType.getValidTypesWithoutFather());
    }

    comboTypesStep = new JComboBox<StepType>();
    comboTypesStep.setModel(typeComboBoxModel);
    comboTypesStep.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        StepType selectedType = (StepType) comboTypesStep.getSelectedItem();

        switch (selectedType) {
          case IF:
          case WHILE: {
            comboActorsStep.setEnabled(false);
            stepDescription.setVisible(false);
            lblCondition.setVisible(true);
            textFieldCondition.setVisible(true);
            textFieldCondition.setEnabled(true);
            entitiesPanel.setVisible(false);
            saveStep.setVisible(true);
            editStep.setVisible(true);
            break;
          }
          case ENDIF: {
            comboActorsStep.setEnabled(false);
            stepDescription.setVisible(false);
            lblCondition.setVisible(false);
            textFieldCondition.setVisible(false);
            entitiesPanel.setVisible(false);
            saveStep.setVisible(false);
            editStep.setVisible(false);
            break;
          }
          default: {
            comboActorsStep.setEnabled(true);
            stepDescription.setVisible(true);
            lblCondition.setVisible(false);
            textFieldCondition.setVisible(false);
            entitiesPanel.setVisible(true);
            saveStep.setVisible(true);
            editStep.setVisible(true);
          }

        }

      }
    });

    String[] actorItems =
        {ApplicationResources.getInstance().getString("editstepmainflow.user.actor"),
            ApplicationResources.getInstance().getString("editstepmainflow.system.actor")};

    JLabel actorLabel =
        new JLabel(ApplicationResources.getInstance().getString("editstepmainflow.actor.label"));
    ComboBoxModel<String> actorComboBoxModel = new DefaultComboBoxModel<String>(actorItems);
    comboActorsStep = new JComboBox<String>();
    comboActorsStep.setModel(actorComboBoxModel);

    lblCondition =
        new JLabel(ApplicationResources.getInstance().getString("editstepmainflow.condition.label"));
    lblCondition.setVisible(false);
    textFieldCondition = new JTextField();
    textFieldCondition.setColumns(10);
    textFieldCondition.setVisible(false);

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
                            .addComponent(scrollPaneStep, GroupLayout.DEFAULT_SIZE, 337,
                                Short.MAX_VALUE)
                            .addGap(10)
                            .addGroup(
                                gropuLayoutStepPanel
                                    .createParallelGroup(Alignment.TRAILING)
                                    .addComponent(saveStep, GroupLayout.DEFAULT_SIZE, 111,
                                        Short.MAX_VALUE)
                                    .addComponent(editStep, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 111, Short.MAX_VALUE)))
                    .addGroup(
                        gropuLayoutStepPanel
                            .createSequentialGroup()
                            .addGroup(
                                gropuLayoutStepPanel
                                    .createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                        gropuLayoutStepPanel
                                            .createParallelGroup(Alignment.TRAILING, false)
                                            .addComponent(typeLabel, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(actorLabel, GroupLayout.DEFAULT_SIZE, 54,
                                                Short.MAX_VALUE)).addComponent(lblCondition))
                            .addGap(18)
                            .addGroup(
                                gropuLayoutStepPanel
                                    .createParallelGroup(Alignment.LEADING)
                                    .addComponent(comboActorsStep, 0, 386, Short.MAX_VALUE)
                                    .addComponent(comboTypesStep, 0, 386, Short.MAX_VALUE)
                                    .addComponent(textFieldCondition, GroupLayout.DEFAULT_SIZE,
                                        386, Short.MAX_VALUE)))).addContainerGap()));
    gropuLayoutStepPanel.setVerticalGroup(gropuLayoutStepPanel.createParallelGroup(
        Alignment.LEADING).addGroup(
        gropuLayoutStepPanel
            .createSequentialGroup()
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(comboTypesStep, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(actorLabel)
                    .addComponent(comboActorsStep, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(textFieldCondition, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCondition))
            .addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
            .addGroup(
                gropuLayoutStepPanel
                    .createParallelGroup(Alignment.TRAILING, false)
                    .addComponent(scrollPaneStep, GroupLayout.PREFERRED_SIZE, 52,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(
                        gropuLayoutStepPanel
                            .createSequentialGroup()
                            .addComponent(saveStep)
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(editStep))).addContainerGap()));

    stepDescription = new JTextPane();
    scrollPaneStep.setViewportView(stepDescription);
    stepPanel.setLayout(gropuLayoutStepPanel);

    entitiesPanel = new JPanel();
    entitiesPanel.setBorder(new TitledBorder(null, ApplicationResources.getInstance().getString(
        "editstepmainflow.entities.label"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

    GroupLayout firstLayout = new GroupLayout(generalPanel);
    firstLayout.setHorizontalGroup(firstLayout.createParallelGroup(Alignment.TRAILING).addGroup(
        firstLayout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                firstLayout
                    .createParallelGroup(Alignment.TRAILING)
                    .addComponent(stepPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 490,
                        Short.MAX_VALUE)
                    .addComponent(entitiesPanel, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
            .addGap(18)));
    firstLayout.setVerticalGroup(firstLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(
            firstLayout
                .createSequentialGroup()
                .addGap(6)
                .addComponent(stepPanel, GroupLayout.PREFERRED_SIZE, 176,
                    GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(entitiesPanel, GroupLayout.PREFERRED_SIZE, 82,
                    GroupLayout.PREFERRED_SIZE).addContainerGap()));

    JScrollPane scrollPaneEntity = new JScrollPane();

    comboEntities = new JComboBox();

    JButton addEntity = new JButton(ApplicationResources.getInstance().getString("stdcaption.add"));
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
        new JButton(ApplicationResources.getInstance().getString("stdcaption.delete"));
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

  private void setDescription(UmlMainStep step) {
    StepType stepType = getStepType();

    switch (stepType) {
      case IF:
      case WHILE: {
        textFieldCondition.setText(step.getDescription());
        break;
      }
      case REGULAR: {
        stepDescription.setText(step.getDescription());
        break;
      }
      default:
        break;

    }
  }

  private void setEntities(String description, Set<String> entities) {

    StepType stepType = getStepType();

    switch (stepType) {
      case IF:
      case WHILE: {
        return;
      }
      case REGULAR: {
        // Guardo lista de Entidades Posibles
        String[] split = description.split(" ");
        List<String> entitiesList = Arrays.asList(split);
        List<String> entityListFinal = new ArrayList<String>();
        for (String entity : entitiesList) {

          if (entity.startsWith("@")) {
            continue;
          }

          String entityFormated = entity.substring(0, 1).toUpperCase() + entity.substring(1);
          if (!entityListFinal.contains(entityFormated)) {
            entityListFinal.add(entityFormated);
          }
        }
        Collections.sort(entityListFinal);
        comboEntities.setModel(new DefaultComboBoxModel(entityListFinal.toArray()));

        // Guardo las entidades seleccionadas
        DefaultListModel<String> model = (DefaultListModel<String>) this.entities.getModel();
        for (String entity : entities) {
          model.addElement(entity);
        }
        break;
      }
      default:
        break;

    }
  }

  public String getDescription() {
    StepType stepType = getStepType();

    switch (stepType) {
      case IF:
      case WHILE: {
        return this.textFieldCondition.getText();
      }
      case REGULAR: {
        return this.stepDescription.getText();
      }
      default:
        return "";

    }
  }

  public Set<String> getEntities() {
    DefaultListModel<String> model = (DefaultListModel<String>) entities.getModel();
    Enumeration<String> elements = model.elements();
    Set<String> set = new HashSet<String>(Collections.list(elements));
    return set;
  }

  public StepType getStepType() {
    return (StepType) this.comboTypesStep.getSelectedItem();
  }

  public String getActor() {
    return (String) this.comboActorsStep.getSelectedItem();
  }

  public boolean isOk() {
    return isOk;
  }
}
