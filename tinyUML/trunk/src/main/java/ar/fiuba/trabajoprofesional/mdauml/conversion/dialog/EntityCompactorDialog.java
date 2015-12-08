package ar.fiuba.trabajoprofesional.mdauml.conversion.dialog;

import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class EntityCompactorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList allEntities;
    private JTextField mergedEntity;
    private JButton mergeButton;
    private JButton add;
    private JButton remove;
    private JList mergingEntities;
    private JLabel mergedLabel;
    private JLabel mergingLabel;
    private JLabel messageLabel;
    private Map<String,String> mergingMap;
    private Map<String,String> originalMap;
    private List<String> entities;

    public EntityCompactorDialog(Window parent, Map<String,String> mergingMap) {
        super(parent,ModalityType.APPLICATION_MODAL);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(Msg.get("conversion.dialog.entityCompactor.title"));
        mergedLabel.setText(Msg.get("conversion.dialog.entityCompactor.mergedLabel"));
        mergingLabel.setText(Msg.get("conversion.dialog.entityCompactor.mergingLabel"));
        messageLabel.setText(Msg.get("conversion.dialog.entityCompactor.messageLabel"));
        mergeButton.setText(Msg.get("conversion.dialog.entityCompactor.mergeButton"));
        buttonOK.setText(Msg.get("stdcaption.ok"));
        buttonCancel.setText(Msg.get("stdcaption.cancel"));


        this.mergingMap=mergingMap;
        this.originalMap = new HashMap<>(mergingMap);
        this.entities = new ArrayList<>();
        initEntityList();
        mergingEntities.setModel(new DefaultListModel());

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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selected = allEntities.getSelectedValuesList();
                DefaultListModel<String> model = ((DefaultListModel<String>) mergingEntities.getModel());
                model.ensureCapacity(model.size()+selected.size());
                for(int i = 0 , j=model.size(); i < selected.size() ; i++) {
                    if(model.contains(selected.get(i)))
                        continue;
                    model.add(j++,selected.get(i));
                }
            }
        });
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedEntities = mergingEntities.getSelectedValuesList();
                DefaultListModel<String> model = ((DefaultListModel<String>) mergingEntities.getModel());
                for(String entity : selectedEntities){
                    model.removeElement(entity);
                }

            }
        });
        mergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> model = ((DefaultListModel<String>) mergingEntities.getModel());
                List<String> mergingElements = new ArrayList<String>();
                for (int i = 0 ; i < model.size() ; i++)
                    mergingElements.add(i,model.get(i));
                String merged = mergedEntity.getText();

                if(mergingElements.size()<2){
                    JOptionPane.showMessageDialog(AppFrame.get(), Msg.get("conversion.dialog.entityCompactor.moreThan2"));
                    return;
                }
                if(merged.isEmpty()) {
                    JOptionPane.showMessageDialog(AppFrame.get(), Msg.get("conversion.dialog.entityCompactor.emptyField"));
                    return;
                }
                doMerge(mergingElements,merged);
                model.clear();
                initEntityList();
                mergedEntity.setText("");



            }
        });
        pack();
        setResizable(false);
    }

    private void doMerge(List<String> mergingElements, String merged) {
        for(String mergingEntity : mergingElements)
            for(String entity : mergingMap.keySet())
                if(mergingMap.get(entity).equals(mergingEntity))
                    mergingMap.put(entity,merged);

    }

    private void initEntityList() {
        entities.clear();
        entities.addAll(new HashSet<>(mergingMap.values()));
        Collections.sort(entities);
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.ensureCapacity(entities.size());
        for(int i=0; i < entities.size(); i++)
            model.add(i,entities.get(i));
        allEntities.setModel(model);
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        mergingMap.clear();
        mergingMap.putAll(originalMap);
        dispose();
    }


    private void createUIComponents() {

    }
}
