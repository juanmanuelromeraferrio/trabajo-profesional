package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import java.awt.Dimension;

import javax.swing.JDialog;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseElement;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



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
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField postcondition;
  private JTextPane description;
  private JList mainActors;
  private JList secondaryActors;
  private JComboBox comboMainActor;
  private JComboBox comboSecActors;
  private JList alternativeFlows;
  private JList<String> postconditions;
  private JList<String> preconditions;


  /**
   * Creates new form EditUseCaseDialog
   * 
   * @param parent the parent frame
   * @param anActor the edited Use Case
   * @param modal whether the dialog is to be modal
   */
  public EditUseCaseDialog(java.awt.Window parent, UseCaseElement anUseCase, boolean modal) {
    super(parent, ModalityType.APPLICATION_MODAL);
    
    useCaseElement = anUseCase;
    initComponents();
    myPostInit();
  }
  
  public String getDescription() {
    return description.getText();
  }
  
  public String getName(){
    return name.getText();
  }
  
  public Set<UmlActor> getMainActors(){
    //TODO: returnActors
    return new HashSet<UmlActor>();
  }
  
  public Set<UmlActor> getSecondaryActors(){
    //TODO: returnActors
    return new HashSet<UmlActor>();
  }
  
  public List<String> getPreconditions(){
    ListModel<String> listmodel = preconditions.getModel();
    List<String> list = new ArrayList<String>();
    for(int i=0 ; i< listmodel.getSize(); i++)
      list.add(listmodel.getElementAt(i));
    return list;
  }
  
  public List<String> getPostconditions(){
    ListModel<String> listmodel = postconditions.getModel();
    List<String> list = new ArrayList<String>();
    for(int i=0 ; i< listmodel.getSize(); i++)
      list.add(listmodel.getElementAt(i));
    return list;
  }
  
  public boolean isOk() {
    return isOk;
  }


  private void myPostInit() {
    UmlUseCase useCase = (UmlUseCase)useCaseElement.getModelElement(); 
    name.setText(useCase.getName()); 
    description.setText(useCase.getDescription());
    comboMainActor.setModel(new DefaultComboBoxModel(new String[] {"Client", "Administrator", "User"}));
    comboSecActors.setModel(new DefaultComboBoxModel(new String[]{"Client","Administrator","User"}));
    
    DefaultListModel<String> mainActorsModel = new DefaultListModel<String>();
    for(UmlActor actor: useCase.getMainActors())
      mainActorsModel.addElement(actor.getName());
    mainActors.setModel(mainActorsModel);
    
    DefaultListModel<String> secActorsModel = new DefaultListModel<String>();
    for(UmlActor actor: useCase.getSecondaryActors())
      secActorsModel.addElement(actor.getName());
    secondaryActors.setModel(secActorsModel);
    //comboMainActor.setModel(new DefaultComboBoxModel(ProjectModel.getInstace().getAllActors());
    //comboMainActor.setModel(new DefaultComboBoxModel(ProjectModel.getInstace().getAllActors());
  }


  private void initComponents() {
    setResizable(false);
    setSize(new Dimension(475, 492));
    setTitle("Use case specification");
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
    JScrollPane mainScrollPanel = new JScrollPane();
    mainScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    
    JButton btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isOk=false;
        dispose();
      }
    });
    
    JButton btnOk = new JButton("OK");
    btnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isOk=true;
        dispose();
      }
    });
    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addComponent(mainScrollPanel, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
          .addContainerGap(341, Short.MAX_VALUE)
          .addComponent(btnOk)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnCancel)
          .addContainerGap())
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addComponent(mainScrollPanel, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
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
    mainActorsPanel.setBorder(new TitledBorder(null, "Main actors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    JPanel panel_1 = new JPanel();
    panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Secondary actors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    JScrollPane scrollPane_2 = new JScrollPane();
    
    comboSecActors = new JComboBox();
    
    JButton button = new JButton("Add");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String selectedActor = (String)comboSecActors.getSelectedItem();
        ((DefaultListModel<String>)secondaryActors.getModel()).addElement(selectedActor); 
      }
    });
    
    JButton button_1 = new JButton("Delete");
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int selectedActor = secondaryActors.getSelectedIndex();
        ((DefaultListModel<String>)secondaryActors.getModel()).remove(selectedActor);
      }
    });
    GroupLayout gl_panel_1 = new GroupLayout(panel_1);
    gl_panel_1.setHorizontalGroup(
      gl_panel_1.createParallelGroup(Alignment.LEADING)
        .addGap(0, 426, Short.MAX_VALUE)
        .addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
          .addContainerGap()
          .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
            .addGroup(gl_panel_1.createSequentialGroup()
              .addComponent(comboSecActors, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(button, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
            .addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    gl_panel_1.setVerticalGroup(
      gl_panel_1.createParallelGroup(Alignment.LEADING)
        .addGap(0, 82, Short.MAX_VALUE)
        .addGroup(gl_panel_1.createSequentialGroup()
          .addGap(6)
          .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_1.createSequentialGroup()
              .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                .addComponent(comboSecActors, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(button))
              .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(button_1))
            .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
              .addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    
    secondaryActors = new JList();
    secondaryActors.setVisibleRowCount(3);
    scrollPane_2.setViewportView(secondaryActors);
    panel_1.setLayout(gl_panel_1);
    
    JPanel panel_2 = new JPanel();
    panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Preconditions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    JScrollPane scrollPane_3 = new JScrollPane();
    
    JButton button_2 = new JButton("Add");
    
    JButton button_3 = new JButton("Delete");
    button_3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
      }
    });
    
    textField_1 = new JTextField();
    textField_1.setColumns(10);
    
    JButton button_6 = new JButton("Edit");
    GroupLayout gl_panel_2 = new GroupLayout(panel_2);
    gl_panel_2.setHorizontalGroup(
      gl_panel_2.createParallelGroup(Alignment.TRAILING)
        .addGroup(gl_panel_2.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
            .addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, gl_panel_2.createParallelGroup(Alignment.LEADING, false)
              .addComponent(button_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(button_6, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
            .addComponent(button_3, GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE))
          .addContainerGap())
    );
    gl_panel_2.setVerticalGroup(
      gl_panel_2.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel_2.createSequentialGroup()
          .addGap(6)
          .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
            .addComponent(button_2)
            .addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addGap(8)
          .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
            .addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
            .addGroup(gl_panel_2.createSequentialGroup()
              .addComponent(button_6)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(button_3)))
          .addContainerGap(29, Short.MAX_VALUE))
    );
    
    preconditions = new JList();
    scrollPane_3.setViewportView(preconditions);
    panel_2.setLayout(gl_panel_2);
    
    JPanel panel_3 = new JPanel();
    panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Main flow", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    JButton button_4 = new JButton("Edit");
    
    textField_2 = new JTextField();
    textField_2.setColumns(10);
    
    JScrollPane scrollPane_4 = new JScrollPane();
    
    JButton button_5 = new JButton("Add");
    
    JButton button_7 = new JButton("Delete");
    GroupLayout gl_panel_3 = new GroupLayout(panel_3);
    gl_panel_3.setHorizontalGroup(
      gl_panel_3.createParallelGroup(Alignment.TRAILING)
        .addGap(0, 426, Short.MAX_VALUE)
        .addGroup(gl_panel_3.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
            .addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, gl_panel_3.createParallelGroup(Alignment.LEADING, false)
              .addComponent(button_5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(button_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
            .addComponent(button_7, GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE))
          .addContainerGap())
    );
    gl_panel_3.setVerticalGroup(
      gl_panel_3.createParallelGroup(Alignment.LEADING)
        .addGap(0, 114, Short.MAX_VALUE)
        .addGroup(gl_panel_3.createSequentialGroup()
          .addGap(6)
          .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
            .addComponent(button_5)
            .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addGap(8)
          .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_3.createSequentialGroup()
              .addComponent(button_4)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(button_7))
            .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
              .addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap(29, Short.MAX_VALUE))
    );
    
    JList list_3 = new JList();
    scrollPane_4.setViewportView(list_3);
    panel_3.setLayout(gl_panel_3);
    
    JPanel panel_4 = new JPanel();
    panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Postconditions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    JButton button_8 = new JButton("Edit");
    
    postcondition = new JTextField();
    postcondition.setColumns(10);
    
    JScrollPane scrollPane_5 = new JScrollPane();
    
    JButton button_9 = new JButton("Add");
    
    JButton button_10 = new JButton("Delete");
    GroupLayout gl_panel_4 = new GroupLayout(panel_4);
    gl_panel_4.setHorizontalGroup(
      gl_panel_4.createParallelGroup(Alignment.TRAILING)
        .addGap(0, 426, Short.MAX_VALUE)
        .addGroup(gl_panel_4.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
            .addComponent(postcondition, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
          .addPreferredGap(ComponentPlacement.UNRELATED)
          .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, gl_panel_4.createParallelGroup(Alignment.LEADING, false)
              .addComponent(button_9, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(button_8, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
            .addComponent(button_10, GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE))
          .addContainerGap())
    );
    gl_panel_4.setVerticalGroup(
      gl_panel_4.createParallelGroup(Alignment.LEADING)
        .addGap(0, 114, Short.MAX_VALUE)
        .addGroup(gl_panel_4.createSequentialGroup()
          .addGap(6)
          .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
            .addComponent(button_9)
            .addComponent(postcondition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addGap(8)
          .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel_4.createSequentialGroup()
              .addComponent(button_8)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(button_10))
            .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
              .addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap(29, Short.MAX_VALUE))
    );
    
    postconditions = new JList();
    scrollPane_5.setViewportView(postconditions);
    panel_4.setLayout(gl_panel_4);
    
    JPanel panel_5 = new JPanel();
    panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Alternative flows", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    JButton button_11 = new JButton("Edit");
    
    JScrollPane scrollPane_6 = new JScrollPane();
    
    JButton button_13 = new JButton("Delete");
    
    JButton btnAdd_1 = new JButton("Add");
    GroupLayout gl_panel_5 = new GroupLayout(panel_5);
    gl_panel_5.setHorizontalGroup(
      gl_panel_5.createParallelGroup(Alignment.TRAILING)
        .addGroup(gl_panel_5.createSequentialGroup()
          .addContainerGap()
          .addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panel_5.createSequentialGroup()
              .addComponent(button_13, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
              .addContainerGap())
            .addGroup(gl_panel_5.createSequentialGroup()
              .addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
                .addComponent(btnAdd_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addComponent(button_11, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
              .addContainerGap())))
    );
    gl_panel_5.setVerticalGroup(
      gl_panel_5.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel_5.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
            .addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
              .addComponent(btnAdd_1)
              .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(button_11)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(button_13))
            .addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
          .addGap(58))
    );
    
    alternativeFlows = new JList();
    scrollPane_6.setViewportView(alternativeFlows);
    panel_5.setLayout(gl_panel_5);
    GroupLayout gl_panel = new GroupLayout(panel);
    gl_panel.setHorizontalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
            .addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
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
            .addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE))
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
          .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    
    JScrollPane scrollPane_1 = new JScrollPane();
    
    comboMainActor = new JComboBox();
    
    JButton addMainActor = new JButton("Add");
    addMainActor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        String selectedActor = (String)comboMainActor.getSelectedItem();
        ((DefaultListModel<String>)mainActors.getModel()).addElement(selectedActor);        
      }
    });
    
    JButton deleteMainActor = new JButton("Delete");
    deleteMainActor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        int selectedActor = mainActors.getSelectedIndex();
        ((DefaultListModel<String>)mainActors.getModel()).remove(selectedActor);
      }
    });
    GroupLayout gl_mainActorsPanel = new GroupLayout(mainActorsPanel);
    gl_mainActorsPanel.setHorizontalGroup(
      gl_mainActorsPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(Alignment.TRAILING, gl_mainActorsPanel.createSequentialGroup()
          .addContainerGap()
          .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_mainActorsPanel.createParallelGroup(Alignment.LEADING, false)
            .addGroup(gl_mainActorsPanel.createSequentialGroup()
              .addComponent(comboMainActor, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(addMainActor, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
            .addComponent(deleteMainActor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    gl_mainActorsPanel.setVerticalGroup(
      gl_mainActorsPanel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_mainActorsPanel.createSequentialGroup()
          .addGap(6)
          .addGroup(gl_mainActorsPanel.createParallelGroup(Alignment.BASELINE)
            .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
            .addGroup(gl_mainActorsPanel.createSequentialGroup()
              .addGroup(gl_mainActorsPanel.createParallelGroup(Alignment.BASELINE)
                .addComponent(comboMainActor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(addMainActor))
              .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(deleteMainActor)))
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    
    mainActors = new JList();
    mainActors.setVisibleRowCount(3);

    scrollPane_1.setViewportView(mainActors);
    mainActorsPanel.setLayout(gl_mainActorsPanel);
    panel.setLayout(gl_panel);
    getContentPane().setLayout(groupLayout);
    
  }


  


  
}
