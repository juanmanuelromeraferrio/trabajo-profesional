package ar.fiuba.trabajoprofesional.mdauml.conversion.dialog;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MainEntityDialog extends JDialog {
    private UmlUseCase useCase;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox entitiesCombo;
    private JLabel mainEntityMessage;
    private JCheckBox newBox;
    private JTextField newEntityField;
    private JLabel problem;
    private boolean canceled=false;


    public MainEntityDialog(Window parent, UmlUseCase useCase) {
        super(parent,ModalityType.APPLICATION_MODAL);
        this.useCase = useCase;
        setContentPane(contentPane);
        problem.setText(Msg.get("conversion.dialog.mainEntity.title").replace("@USECASE",useCase.getName()));
        mainEntityMessage.setText(Msg.get("conversion.dialog.mainEntity.message"));
        pack();
        setResizable(false);
        setModal(true);
        setTitle(useCase.getName());
        getRootPane().setDefaultButton(buttonOK);


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


        newBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newBox.isSelected()){
                    entitiesCombo.setEnabled(false);
                    newEntityField.setEnabled(true);
                }else{
                    entitiesCombo.setEnabled(true);
                    newEntityField.setEnabled(false);


                }
            }
        });
        if(useCase.getAllEntities().isEmpty()){
            newBox.setSelected(true);
            newBox.setEnabled(false);
            entitiesCombo.setEnabled(false);
            newEntityField.setEnabled(true);
        }
    }

    private void onOK() {
        String selected;
        if(newBox.isSelected())
            selected = newEntityField.getText();
        else
            selected = (String) entitiesCombo.getSelectedItem();

        if(selected==null || selected.trim().isEmpty())
            return;
        useCase.setMainEntity(selected.trim());
        dispose();
    }

    private void onCancel() {
        if( JOptionPane.showConfirmDialog(this,
                Msg.get("confirm.quit.conversion.message"),
                Msg.get("confirm.quit.conversion.title"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            canceled=true;
            dispose();
        }

    }

    private void createUIComponents() {
        problem= new JLabel();

        mainEntityMessage = new JLabel();
        Set<String> allEntities = useCase.getAllEntities();
        List<String> sortedEntities = new ArrayList<>(allEntities);
        Collections.sort(sortedEntities);
        String[] arrayEntities = new String[sortedEntities.size()];
        sortedEntities.toArray(arrayEntities);
        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel(arrayEntities);
        entitiesCombo = new JComboBox(comboBoxModel);


    }

    public boolean hasCanceled() {
        return false;
    }
}
