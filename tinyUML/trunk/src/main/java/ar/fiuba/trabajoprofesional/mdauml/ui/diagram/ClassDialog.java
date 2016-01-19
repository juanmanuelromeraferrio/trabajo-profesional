package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlBoundary;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlClass;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlStereotype;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.ClassElement;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class ClassDialog extends JDialog {
    private static final int CLASS_INDEX = 0;
    private static final int ATTRIBUTES_INDEX = 1;
    private static final int METHODS_INDEX = 2;
    private UmlClass classModel;
    private ClassElement classElement;
    private DefaultListModel<String> stereotypeListModel;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel footer;
    private JPanel mainPanel;
    private JTabbedPane tabbedPanel;
    private JPanel classPanel;
    private JPanel attributesPanel;
    private JPanel methodsPanel;
    private JPanel namePanel;
    private JLabel classNameLabel;
    private JTextField classNameField;
    private JCheckBox classAbstractCheckbox;
    private JPanel stereotypePanel;
    private JCheckBox stereotypesVisible;
    private JList stereotypeList;
    private JButton addStereotypeButton;
    private JButton removeStereotypeButton;
    private JPanel stereotypesLeft;
    private JPanel stereotypesRight;
    private JButton upStereotypeButton;
    private JButton downStereotypeButton;
    private JPanel docPanel;
    private JTextArea documentationArea;
    private JTable attributesTable;
    private JButton addAttributeButton;
    private JButton deleteAttributeButton;
    private JButton moveUpAttributeButton;
    private JButton moveDownAttributeButton;
    private JLabel stereotypesLabel;

    public ClassDialog(Window parent, ClassElement aClassElement) {
        super(parent, ModalityType.APPLICATION_MODAL);
        setContentPane(contentPane);
        setModal(true);
        this.classElement = aClassElement;
        this.classModel = (UmlClass) aClassElement.getModelElement();
        getRootPane().setDefaultButton(buttonOK);

        tabbedPanel.setTitleAt(CLASS_INDEX, Msg.get("classeditor.tab.class"));

        tabbedPanel.setTitleAt(ATTRIBUTES_INDEX, Msg.get("classeditor.tab.attributes"));

        tabbedPanel.setTitleAt(METHODS_INDEX, Msg.get("classeditor.tab.methods"));

        classNameLabel.setText(Msg.get("classeditor.className"));

        classAbstractCheckbox.setText(Msg.get("classeditor.isAbstract"));

        ((TitledBorder) stereotypePanel.getBorder()).setTitle(Msg.get("classeditor.stereotypes"));

        stereotypesVisible.setText(Msg.get("classeditor.visible"));

        addStereotypeButton.setText(Msg.get("classeditor.add"));

        removeStereotypeButton.setText(Msg.get("classeditor.remove"));

        upStereotypeButton.setText(Msg.get("classeditor.moveUp"));

        downStereotypeButton.setText(Msg.get("classeditor.moveDown"));

        ((TitledBorder) docPanel.getBorder()).setTitle(Msg.get("classeditor.documentation"));

        stereotypeListModel = new DefaultListModel<String>();
        for (int i = 0; i < classModel.getStereotypes().size(); i++) {
            UmlStereotype umlStereotype = classModel.getStereotypes().get(i);
            stereotypeListModel.addElement(umlStereotype.getName().substring(2, umlStereotype.getName().length() - 2));
        }

        stereotypeList.setModel(stereotypeListModel);

        classNameField.setText(classModel.getName());
        classAbstractCheckbox.setSelected(classModel.isAbstract());
        stereotypesVisible.setSelected(classElement.showStereotypes());

        ////////////////////////////////////////////////////

//        TableColumn returnTypeColumn = new TableColumn(0, 10);
//        returnTypeColumn.setHeaderValue(Msg.get("classeditor.returnType"));
//        TableColumn nameColumn = new TableColumn(1, 50);
//        nameColumn.setHeaderValue(Msg.get("classeditor.nameColumn"));

//        TableColumn argumentsColumn = new TableColumn(2, 200);
//        argumentsColumn.setHeaderValue(Msg.get("classeditor.argumentsColumn"));


        String typeAttColumnName = Msg.get("classeditor.type");
        String nameAttColumnName = Msg.get("classeditor.nameColumn");
        String visibleAttColumnName = Msg.get("classeditor.visible");

        final DefaultTableModel attributesTableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int index) {
                if (index == 2)
                    return Boolean.class;

                return String.class;
            }
        };

        attributesTableModel.addColumn(typeAttColumnName);
        attributesTableModel.addColumn(nameAttColumnName);
        attributesTableModel.addColumn(visibleAttColumnName);

        attributesTable.setModel(attributesTableModel);
        attributesTable.getColumn(typeAttColumnName).setPreferredWidth(100);
        attributesTable.getColumn(nameAttColumnName).setPreferredWidth(400);
        attributesTable.getColumn(visibleAttColumnName).setPreferredWidth(60);



        addAttributeButton.setText(Msg.get("classeditor.add"));
        deleteAttributeButton.setText(Msg.get("classeditor.remove"));
        moveUpAttributeButton.setText(Msg.get("classeditor.moveUp"));
        moveDownAttributeButton.setText(Msg.get("classeditor.moveDown"));


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addStereotypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stereotypeListModel.addElement("new stereotype");
                stereotypeList.setSelectedIndex(stereotypeListModel.getSize() - 1);
                stereotypeList.ensureIndexIsVisible(stereotypeList.getSelectedIndex());
            }
        });
        removeStereotypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stereotypeListModel.remove(stereotypeList.getSelectedIndex());
            }
        });
        upStereotypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) stereotypeList.getSelectedValue();
                int index = stereotypeList.getSelectedIndex();
                if (index == 0)
                    return;
                stereotypeListModel.setElementAt(stereotypeListModel.get(index - 1), index);

                stereotypeListModel.setElementAt(selected, index - 1);
                stereotypeList.setSelectedIndex(index - 1);
            }
        });
        downStereotypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selected = (String) stereotypeList.getSelectedValue();
                int index = stereotypeList.getSelectedIndex();
                if (index == stereotypeListModel.getSize() - 1)
                    return;
                stereotypeListModel.setElementAt(stereotypeListModel.get(index + 1), index);

                stereotypeListModel.setElementAt(selected, index + 1);
                stereotypeList.setSelectedIndex(index + 1);

            }
        });
        addAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attributesTableModel.addRow(new Object[]{"int", "newAttribute", true});
            }
        });
        deleteAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = attributesTable.getSelectedRow();
                if (selected == -1)
                    return;
                attributesTableModel.removeRow(selected);
            }
        });
        moveUpAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = attributesTable.getSelectedRow();
                if (selected == -1 || selected == 0)
                    return;
                Vector rows = attributesTableModel.getDataVector();
                Object rowSelected = rows.get(selected);
                Object rowAbove = rows.get(selected - 1);
                rows.setElementAt(rowSelected, selected - 1);
                rows.setElementAt(rowAbove, selected);
                attributesTable.setRowSelectionInterval(selected - 1, selected - 1);

            }
        });
        moveDownAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = attributesTable.getSelectedRow();
                if (selected == -1 || selected == attributesTableModel.getRowCount() - 1)
                    return;
                Vector rows = attributesTableModel.getDataVector();
                Object rowSelected = rows.get(selected);
                Object rowBelow = rows.get(selected + 1);
                rows.setElementAt(rowSelected, selected + 1);
                rows.setElementAt(rowBelow, selected);
                attributesTable.setRowSelectionInterval(selected + 1, selected + 1);
            }
        });
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ClassElement element = (ClassElement) ClassElement.getPrototype().clone();
        UmlModelElement model = (UmlModelElement) UmlBoundary.getPrototype().clone();
        model.setName("MiClase");
        element.setModelElement(model);
        ClassDialog dialog = new ClassDialog(AppFrame.get(), element);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        footer = new JPanel();
        footer.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(footer, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        footer.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        footer.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel1.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel1.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(700, 500), null, 0, false));
        tabbedPanel = new JTabbedPane();
        mainPanel.add(tabbedPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        classPanel = new JPanel();
        classPanel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        tabbedPanel.addTab("Untitled", classPanel);
        namePanel = new JPanel();
        namePanel.setLayout(new GridLayoutManager(1, 3, new Insets(20, 20, 20, 20), -1, -1));
        classPanel.add(namePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        namePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(namePanel.getFont().getName(), namePanel.getFont().getStyle(), namePanel.getFont().getSize()), new Color(-4473925)));
        classNameLabel = new JLabel();
        classNameLabel.setText("Label");
        namePanel.add(classNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        classNameField = new JTextField();
        namePanel.add(classNameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        classAbstractCheckbox = new JCheckBox();
        classAbstractCheckbox.setText("CheckBox");
        namePanel.add(classAbstractCheckbox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stereotypePanel = new JPanel();
        stereotypePanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 20, 10, 20), -1, -1));
        stereotypePanel.setEnabled(true);
        classPanel.add(stereotypePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        stereotypePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-16777216)));
        stereotypesVisible = new JCheckBox();
        stereotypesVisible.setText("CheckBox");
        stereotypePanel.add(stereotypesVisible, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stereotypesLeft = new JPanel();
        stereotypesLeft.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        stereotypePanel.add(stereotypesLeft, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 70), new Dimension(-1, 70), 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        stereotypesLeft.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 60), new Dimension(-1, 60), 0, false));
        stereotypeList = new JList();
        scrollPane1.setViewportView(stereotypeList);
        stereotypesRight = new JPanel();
        stereotypesRight.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        stereotypePanel.add(stereotypesRight, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 60), new Dimension(-1, 60), 0, false));
        addStereotypeButton = new JButton();
        addStereotypeButton.setText("Button");
        stereotypesRight.add(addStereotypeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeStereotypeButton = new JButton();
        removeStereotypeButton.setText("Button");
        stereotypesRight.add(removeStereotypeButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        upStereotypeButton = new JButton();
        upStereotypeButton.setText("Button");
        stereotypesRight.add(upStereotypeButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        downStereotypeButton = new JButton();
        downStereotypeButton.setText("Button");
        stereotypesRight.add(downStereotypeButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        docPanel = new JPanel();
        docPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 20, 10, 20), -1, -1));
        classPanel.add(docPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        docPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JScrollPane scrollPane2 = new JScrollPane();
        docPanel.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        documentationArea = new JTextArea();
        scrollPane2.setViewportView(documentationArea);
        attributesPanel = new JPanel();
        attributesPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPanel.addTab("Untitled", attributesPanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        attributesPanel.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        attributesPanel.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel3.add(scrollPane3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        attributesTable = new JTable();
        attributesTable.setCellSelectionEnabled(true);
        attributesTable.setEditingRow(-1);
        attributesTable.setFocusCycleRoot(false);
        scrollPane3.setViewportView(attributesTable);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        attributesPanel.add(panel4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addAttributeButton = new JButton();
        addAttributeButton.setText("Button");
        panel4.add(addAttributeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        deleteAttributeButton = new JButton();
        deleteAttributeButton.setText("Button");
        panel4.add(deleteAttributeButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveUpAttributeButton = new JButton();
        moveUpAttributeButton.setText("Button");
        panel4.add(moveUpAttributeButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveDownAttributeButton = new JButton();
        moveDownAttributeButton.setText("Button");
        panel4.add(moveDownAttributeButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        methodsPanel = new JPanel();
        methodsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPanel.addTab("Untitled", methodsPanel);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
