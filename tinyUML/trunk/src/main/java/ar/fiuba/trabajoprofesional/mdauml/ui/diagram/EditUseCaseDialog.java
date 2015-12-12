package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import ar.fiuba.trabajoprofesional.mdauml.model.Flow;
import ar.fiuba.trabajoprofesional.mdauml.model.StepType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlMainStep;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlStep;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseElement;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;


/**
 * An edit dialog for use cases.
 *
 * @author Fernando Romera Ferrio
 * @version 1.0
 */
public class EditUseCaseDialog extends javax.swing.JDialog {


  private UseCaseElement useCaseElement;

  private boolean isOk;
  private JTextField name;
  private JTextField preconditionsTextField;
  private JTextField postconditionTextField;
  private JTextPane description;
  private JList<String> mainEntities;
  private JList<UmlActor> mainActors;
  private JList<UmlActor> secondaryActors;
  private JComboBox<String> comboMainEntity;
  private JComboBox<UmlActor> comboMainActor;
  private JComboBox<UmlActor> comboSecActors;
  private JList alternativeFlows;
  private JList<String> postconditions;
  private JList<String> preconditions;
  private JList<String> mainFlowStepList;
  private Flow mainFlow;

  private EditListAction preconditionEditListAction;
  private EditListAction postconditionEditListAction;

  private Window parent;
  private Stack<UmlMainStep> fathers;



  /**
   * Creates new form EditUseCaseDialog
   *
   * @param parent the parent frame
   * @param anUseCase the edited Use Case
   * @param modal whether the dialog is to be modal
   */
  public EditUseCaseDialog(Window parent, UseCaseElement anUseCase, boolean modal) {
    super(parent, ModalityType.APPLICATION_MODAL);
    this.parent = parent;
    this.useCaseElement = anUseCase;
    this.fathers = new Stack<UmlMainStep>();
    initComponents();
    myPostInit();
  }

  public String getDescription() {
    return description.getText();
  }

  public String getName() {
    return name.getText();
  }

  public Set<UmlActor> getMainActors() {
    DefaultListModel<UmlActor> model = (DefaultListModel<UmlActor>) mainActors.getModel();
    Enumeration<UmlActor> elements = model.elements();
    Set<UmlActor> set = new HashSet<UmlActor>(Collections.list(elements));
    return set;
  }

  public Set<UmlActor> getSecondaryActors() {
    DefaultListModel<UmlActor> model = (DefaultListModel<UmlActor>) secondaryActors.getModel();
    Enumeration<UmlActor> elements = model.elements();
    Set<UmlActor> set = new HashSet<UmlActor>(Collections.list(elements));
    return set;
  }

  public List<String> getPreconditions() {
    DefaultListModel<String> listmodel = (DefaultListModel<String>) preconditions.getModel();
    Enumeration<String> elements = listmodel.elements();
    return Collections.list(elements);
  }

  public List<String> getPostconditions() {
    DefaultListModel<String> listmodel = (DefaultListModel<String>) postconditions.getModel();
    Enumeration<String> elements = listmodel.elements();
    return Collections.list(elements);
  }


  public Flow getMainFlow() {
    return mainFlow;
  }


  public String getMainEntity() {
    if (mainEntities.getModel().getSize() != 0)
      return mainEntities.getModel().getElementAt(0);
    else
      return null;
  }

  public boolean isOk() {
    return isOk;
  }


  private void myPostInit() {
    UmlUseCase useCase = (UmlUseCase) useCaseElement.getModelElement();
    name.setText(useCase.getName());
    description.setText(useCase.getDescription());

    List<String> umlEntities = new ArrayList<String>(useCase.getAllEntities());
    Collections.sort(umlEntities);

    List<UmlActor> umlActors = new ArrayList<UmlActor>(useCase.getUmlActors());
    Collections.sort(umlActors, new Comparator<UmlActor>() {
      @Override
      public int compare(UmlActor o1, UmlActor o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });


    String[] actorsNames = new String[umlActors.size()];
    int index = 0;
    for (UmlActor umlActor : umlActors) {
      actorsNames[index] = umlActor.getName();
      index++;
    }


    DefaultComboBoxModel entityComboBoxModel = new DefaultComboBoxModel(umlEntities.toArray());
    comboMainEntity.setModel(entityComboBoxModel);
    comboMainActor.setModel(new DefaultComboBoxModel(umlActors.toArray()));
    comboSecActors.setModel(new DefaultComboBoxModel(umlActors.toArray()));


    DefaultListModel<String> mainEntityModel = new DefaultListModel<String>();
    String mainEntity = useCase.getMainEntity();
    if (mainEntity != null) {
      mainEntityModel.addElement(mainEntity);
      entityComboBoxModel.removeElement(mainEntity);
    }
    mainEntities.setModel(mainEntityModel);

    DefaultListModel<UmlActor> mainActorsModel = new DefaultListModel<UmlActor>();
    for (UmlActor actor : useCase.getMainActors())
      mainActorsModel.addElement(actor);
    mainActors.setModel(mainActorsModel);

    DefaultListModel<UmlActor> secActorsModel = new DefaultListModel<UmlActor>();
    for (UmlActor actor : useCase.getSecondaryActors())
      secActorsModel.addElement(actor);
    secondaryActors.setModel(secActorsModel);

    DefaultListModel<String> preconditionsModel = new DefaultListModel<String>();
    for (String precondition : useCase.getPreconditions()) {
      preconditionsModel.addElement(precondition);
    }
    preconditions.setModel(preconditionsModel);
    preconditionEditListAction.setList(preconditions);

    DefaultListModel<String> postconditionsModel = new DefaultListModel<String>();
    for (String postconditions : useCase.getPostconditions()) {
      postconditionsModel.addElement(postconditions);
    }
    postconditions.setModel(postconditionsModel);
    postconditionEditListAction.setList(postconditions);


    mainFlow = (Flow) useCase.getMainFLow().clone();

    DefaultListModel<String> mainFlowStepModel = new DefaultListModel<String>();

    refreshMainFlow(mainFlowStepModel);
    mainFlowStepList.setModel(mainFlowStepModel);

  }

  private void refreshMainFlow(DefaultListModel<String> listModel) {
    List<UmlStep> flow = mainFlow.getFlow();
    listModel.clear();
    for (UmlStep step_ : flow) {

      List<String> completeDescription = step_.getCompleteDescription();
      for (String element : completeDescription) {
        listModel.addElement(element);
      }
    }
  }

  private UmlMainStep getFather() {
    UmlMainStep father = null;
    if (!fathers.isEmpty()) {
      father = fathers.peek();
    }
    return father;
  }

  private void removeFathers(UmlMainStep umlsMainStep) {
    if (umlsMainStep.isFatherType()) {
      fathers.remove(umlsMainStep);
    }

    for (UmlStep umlStep : umlsMainStep.getChildrens()) {
      removeFathers((UmlMainStep) umlStep);
    }

  }

  private void initComponents() {
    setResizable(false);
    setSize(new Dimension(570, 700));
    setTitle("Use case specification");
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JScrollPane mainScrollPanel = new JScrollPane();
    mainScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
    mainScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JButton btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isOk = false;
        dispose();
      }
    });

    JButton btnOk = new JButton("OK");
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
            groupLayout.createSequentialGroup().addContainerGap(335, Short.MAX_VALUE)
                .addComponent(btnOk).addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnCancel).addContainerGap())
        .addComponent(mainScrollPanel, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
        groupLayout
            .createSequentialGroup()
            .addComponent(mainScrollPanel, GroupLayout.PREFERRED_SIZE, 633,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(
                groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnCancel)
                    .addComponent(btnOk)).addContainerGap()));

    JPanel panel = new JPanel();
    mainScrollPanel.setViewportView(panel);

    JLabel lblName = new JLabel("Name:");

    name = new JTextField();
    name.setColumns(10);

    JLabel lblDescription = new JLabel("Description:");

    JScrollPane scrollPane = new JScrollPane();

    description = new JTextPane();
    scrollPane.setViewportView(description);

    JPanel mainEntityPanel = new JPanel();
    mainEntityPanel.setBorder(new TitledBorder(null, "Main entity", TitledBorder.LEADING,
        TitledBorder.TOP, null, null));

    JPanel mainActorsPanel = new JPanel();
    mainActorsPanel.setBorder(new TitledBorder(null, "Main actors", TitledBorder.LEADING,
        TitledBorder.TOP, null, null));

    JPanel panelSecondaryActors = new JPanel();
    panelSecondaryActors.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Secondary actors", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JScrollPane scrollPane_2 = new JScrollPane();

    comboSecActors = new JComboBox();

    JButton addSecondaryActors = new JButton("Add");
    addSecondaryActors.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UmlActor selectedActor = (UmlActor) comboSecActors.getSelectedItem();

        DefaultListModel<UmlActor> secondaryActorModelList =
            (DefaultListModel<UmlActor>) secondaryActors.getModel();

        if (!secondaryActorModelList.contains(selectedActor)) {
          ((DefaultListModel<UmlActor>) secondaryActors.getModel()).addElement(selectedActor);
          ((DefaultListModel<UmlActor>) mainActors.getModel()).removeElement(selectedActor);
        }
      }
    });

    JButton removeSecondaryActors = new JButton("Delete");
    removeSecondaryActors.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        DefaultListModel<UmlActor> listModelActors =
            ((DefaultListModel<UmlActor>) secondaryActors.getModel());

        if (listModelActors.isEmpty())
          return;

        int selectedActor = secondaryActors.getSelectedIndex();

        if (selectedActor == -1) {
          selectedActor = listModelActors.getSize() - 1;
        }

        listModelActors.remove(selectedActor);
      }
    });
    GroupLayout gl_panelSecondaryActors = new GroupLayout(panelSecondaryActors);
    gl_panelSecondaryActors.setHorizontalGroup(gl_panelSecondaryActors.createParallelGroup(
        Alignment.TRAILING).addGroup(
        gl_panelSecondaryActors
            .createSequentialGroup()
            .addContainerGap()
            .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(
                gl_panelSecondaryActors
                    .createParallelGroup(Alignment.TRAILING)
                    .addGroup(
                        gl_panelSecondaryActors
                            .createSequentialGroup()
                            .addComponent(comboSecActors, GroupLayout.PREFERRED_SIZE, 130,
                                GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(addSecondaryActors, GroupLayout.DEFAULT_SIZE, 72,
                                Short.MAX_VALUE))
                    .addComponent(removeSecondaryActors, GroupLayout.DEFAULT_SIZE, 208,
                        Short.MAX_VALUE)).addContainerGap()));
    gl_panelSecondaryActors.setVerticalGroup(gl_panelSecondaryActors.createParallelGroup(
        Alignment.LEADING).addGroup(
        gl_panelSecondaryActors
            .createSequentialGroup()
            .addGap(6)
            .addGroup(
                gl_panelSecondaryActors
                    .createParallelGroup(Alignment.LEADING)
                    .addGroup(
                        gl_panelSecondaryActors
                            .createSequentialGroup()
                            .addGroup(
                                gl_panelSecondaryActors
                                    .createParallelGroup(Alignment.BASELINE)
                                    .addComponent(comboSecActors, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addSecondaryActors))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(removeSecondaryActors))
                    .addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 65,
                        GroupLayout.PREFERRED_SIZE)).addContainerGap(19, Short.MAX_VALUE)));

    secondaryActors = new JList();
    secondaryActors.setVisibleRowCount(3);
    scrollPane_2.setViewportView(secondaryActors);
    panelSecondaryActors.setLayout(gl_panelSecondaryActors);

    JPanel panelPreconditions = new JPanel();
    panelPreconditions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Preconditions", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JScrollPane scrollPanePreconditions = new JScrollPane();

    JButton addPreconditions = new JButton("Add");
    addPreconditions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        String precondition = preconditionsTextField.getText();
        if (!precondition.isEmpty()) {
          ((DefaultListModel<String>) preconditions.getModel()).addElement(precondition);
          preconditionsTextField.setText("");
        }
      }
    });

    JButton deletePreconditions = new JButton("Delete");
    deletePreconditions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        DefaultListModel<String> listModel = ((DefaultListModel<String>) preconditions.getModel());
        if (listModel.isEmpty())
          return;

        int selectedPrecondition = preconditions.getSelectedIndex();

        if (selectedPrecondition == -1) {
          selectedPrecondition = listModel.getSize() - 1;
        }
        listModel.remove(selectedPrecondition);
      }
    });

    preconditionsTextField = new JTextField();
    preconditionsTextField.setColumns(10);

    JButton editPreconditions = new JButton("Edit");
    preconditionEditListAction = new EditListAction("Edit");
    editPreconditions.setAction(preconditionEditListAction);

    GroupLayout gl_panelPreconditions = new GroupLayout(panelPreconditions);
    gl_panelPreconditions.setHorizontalGroup(gl_panelPreconditions.createParallelGroup(
        Alignment.TRAILING)
        .addGroup(
            gl_panelPreconditions
                .createSequentialGroup()
                .addContainerGap()
                .addGroup(
                    gl_panelPreconditions
                        .createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPanePreconditions, GroupLayout.DEFAULT_SIZE, 321,
                            Short.MAX_VALUE)
                        .addComponent(preconditionsTextField, GroupLayout.DEFAULT_SIZE, 321,
                            Short.MAX_VALUE))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(
                    gl_panelPreconditions
                        .createParallelGroup(Alignment.TRAILING)
                        .addComponent(deletePreconditions, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editPreconditions, GroupLayout.DEFAULT_SIZE, 102,
                            Short.MAX_VALUE)
                        .addComponent(addPreconditions, GroupLayout.DEFAULT_SIZE, 122,
                            Short.MAX_VALUE)).addContainerGap()));
    gl_panelPreconditions.setVerticalGroup(gl_panelPreconditions.createParallelGroup(
        Alignment.LEADING).addGroup(
        gl_panelPreconditions
            .createSequentialGroup()
            .addGap(6)
            .addGroup(
                gl_panelPreconditions
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(addPreconditions)
                    .addComponent(preconditionsTextField, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(8)
            .addGroup(
                gl_panelPreconditions
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(scrollPanePreconditions, GroupLayout.PREFERRED_SIZE, 65,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(
                        gl_panelPreconditions.createSequentialGroup()
                            .addComponent(editPreconditions)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(deletePreconditions)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    preconditions = new JList();
    scrollPanePreconditions.setViewportView(preconditions);
    panelPreconditions.setLayout(gl_panelPreconditions);

    JPanel panelMainFlow = new JPanel();
    panelMainFlow.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Main flow", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JScrollPane mainFlowStepPane = new JScrollPane();
    JButton addMainFlowButton = new JButton("Add");

    addMainFlowButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        UmlMainStep father = getFather();
        UmlUseCase useCase = (UmlUseCase) useCaseElement.getModelElement();

        EditStepMainFlowDialog dialog = new EditStepMainFlowDialog(parent, useCase, father);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        if (dialog.isOk()) {

          StepType stepType = dialog.getStepType();
          String stepDescription = dialog.getDescription();
          UmlStep step = null;

          switch (stepType) {
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
      }

      private void addNewStep(UmlStep step) {
        UmlMainStep father = getFather();
        if (father != null) {
          mainFlow.addChildrenStep(father, step);
        } else {
          mainFlow.addStep(step);
        }

        UmlMainStep umlMainStep = (UmlMainStep) step;
        if (umlMainStep.isFatherType()) {
          fathers.push(umlMainStep);
        }

        String informationStep = step.showDescription();
        ((DefaultListModel<String>) mainFlowStepList.getModel()).addElement(informationStep);
      }
    });

    JButton editStepMainFlowButton = new JButton("Edit");
    editStepMainFlowButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        DefaultListModel<String> listModel =
            ((DefaultListModel<String>) mainFlowStepList.getModel());

        if (listModel.isEmpty())
          return;

        int selectedStep = mainFlowStepList.getSelectedIndex();

        if (selectedStep == -1) {
          selectedStep = listModel.getSize() - 1;
        }

        UmlMainStep step = (UmlMainStep) mainFlow.getStep(selectedStep);
        UmlMainStep father = (UmlMainStep) step.getFather();

        UmlUseCase useCase = (UmlUseCase) useCaseElement.getModelElement();
        EditStepMainFlowDialog dialog = new EditStepMainFlowDialog(parent, useCase, father, step);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);


        if (dialog.isOk()) {
          String stepDescription = dialog.getDescription();
          StepType stepType = dialog.getStepType();


          int indexReal = step.getIndex();
          int indexFlow = mainFlow.getFlowIndex(step);

          if (father != null) {
            selectedStep = selectedStep - mainFlow.getFlow().indexOf(father) - 1;
          }

          // Borro el Step Original
          mainFlow.removeStep(step);

          // Creo uno nuevo
          UmlStep newStep;
          if (stepType.equals(StepType.REGULAR)) {
            String actor = dialog.getActor();
            Set<String> entities = dialog.getEntities();
            newStep = new UmlMainStep(stepDescription, actor, stepType, entities);
          } else {
            newStep = new UmlMainStep(stepDescription, stepType);
          }

          if (father != null) {
            mainFlow.addChildrenStep(father, newStep, selectedStep);
          } else {
            mainFlow.addStep(newStep, indexReal, indexFlow);
          }

          // Add Children
          for (UmlStep children : step.getChildrens()) {
            mainFlow.addChildrenStep(newStep, children);
          }

          UmlMainStep umlMainStep = (UmlMainStep) newStep;
          if (umlMainStep.isFatherType()) {
            int index = fathers.indexOf(step);
            if (index != -1) {
              fathers.remove(index);
              fathers.insertElementAt(umlMainStep, index);
            }
          }

          refreshMainFlow(listModel);

        }
      }
    });

    JButton deletStepMainFlowButton = new JButton("Delete");
    deletStepMainFlowButton.addActionListener(new ActionListener() {
      @SuppressWarnings("incomplete-switch")
      public void actionPerformed(ActionEvent e) {

        DefaultListModel<String> listModel =
            ((DefaultListModel<String>) mainFlowStepList.getModel());
        if (listModel.isEmpty())
          return;

        int selectedStep = mainFlowStepList.getSelectedIndex();

        if (selectedStep == -1) {
          selectedStep = listModel.getSize() - 1;
        }

        UmlStep step = mainFlow.getStep(selectedStep);

        UmlStep father = step.getFather();
        if (father != null && father.getChildrens().size() == 1) {

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



        mainFlow.removeStep(step);


        UmlMainStep umlMainStep = (UmlMainStep) step;
        removeFathers(umlMainStep);

        refreshMainFlow(listModel);
      }
    });



    GroupLayout gl_panelMainFlow = new GroupLayout(panelMainFlow);
    gl_panelMainFlow.setHorizontalGroup(gl_panelMainFlow.createParallelGroup(Alignment.TRAILING)
        .addGroup(
            gl_panelMainFlow
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(mainFlowStepPane, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addGap(10)
                .addGroup(
                    gl_panelMainFlow
                        .createParallelGroup(Alignment.LEADING)
                        .addComponent(editStepMainFlowButton, Alignment.TRAILING,
                            GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                        .addComponent(deletStepMainFlowButton, GroupLayout.DEFAULT_SIZE, 67,
                            Short.MAX_VALUE)
                        .addGroup(
                            Alignment.TRAILING,
                            gl_panelMainFlow
                                .createSequentialGroup()
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(addMainFlowButton, GroupLayout.DEFAULT_SIZE, 132,
                                    Short.MAX_VALUE))).addContainerGap()));
    gl_panelMainFlow.setVerticalGroup(gl_panelMainFlow.createParallelGroup(Alignment.LEADING)
        .addGroup(
            gl_panelMainFlow
                .createSequentialGroup()
                .addGap(11)
                .addGroup(
                    gl_panelMainFlow
                        .createParallelGroup(Alignment.BASELINE)
                        .addComponent(mainFlowStepPane, GroupLayout.PREFERRED_SIZE, 150,
                            GroupLayout.PREFERRED_SIZE)
                        .addGroup(
                            gl_panelMainFlow.createSequentialGroup()
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(addMainFlowButton).addGap(7)
                                .addComponent(editStepMainFlowButton).addGap(8)
                                .addComponent(deletStepMainFlowButton)
                                .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));


    mainFlowStepList = new JList();
    mainFlowStepPane.setViewportView(mainFlowStepList);
    panelMainFlow.setLayout(gl_panelMainFlow);

    JPanel panelPostconditions = new JPanel();
    panelPostconditions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Postconditions", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    postconditionTextField = new JTextField();
    postconditionTextField.setColumns(10);

    JScrollPane scrollPanePostconditions = new JScrollPane();

    JButton editPostConditions = new JButton("Edit");
    postconditionEditListAction = new EditListAction("Edit");
    editPostConditions.setAction(postconditionEditListAction);

    JButton addPostconditions = new JButton("Add");
    addPostconditions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        String postCondition = postconditionTextField.getText();
        if (!postCondition.isEmpty()) {
          ((DefaultListModel<String>) postconditions.getModel()).addElement(postCondition);
          postconditionTextField.setText("");
        }
      }
    });

    JButton deletePostoconditions = new JButton("Delete");
    deletePostoconditions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        DefaultListModel<String> listModel = ((DefaultListModel<String>) postconditions.getModel());
        if (listModel.isEmpty())
          return;

        int selected = postconditions.getSelectedIndex();

        if (selected == -1) {
          selected = listModel.getSize() - 1;
        }
        listModel.remove(selected);
      }
    });


    GroupLayout gl_panelPostconditions = new GroupLayout(panelPostconditions);
    gl_panelPostconditions.setHorizontalGroup(gl_panelPostconditions.createParallelGroup(
        Alignment.TRAILING).addGroup(
        gl_panelPostconditions
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                gl_panelPostconditions
                    .createParallelGroup(Alignment.LEADING)
                    .addComponent(scrollPanePostconditions, GroupLayout.DEFAULT_SIZE, 332,
                        Short.MAX_VALUE)
                    .addComponent(postconditionTextField, GroupLayout.DEFAULT_SIZE, 332,
                        Short.MAX_VALUE))
            .addGap(10)
            .addGroup(
                gl_panelPostconditions
                    .createParallelGroup(Alignment.TRAILING)
                    .addComponent(deletePostoconditions, GroupLayout.PREFERRED_SIZE, 102,
                        Short.MAX_VALUE)
                    .addGroup(
                        gl_panelPostconditions
                            .createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(addPostconditions, GroupLayout.DEFAULT_SIZE, 130,
                                Short.MAX_VALUE))
                    .addComponent(editPostConditions, GroupLayout.DEFAULT_SIZE, 130,
                        Short.MAX_VALUE)).addContainerGap()));
    gl_panelPostconditions.setVerticalGroup(gl_panelPostconditions.createParallelGroup(
        Alignment.LEADING).addGroup(
        gl_panelPostconditions
            .createSequentialGroup()
            .addGap(6)
            .addGroup(
                gl_panelPostconditions
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(addPostconditions)
                    .addComponent(postconditionTextField, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(8)
            .addGroup(
                gl_panelPostconditions
                    .createParallelGroup(Alignment.LEADING)
                    .addGroup(
                        gl_panelPostconditions.createSequentialGroup()
                            .addComponent(editPostConditions)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(deletePostoconditions))
                    .addComponent(scrollPanePostconditions, GroupLayout.PREFERRED_SIZE, 65,
                        GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    postconditions = new JList();
    scrollPanePostconditions.setViewportView(postconditions);
    panelPostconditions.setLayout(gl_panelPostconditions);

    JPanel panelAlternativeFlow = new JPanel();
    panelAlternativeFlow.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Alternative flows", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JButton editAlternativeFlowButton = new JButton("Edit");

    JScrollPane scrollPaneAlternativeFlow = new JScrollPane();

    JButton deleteAlternativeFlowButton = new JButton("Delete");

    JButton addAlternativeFlowButton = new JButton("Add");
    addAlternativeFlowButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EditAlternativeStepMainFlowDialog dialog =
            new EditAlternativeStepMainFlowDialog(parent, mainFlow);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        if (dialog.isOk()) {

          mainFlow = dialog.getUpdatedFlow();
          // String stepDescription = dialog.getDescription();
          // Set<String> entities = dialog.getEntities();
          // String stepType = dialog.getStepType();
          // UmlStep step = new UmlMainStep(stepType, stepDescription, entities);
          // mainFlow.addStep(step);
          // String informationStep = step.showDescription();
          // ((DefaultListModel<String>) mainFlowStepList.getModel()).addElement(informationStep);

        }
      }
    });



    // GroupLayout gl_panelAlternativeFlow = new GroupLayout(panelAlternativeFlow);
    // gl_panelAlternativeFlow.setHorizontalGroup(
    // gl_panelAlternativeFlow.createParallelGroup(Alignment.TRAILING)
    // .addGroup(gl_panelAlternativeFlow.createSequentialGroup()
    // .addContainerGap()
    // .addComponent(scrollPaneAlternativeFlow, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
    // .addGap(10)
    // .addGroup(gl_panelAlternativeFlow.createParallelGroup(Alignment.TRAILING)
    // .addComponent(deleteAlternativeFlowButton, GroupLayout.DEFAULT_SIZE,
    // GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    // .addGroup(gl_panelAlternativeFlow.createSequentialGroup()
    // .addGap(18)
    // .addComponent(addAlternativeFlowButton, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
    // .addGroup(gl_panelAlternativeFlow.createSequentialGroup()
    // .addGap(18)
    // .addComponent(editAlternativeFlowButton, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))
    // .addGap(1))
    // );
    // gl_panelAlternativeFlow.setVerticalGroup(
    // gl_panelAlternativeFlow.createParallelGroup(Alignment.LEADING)
    // .addGroup(gl_panelAlternativeFlow.createSequentialGroup()
    // .addContainerGap()
    // .addGroup(gl_panelAlternativeFlow.createParallelGroup(Alignment.LEADING, false)
    // .addGroup(gl_panelAlternativeFlow.createSequentialGroup()
    // .addComponent(scrollPaneAlternativeFlow, GroupLayout.PREFERRED_SIZE, 150,
    // GroupLayout.PREFERRED_SIZE)
    // .addContainerGap())
    // .addGroup(Alignment.TRAILING, gl_panelAlternativeFlow.createSequentialGroup()
    // .addComponent(addAlternativeFlowButton)
    // .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    // .addComponent(editAlternativeFlowButton)
    // .addPreferredGap(ComponentPlacement.RELATED)
    // .addComponent(deleteAlternativeFlowButton)
    // .addGap(58))))
    // );


    GroupLayout gl_panelAlternativeFlow = new GroupLayout(panelAlternativeFlow);
    gl_panelAlternativeFlow.setHorizontalGroup(gl_panelAlternativeFlow.createParallelGroup(
        Alignment.TRAILING)
        .addGroup(
            gl_panelAlternativeFlow
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneAlternativeFlow, GroupLayout.DEFAULT_SIZE, 302,
                    Short.MAX_VALUE)
                .addGap(10)
                .addGroup(
                    gl_panelAlternativeFlow
                        .createParallelGroup(Alignment.TRAILING)
                        .addComponent(editAlternativeFlowButton, Alignment.TRAILING,
                            GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                        .addComponent(deleteAlternativeFlowButton, GroupLayout.DEFAULT_SIZE, 67,
                            Short.MAX_VALUE)
                        .addGroup(
                            Alignment.TRAILING,
                            gl_panelAlternativeFlow
                                .createSequentialGroup()
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(addAlternativeFlowButton, GroupLayout.DEFAULT_SIZE,
                                    132, Short.MAX_VALUE))).addContainerGap()));
    gl_panelAlternativeFlow.setVerticalGroup(gl_panelAlternativeFlow.createParallelGroup(
        Alignment.LEADING).addGroup(
        gl_panelAlternativeFlow
            .createSequentialGroup()
            .addGap(11)
            .addGroup(
                gl_panelAlternativeFlow
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(scrollPaneAlternativeFlow, GroupLayout.PREFERRED_SIZE, 150,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(
                        gl_panelAlternativeFlow.createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(addAlternativeFlowButton).addGap(7)
                            .addComponent(editAlternativeFlowButton).addGap(8)
                            .addComponent(deleteAlternativeFlowButton)
                            .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    alternativeFlows = new JList();
    scrollPaneAlternativeFlow.setViewportView(alternativeFlows);
    panelAlternativeFlow.setLayout(gl_panelAlternativeFlow);
    GroupLayout gl_panel = new GroupLayout(panel);
    gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(
        gl_panel
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                gl_panel
                    .createParallelGroup(Alignment.TRAILING)
                    .addComponent(mainEntityPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAlternativeFlow, Alignment.LEADING,
                        GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addComponent(panelPostconditions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                        504, Short.MAX_VALUE)
                    .addComponent(panelMainFlow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 504,
                        Short.MAX_VALUE)
                    .addComponent(panelPreconditions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                        504, Short.MAX_VALUE)
                    .addComponent(panelSecondaryActors, Alignment.LEADING,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainActorsPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                        504, Short.MAX_VALUE)
                    .addGroup(
                        Alignment.LEADING,
                        gl_panel
                            .createSequentialGroup()
                            .addGroup(
                                gl_panel.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblDescription).addComponent(lblName))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(
                                gl_panel
                                    .createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(scrollPane)
                                    .addComponent(name, GroupLayout.DEFAULT_SIZE, 443,
                                        Short.MAX_VALUE)))).addGap(31)));
    gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
        gl_panel
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                gl_panel
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                gl_panel
                    .createParallelGroup(Alignment.LEADING)
                    .addComponent(lblDescription)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 58,
                        GroupLayout.PREFERRED_SIZE))
            .addGap(18)
            .addComponent(mainEntityPanel, GroupLayout.PREFERRED_SIZE, 96,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(mainActorsPanel, GroupLayout.PREFERRED_SIZE, 108,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(panelSecondaryActors, GroupLayout.PREFERRED_SIZE, 108,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(panelPreconditions, GroupLayout.PREFERRED_SIZE, 136,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addComponent(panelMainFlow, GroupLayout.PREFERRED_SIZE, 188,
                GroupLayout.PREFERRED_SIZE)
            .addGap(18)
            .addComponent(panelPostconditions, GroupLayout.PREFERRED_SIZE, 136,
                GroupLayout.PREFERRED_SIZE)
            .addGap(18)
            .addComponent(panelAlternativeFlow, GroupLayout.PREFERRED_SIZE, 188,
                GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));

    /* Main Entity */

    JScrollPane scrollPaneEntity = new JScrollPane();
    comboMainEntity = new JComboBox();

    JButton selectMainEntity = new JButton("Select");
    selectMainEntity.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        String selectedEntity = (String) comboMainEntity.getSelectedItem();
        DefaultListModel<String> mainEntityModelList =
            (DefaultListModel<String>) mainEntities.getModel();



        String changeEntity = mainEntityModelList.get(0);
        comboMainEntity.addItem(changeEntity);
        comboMainEntity.removeItem(selectedEntity);
        mainEntityModelList.clear();
        mainEntityModelList.addElement(selectedEntity);
      }
    });


    GroupLayout gl_mainEntityPanel = new GroupLayout(mainEntityPanel);
    gl_mainEntityPanel.setHorizontalGroup(gl_mainEntityPanel
        .createParallelGroup(Alignment.TRAILING).addGroup(
            gl_mainEntityPanel
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneEntity, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(
                    gl_mainEntityPanel
                        .createParallelGroup(Alignment.LEADING)
                        .addComponent(comboMainEntity, 0, 212, Short.MAX_VALUE)
                        .addComponent(selectMainEntity, GroupLayout.DEFAULT_SIZE, 212,
                            Short.MAX_VALUE)).addContainerGap()));
    gl_mainEntityPanel.setVerticalGroup(gl_mainEntityPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(
            gl_mainEntityPanel
                .createSequentialGroup()
                .addGap(6)
                .addGroup(
                    gl_mainEntityPanel
                        .createParallelGroup(Alignment.BASELINE)
                        .addComponent(scrollPaneEntity, GroupLayout.PREFERRED_SIZE, 68,
                            GroupLayout.PREFERRED_SIZE)
                        .addGroup(
                            gl_mainEntityPanel
                                .createSequentialGroup()
                                .addComponent(comboMainEntity, GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(selectMainEntity)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    mainEntities = new JList();
    mainEntities.setVisibleRowCount(3);

    scrollPaneEntity.setViewportView(mainEntities);
    mainEntityPanel.setLayout(gl_mainEntityPanel);


    JScrollPane scrollPane_1 = new JScrollPane();
    comboMainActor = new JComboBox();

    JButton addMainActor = new JButton("Add");
    addMainActor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        UmlActor selectedActor = (UmlActor) comboMainActor.getSelectedItem();
        DefaultListModel<UmlActor> mainActorModelList =
            (DefaultListModel<UmlActor>) mainActors.getModel();

        if (!mainActorModelList.contains(selectedActor)) {
          ((DefaultListModel<UmlActor>) mainActors.getModel()).addElement(selectedActor);
          ((DefaultListModel<UmlActor>) secondaryActors.getModel()).removeElement(selectedActor);
        }
      }
    });

    JButton deleteMainActor = new JButton("Delete");
    deleteMainActor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        DefaultListModel<UmlActor> listModelActors =
            ((DefaultListModel<UmlActor>) mainActors.getModel());

        if (listModelActors.isEmpty())
          return;

        int selectedActor = mainActors.getSelectedIndex();

        if (selectedActor == -1) {
          selectedActor = listModelActors.getSize() - 1;
        }

        listModelActors.remove(selectedActor);

      }
    });
    GroupLayout gl_mainActorsPanel = new GroupLayout(mainActorsPanel);
    gl_mainActorsPanel.setHorizontalGroup(gl_mainActorsPanel
        .createParallelGroup(Alignment.TRAILING).addGroup(
            gl_mainActorsPanel
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(
                    gl_mainActorsPanel
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                            gl_mainActorsPanel
                                .createSequentialGroup()
                                .addComponent(comboMainActor, GroupLayout.PREFERRED_SIZE, 132,
                                    GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(addMainActor, GroupLayout.DEFAULT_SIZE, 70,
                                    Short.MAX_VALUE))
                        .addComponent(deleteMainActor, GroupLayout.DEFAULT_SIZE, 200,
                            Short.MAX_VALUE)).addContainerGap()));
    gl_mainActorsPanel.setVerticalGroup(gl_mainActorsPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(
            gl_mainActorsPanel
                .createSequentialGroup()
                .addGap(6)
                .addGroup(
                    gl_mainActorsPanel
                        .createParallelGroup(Alignment.BASELINE)
                        .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 68,
                            GroupLayout.PREFERRED_SIZE)
                        .addGroup(
                            gl_mainActorsPanel
                                .createSequentialGroup()
                                .addGroup(
                                    gl_mainActorsPanel
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(comboMainActor, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addMainActor))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(deleteMainActor)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    mainActors = new JList();
    mainActors.setVisibleRowCount(3);

    scrollPane_1.setViewportView(mainActors);
    mainActorsPanel.setLayout(gl_mainActorsPanel);
    panel.setLayout(gl_panel);
    getContentPane().setLayout(groupLayout);

  }
}
