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
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;


public class EditStepMainFlowDialog extends javax.swing.JDialog {

  /**
   * 
   */
  private static final long serialVersionUID = 4748892124691187498L;


  private Boolean isOk;
  private JList<String> entities;
  private JComboBox<String> comboEntities;
  private JTextPane stepDescription;

  /**
   * Creates new form EditUseCaseDialog
   *
   * @param parent the parent frame
   */
  public EditStepMainFlowDialog(java.awt.Window parent) {
    super(parent, ModalityType.APPLICATION_MODAL);

    initComponents();
    myPostInit();
  }


  public Set<String> getEntities() {
    DefaultListModel<String> model = (DefaultListModel<String>) entities.getModel();
    Enumeration<String> elements = model.elements();
    Set<String> set = new HashSet<String>(Collections.list(elements));
    return set;
  }

  private void myPostInit() {}

  private void initComponents() {
    setResizable(false);
    setSize(new Dimension(475, 280));
    setTitle("Main Flow Step");
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JScrollPane mainScrollPanel = new JScrollPane();
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
    groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addComponent(mainScrollPanel, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
        .addGroup(Alignment.TRAILING,
            groupLayout.createSequentialGroup().addContainerGap(170, Short.MAX_VALUE)
                .addComponent(btnOk).addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnCancel).addContainerGap()));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
            .addComponent(mainScrollPanel, GroupLayout.PREFERRED_SIZE, 215,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout
                .createParallelGroup(Alignment.LEADING).addComponent(btnCancel).addComponent(btnOk))
            .addContainerGap()));

    JPanel panel = new JPanel();
    mainScrollPanel.setViewportView(panel);

    JPanel stepPanel = new JPanel();
    stepPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Step",
        TitledBorder.LEADING, TitledBorder.TOP, null, null));

    JScrollPane scrollPaneStep = new JScrollPane();

    JButton saveStep = new JButton("Save");
    saveStep.addActionListener(new ActionListener() {
      @SuppressWarnings({"unchecked", "rawtypes"})
      public void actionPerformed(ActionEvent arg0) {

        // Remove all entities
        entities.setModel(new DefaultListModel<String>());

        List<String> entityModel = new ArrayList<String>();
        String description = stepDescription.getText();
        String[] split = description.split(" ");
        List<String> entitiesList = Arrays.asList(split);
        for (String entity : entitiesList) {

          String entityFormated = entity.substring(0, 1).toUpperCase() + entity.substring(1);

          if (!entityModel.contains(entityFormated)) {
            entityModel.add(entityFormated);
          }

          Collections.sort(entityModel);
          comboEntities.setModel(new DefaultComboBoxModel(entityModel.toArray()));
          stepDescription.setEnabled(false);
        }
      }
    });

    JButton editStep = new JButton("Edit");
    editStep.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        stepDescription.setEnabled(true);
      }
    });


    JLabel typeLabel = new JLabel("Type");

    JComboBox comboBox = new JComboBox();

    GroupLayout gropuLayoutStepPanel = new GroupLayout(stepPanel);
    gropuLayoutStepPanel.setHorizontalGroup(gropuLayoutStepPanel
        .createParallelGroup(Alignment.TRAILING)
        .addGroup(gropuLayoutStepPanel.createSequentialGroup().addContainerGap()
            .addGroup(gropuLayoutStepPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gropuLayoutStepPanel.createSequentialGroup()
                    .addComponent(scrollPaneStep, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gropuLayoutStepPanel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(saveStep, GroupLayout.PREFERRED_SIZE, 63,
                            GroupLayout.PREFERRED_SIZE)
                        .addComponent(editStep, GroupLayout.PREFERRED_SIZE, 63, Short.MAX_VALUE)))
                .addGroup(gropuLayoutStepPanel.createSequentialGroup().addComponent(typeLabel)
                    .addGap(18).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 313,
                        GroupLayout.PREFERRED_SIZE)))
            .addContainerGap()));
    gropuLayoutStepPanel
        .setVerticalGroup(gropuLayoutStepPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(gropuLayoutStepPanel.createSequentialGroup()
                .addGroup(gropuLayoutStepPanel.createParallelGroup(Alignment.BASELINE)
                    .addComponent(typeLabel).addComponent(comboBox, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(gropuLayoutStepPanel.createParallelGroup(Alignment.BASELINE)
                .addComponent(scrollPaneStep, GroupLayout.PREFERRED_SIZE, 52,
                    GroupLayout.PREFERRED_SIZE)
                .addGroup(gropuLayoutStepPanel.createSequentialGroup().addComponent(saveStep)
                    .addPreferredGap(ComponentPlacement.RELATED).addComponent(editStep)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    stepDescription = new JTextPane();
    scrollPaneStep.setViewportView(stepDescription);
    stepPanel.setLayout(gropuLayoutStepPanel);

    JPanel entitiesPanel = new JPanel();
    entitiesPanel.setBorder(
        new TitledBorder(null, "Entities", TitledBorder.LEADING, TitledBorder.TOP, null, null));

    GroupLayout firstLayout = new GroupLayout(panel);
    firstLayout.setHorizontalGroup(firstLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(firstLayout.createSequentialGroup().addContainerGap()
            .addGroup(firstLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(firstLayout.createSequentialGroup()
                    .addComponent(stepPanel, GroupLayout.PREFERRED_SIZE, 426,
                        GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            .addGroup(firstLayout.createSequentialGroup()
                .addComponent(entitiesPanel, 0, 0, Short.MAX_VALUE).addGap(18)))));
    firstLayout
        .setVerticalGroup(firstLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(firstLayout.createSequentialGroup().addGap(6)
                .addComponent(stepPanel, GroupLayout.PREFERRED_SIZE, 114,
                    GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED).addComponent(entitiesPanel,
                    GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(114, Short.MAX_VALUE)));

    JScrollPane scrollPaneEntity = new JScrollPane();

    comboEntities = new JComboBox();

    JButton addEntity = new JButton("Add");
    addEntity.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        String selectedEntity = (String) comboEntities.getSelectedItem();
        DefaultListModel<String> entityModelList = (DefaultListModel<String>) entities.getModel();

        if (!entityModelList.contains(selectedEntity)) {
          ((DefaultListModel<String>) entities.getModel()).addElement(selectedEntity);
        }
      }
    });

    JButton deleteEntity = new JButton("Delete");
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
    groupLayoutEntityPanel.setHorizontalGroup(groupLayoutEntityPanel
        .createParallelGroup(Alignment.LEADING)
        .addGroup(Alignment.TRAILING, groupLayoutEntityPanel.createSequentialGroup()
            .addContainerGap()
            .addComponent(scrollPaneEntity, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(groupLayoutEntityPanel.createParallelGroup(Alignment.LEADING, false)
                .addGroup(groupLayoutEntityPanel.createSequentialGroup()
                    .addComponent(comboEntities, GroupLayout.PREFERRED_SIZE, 120,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED).addComponent(addEntity,
                        GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                .addComponent(deleteEntity, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    groupLayoutEntityPanel
        .setVerticalGroup(groupLayoutEntityPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayoutEntityPanel.createSequentialGroup().addGap(6)
                .addGroup(groupLayoutEntityPanel.createParallelGroup(Alignment.BASELINE)
                    .addComponent(scrollPaneEntity, GroupLayout.PREFERRED_SIZE, 52,
                        GroupLayout.PREFERRED_SIZE)
                .addGroup(groupLayoutEntityPanel.createSequentialGroup()
                    .addGroup(groupLayoutEntityPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(comboEntities, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(addEntity))
                    .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addComponent(deleteEntity)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    entities = new JList<>(new DefaultListModel<String>());
    entities.setVisibleRowCount(3);

    scrollPaneEntity.setViewportView(entities);
    entitiesPanel.setLayout(groupLayoutEntityPanel);
    panel.setLayout(firstLayout);
    getContentPane().setLayout(groupLayout);

  }
}
