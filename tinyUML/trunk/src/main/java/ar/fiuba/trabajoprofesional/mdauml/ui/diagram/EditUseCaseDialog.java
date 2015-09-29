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
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlMainStep;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlStep;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseElement;



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
  private JList<UmlActor> mainActors;
  private JList<UmlActor> secondaryActors;
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

  public boolean isOk() {
    return isOk;
  }


  private void myPostInit() {
    UmlUseCase useCase = (UmlUseCase) useCaseElement.getModelElement();
    name.setText(useCase.getName());
    description.setText(useCase.getDescription());

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

    comboMainActor.setModel(new DefaultComboBoxModel(umlActors.toArray()));
    comboSecActors.setModel(new DefaultComboBoxModel(umlActors.toArray()));

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


    mainFlow = useCase.getMainFLow();
    List<UmlStep> flow = mainFlow.getFlow();

    DefaultListModel<String> mainFlowStepModel = new DefaultListModel<String>();

    for (UmlStep step : flow) {
      String information = step.showDescription();
      mainFlowStepModel.addElement(information);
    }
    mainFlowStepList.setModel(mainFlowStepModel);

  }

  private void refreshMainFlow(DefaultListModel<String> listModel) {
    List<UmlStep> flow = mainFlow.getFlow();
    listModel.clear();
    for (UmlStep step_ : flow) {
      String informationStep = step_.showDescription();
      listModel.addElement(informationStep);
    }
  }


  private void initComponents() {
    setResizable(false);
    setSize(new Dimension(469, 700));
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
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap(335, Short.MAX_VALUE)
          .addComponent(btnOk)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnCancel)
          .addContainerGap())
        .addComponent(mainScrollPanel, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addComponent(mainScrollPanel, GroupLayout.PREFERRED_SIZE, 633, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
            .addComponent(btnCancel)
            .addComponent(btnOk))
          .addContainerGap())
    );

    JPanel panel = new JPanel();
    mainScrollPanel.setViewportView(panel);

    JLabel lblName = new JLabel("Name:");

    name = new JTextField();
    name.setColumns(10);

    JLabel lblDescription = new JLabel("Description:");

    JScrollPane scrollPane = new JScrollPane();

    description = new JTextPane();
    scrollPane.setViewportView(description);

    JPanel mainActorsPanel = new JPanel();
    mainActorsPanel.setBorder(new TitledBorder(null, "Main actors", TitledBorder.LEADING,
        TitledBorder.TOP, null, null));

    JPanel panel_1 = new JPanel();
    panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
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
    GroupLayout gl_panel_1 = new GroupLayout(panel_1);
    gl_panel_1.setHorizontalGroup(gl_panel_1
        .createParallelGroup(Alignment.LEADING)
        .addGap(0, 426, Short.MAX_VALUE)
        .addGroup(
            Alignment.TRAILING,
            gl_panel_1
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(
                    gl_panel_1
                        .createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                            gl_panel_1
                                .createSequentialGroup()
                                .addComponent(comboSecActors, GroupLayout.PREFERRED_SIZE, 120,
                                    GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(addSecondaryActors, GroupLayout.PREFERRED_SIZE, 58,
                                    GroupLayout.PREFERRED_SIZE))
                        .addComponent(removeSecondaryActors, GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    gl_panel_1.setVerticalGroup(gl_panel_1
        .createParallelGroup(Alignment.LEADING)
        .addGap(0, 82, Short.MAX_VALUE)
        .addGroup(
            gl_panel_1
                .createSequentialGroup()
                .addGap(6)
                .addGroup(
                    gl_panel_1
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                            gl_panel_1
                                .createSequentialGroup()
                                .addGroup(
                                    gl_panel_1
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(comboSecActors, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addSecondaryActors))
                                .addPreferredGap(ComponentPlacement.RELATED,
                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removeSecondaryActors))
                        .addGroup(
                            gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(
                                scrollPane_2, GroupLayout.PREFERRED_SIZE, 52,
                                GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    secondaryActors = new JList();
    secondaryActors.setVisibleRowCount(3);
    scrollPane_2.setViewportView(secondaryActors);
    panel_1.setLayout(gl_panel_1);

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
                        .addComponent(preconditionsTextField, GroupLayout.DEFAULT_SIZE, 322,
                            Short.MAX_VALUE)
                        .addComponent(scrollPanePreconditions, GroupLayout.DEFAULT_SIZE, 322,
                            Short.MAX_VALUE))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(
                    gl_panelPreconditions
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                            Alignment.TRAILING,
                            gl_panelPreconditions
                                .createParallelGroup(Alignment.LEADING, false)
                                .addComponent(addPreconditions, Alignment.TRAILING,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
                                .addComponent(editPreconditions, Alignment.TRAILING,
                                    GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                        .addComponent(deletePreconditions, GroupLayout.PREFERRED_SIZE, 61,
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
                    .addComponent(scrollPanePreconditions, GroupLayout.PREFERRED_SIZE, 52,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(
                        gl_panelPreconditions.createSequentialGroup()
                            .addComponent(editPreconditions)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(deletePreconditions)))
            .addContainerGap(29, Short.MAX_VALUE)));

    preconditions = new JList();
    scrollPanePreconditions.setViewportView(preconditions);
    panelPreconditions.setLayout(gl_panelPreconditions);

    JPanel mainFlowPanel = new JPanel();
    mainFlowPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Main flow", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JScrollPane mainFlowStepPane = new JScrollPane();
    JButton addMainFlowButton = new JButton("Add");

    addMainFlowButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EditStepMainFlowDialog dialog = new EditStepMainFlowDialog(parent);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        if (dialog.isOk()) {

          String stepDescription = dialog.getDescription();
          Set<String> entities = dialog.getEntities();
          String stepType = dialog.getStepType();
          UmlStep step = new UmlMainStep(stepDescription, stepType, entities);
          mainFlow.addStep(step);
          String informationStep = step.showDescription();
          ((DefaultListModel<String>) mainFlowStepList.getModel()).addElement(informationStep);

        }
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


        EditStepMainFlowDialog dialog = new EditStepMainFlowDialog(parent, step);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        if (dialog.isOk()) {

          String stepDescription = dialog.getDescription();
          Set<String> entities = dialog.getEntities();
          String stepType = dialog.getStepType();
          UmlStep newStep = new UmlMainStep(stepDescription, stepType, entities);
          mainFlow.removeStep(step);
          mainFlow.addStep(newStep, selectedStep);
          refreshMainFlow(listModel);
        }
      }
    });

    JButton deletStepMainFlowButton = new JButton("Delete");
    deletStepMainFlowButton.addActionListener(new ActionListener() {
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
        mainFlow.removeStep(step);
        refreshMainFlow(listModel);
      }
    });

    GroupLayout mainFlowGP = new GroupLayout(mainFlowPanel);
    mainFlowGP.setHorizontalGroup(
      mainFlowGP.createParallelGroup(Alignment.TRAILING)
        .addGroup(mainFlowGP.createSequentialGroup()
          .addContainerGap()
          .addComponent(mainFlowStepPane, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(mainFlowGP.createParallelGroup(Alignment.LEADING)
            .addComponent(addMainFlowButton, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
            .addComponent(editStepMainFlowButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
            .addComponent(deletStepMainFlowButton, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
          .addContainerGap())
    );
    mainFlowGP.setVerticalGroup(
      mainFlowGP.createParallelGroup(Alignment.LEADING)
        .addGroup(mainFlowGP.createSequentialGroup()
          .addContainerGap(11, Short.MAX_VALUE)
          .addGroup(mainFlowGP.createParallelGroup(Alignment.BASELINE)
            .addComponent(mainFlowStepPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
            .addGroup(mainFlowGP.createSequentialGroup()
              .addComponent(addMainFlowButton)
              .addGap(7)
              .addComponent(editStepMainFlowButton)
              .addGap(8)
              .addComponent(deletStepMainFlowButton)))
          .addContainerGap(16, Short.MAX_VALUE))
    );


    mainFlowStepList = new JList();
    mainFlowStepPane.setViewportView(mainFlowStepList);
    mainFlowPanel.setLayout(mainFlowGP);

    JPanel panel_4 = new JPanel();
    panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Postconditions", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    postconditionTextField = new JTextField();
    postconditionTextField.setColumns(10);

    JScrollPane scrollPane_5 = new JScrollPane();

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


    GroupLayout gl_panel_4 = new GroupLayout(panel_4);
    gl_panel_4
        .setHorizontalGroup(gl_panel_4
            .createParallelGroup(Alignment.TRAILING)
            .addGap(0, 426, Short.MAX_VALUE)
            .addGroup(
                gl_panel_4
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        gl_panel_4
                            .createParallelGroup(Alignment.LEADING)
                            .addComponent(postconditionTextField, GroupLayout.DEFAULT_SIZE, 322,
                                Short.MAX_VALUE)
                            .addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 322,
                                Short.MAX_VALUE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(
                        gl_panel_4
                            .createParallelGroup(Alignment.LEADING)
                            .addGroup(
                                Alignment.TRAILING,
                                gl_panel_4
                                    .createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(addPostconditions, Alignment.TRAILING,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                    .addComponent(editPostConditions, Alignment.TRAILING,
                                        GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                            .addComponent(deletePostoconditions, GroupLayout.PREFERRED_SIZE, 61,
                                Short.MAX_VALUE)).addContainerGap()));
    gl_panel_4
        .setVerticalGroup(gl_panel_4
            .createParallelGroup(Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
            .addGroup(
                gl_panel_4
                    .createSequentialGroup()
                    .addGap(6)
                    .addGroup(
                        gl_panel_4
                            .createParallelGroup(Alignment.BASELINE)
                            .addComponent(addPostconditions)
                            .addComponent(postconditionTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(8)
                    .addGroup(
                        gl_panel_4
                            .createParallelGroup(Alignment.LEADING)
                            .addGroup(
                                gl_panel_4.createSequentialGroup().addComponent(editPostConditions)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(deletePostoconditions))
                            .addGroup(
                                gl_panel_4.createParallelGroup(Alignment.BASELINE).addComponent(
                                    scrollPane_5, GroupLayout.PREFERRED_SIZE, 52,
                                    GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(29, Short.MAX_VALUE)));

    postconditions = new JList();
    scrollPane_5.setViewportView(postconditions);
    panel_4.setLayout(gl_panel_4);

    JPanel alternativeFlowPanel = new JPanel();
    alternativeFlowPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
        "Alternative flows", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JButton button_11 = new JButton("Edit");

    JScrollPane scrollPane_6 = new JScrollPane();

    JButton button_13 = new JButton("Delete");

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



    GroupLayout gl_panel_5 = new GroupLayout(alternativeFlowPanel);
    gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING).addGroup(
        gl_panel_5
            .createSequentialGroup()
            .addContainerGap()
            .addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(
                gl_panel_5
                    .createParallelGroup(Alignment.TRAILING)
                    .addGroup(
                        gl_panel_5.createSequentialGroup()
                            .addComponent(button_13, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addContainerGap())
                    .addGroup(
                        gl_panel_5
                            .createSequentialGroup()
                            .addGroup(
                                gl_panel_5
                                    .createParallelGroup(Alignment.TRAILING)
                                    .addComponent(addAlternativeFlowButton, Alignment.LEADING,
                                        GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                    .addComponent(button_11, GroupLayout.DEFAULT_SIZE, 67,
                                        Short.MAX_VALUE)).addContainerGap()))));
    gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addGroup(
        gl_panel_5
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                gl_panel_5
                    .createParallelGroup(Alignment.LEADING, false)
                    .addGroup(
                        Alignment.TRAILING,
                        gl_panel_5
                            .createSequentialGroup()
                            .addComponent(addAlternativeFlowButton)
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(button_11)
                            .addPreferredGap(ComponentPlacement.RELATED).addComponent(button_13))
                    .addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 83,
                        GroupLayout.PREFERRED_SIZE)).addGap(58)));

    alternativeFlows = new JList();
    scrollPane_6.setViewportView(alternativeFlows);
    alternativeFlowPanel.setLayout(gl_panel_5);
    GroupLayout gl_panel = new GroupLayout(panel);
    gl_panel.setHorizontalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
            .addComponent(mainFlowPanel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
            .addComponent(panelPreconditions, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
              .addComponent(mainActorsPanel, 0, 0, Short.MAX_VALUE)
              .addGroup(gl_panel.createSequentialGroup()
                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                  .addComponent(lblDescription)
                  .addComponent(lblName))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
                  .addComponent(scrollPane)
                  .addComponent(name, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))))
            .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
            .addComponent(alternativeFlowPanel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE))
          .addContainerGap(31, Short.MAX_VALUE))
    );
    gl_panel.setVerticalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblName)
            .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
            .addComponent(lblDescription)
            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(mainActorsPanel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panelPreconditions, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(mainFlowPanel, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
          .addGap(18)
          .addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(alternativeFlowPanel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

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
    gl_mainActorsPanel.setHorizontalGroup(gl_mainActorsPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(
            Alignment.TRAILING,
            gl_mainActorsPanel
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(
                    gl_mainActorsPanel
                        .createParallelGroup(Alignment.LEADING, false)
                        .addGroup(
                            gl_mainActorsPanel
                                .createSequentialGroup()
                                .addComponent(comboMainActor, GroupLayout.PREFERRED_SIZE, 120,
                                    GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(addMainActor, GroupLayout.PREFERRED_SIZE, 58,
                                    GroupLayout.PREFERRED_SIZE))
                        .addComponent(deleteMainActor, GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    gl_mainActorsPanel.setVerticalGroup(gl_mainActorsPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(
            gl_mainActorsPanel
                .createSequentialGroup()
                .addGap(6)
                .addGroup(
                    gl_mainActorsPanel
                        .createParallelGroup(Alignment.BASELINE)
                        .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 52,
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
                                .addPreferredGap(ComponentPlacement.RELATED,
                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
