package ar.fiuba.trabajoprofesional.mdauml.conversion.dialog;

import ar.fiuba.trabajoprofesional.mdauml.util.Msg;
import ar.fiuba.trabajoprofesional.mdauml.util.StringHelper;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class DiagramResolverDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList allEntities;
    private JComboBox comboBox;
    private JTextField newDiagramField;
    private JButton newDiagramButton;
    private JButton addButton;
    private JList diagramEntities;
    private JButton removeButton;
    private JLabel description;
    private JLabel diagramLabel;
    private JLabel selectLabel;
    private JLabel allEntitiesLabel;
    private JButton deleteDiagram;
    private JButton renameButton;
    private Map<String,List<String>> diagramMap;
    private List<String> mainEntities;

    public DiagramResolverDialog(Map<String,List<String>> diagramMap, List<String> mainEntities) {
        setContentPane(contentPane);
        setModal(true);
        this.diagramMap = diagramMap;
        this.mainEntities = mainEntities;
        getRootPane().setDefaultButton(buttonOK);
        setTitle(Msg.get("conversion.dialog.resolver.title"));

        DefaultListModel<String> allEntitiesModel = new DefaultListModel<>();
        for(int i =0 ; i < mainEntities.size();i++)
            allEntitiesModel.add(i,mainEntities.get(i));
        allEntities.setModel(allEntitiesModel);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        List<String> diagrams = new ArrayList<>( diagramMap.keySet());
        for(String diagram : diagrams)
            comboBoxModel.addElement(diagram);
        comboBoxModel.setSelectedItem(comboBoxModel.getElementAt(0));
        comboBox.setModel(comboBoxModel);

        DefaultListModel<String> diagramEntitiesModel = new DefaultListModel<>();
        List<String> selectedDiagramEntities = diagramMap.get(diagrams.get(0));
        for(int i =0 ; i < selectedDiagramEntities.size() ;i++)
            diagramEntitiesModel.add(i,selectedDiagramEntities.get(i));
        diagramEntities.setModel(diagramEntitiesModel);

        description.setText(Msg.get("conversion.dialog.resolver.description"));
        selectLabel.setText(Msg.get("conversion.dialog.resolver.select"));
        allEntitiesLabel.setText(Msg.get("conversion.dialog.resolver.allEntities"));
        diagramLabel.setText(Msg.get("conversion.dialog.resolver.diagramEntities"));

        addButton.setText(Msg.get("conversion.dialog.resolver.addButton"));
        removeButton.setText(Msg.get("conversion.dialog.resolver.removeButton"));
        newDiagramButton.setText(Msg.get("conversion.dialog.resolver.newDiagramButton"));
        renameButton.setText(Msg.get("conversion.dialog.resolver.renameButton"));
        deleteDiagram.setText(Msg.get("conversion.dialog.resolver.deleteDiagram"));
        if(diagramMap.keySet().size()==1)
            deleteDiagram.setEnabled(false);

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
        newDiagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNewDiagram();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove();

            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSelect();
            }
        });
        deleteDiagram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteDiagram();
            }
        });
        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRename();
            }
        });
    }

    private void onRename() {
        String diagram = (String) comboBox.getSelectedItem();
        List<String> entities = diagramMap.get(diagram);

        String renameDiagram = newDiagramField.getText().trim();
        if(renameDiagram.isEmpty())
            return;
        Set<String> diagrams = diagramMap.keySet();
        if(diagrams.contains(renameDiagram))
            return;
        diagramMap.remove(diagram);
        diagramMap.put(renameDiagram,entities);
        List<String> newDiagrams = new ArrayList<>(diagramMap.keySet());
        Collections.sort(newDiagrams);
        comboBox.setModel(new DefaultComboBoxModel(newDiagrams.toArray()));
        newDiagramField.setText("");
        comboBox.setSelectedItem(renameDiagram);

    }

    private void onDeleteDiagram() {
        if(diagramMap.keySet().size()==1)
            return;
        String diagram = (String) comboBox.getSelectedItem();
        diagramMap.remove(diagram);
        ((DefaultComboBoxModel)comboBox.getModel()).removeElement(diagram);
        if(diagramMap.keySet().size()==1)
            deleteDiagram.setEnabled(false);
    }

    private void onSelect() {

        String diagram = (String) comboBox.getSelectedItem();
        ((DefaultListModel)diagramEntities.getModel()).clear();
        for(String entity : diagramMap.get(diagram))
            ((DefaultListModel)diagramEntities.getModel()).addElement(entity);

    }

    private void onRemove() {
        List<String> selected = diagramEntities.getSelectedValuesList();
        for(String entity : selected){
            ((DefaultListModel)diagramEntities.getModel()).removeElement(entity);
        }
        String diagram = (String) comboBox.getSelectedItem();
        diagramMap.get(diagram).removeAll(selected);
    }

    private void onAdd() {

        List<String> newDiagramEntities = allEntities.getSelectedValuesList();
        Enumeration enumeration = ((DefaultListModel) diagramEntities.getModel()).elements();
        while(enumeration.hasMoreElements()){
            String entity = (String) enumeration.nextElement();
            if(newDiagramEntities.contains(entity))
                continue;
            newDiagramEntities.add(entity);
        }
        Collections.sort(newDiagramEntities);
        ((DefaultListModel) diagramEntities.getModel()).clear();
        for(String entity : newDiagramEntities)
            ((DefaultListModel) diagramEntities.getModel()).addElement(entity);

        String diagram = (String) comboBox.getSelectedItem();
        diagramMap.put(diagram,newDiagramEntities);

    }

    private void onNewDiagram() {
        String diagram = newDiagramField.getText().trim();
        if(diagram.isEmpty())
            return;
        Set<String> diagrams = diagramMap.keySet();
        if(diagrams.contains(diagram))
            return;
        List<String> newDiagrams = new ArrayList<>(diagrams);
        newDiagrams.add(diagram);
        Collections.sort(newDiagrams);
        comboBox.setModel(new DefaultComboBoxModel(newDiagrams.toArray()));
        diagramMap.put(newDiagramField.getText(),new ArrayList<String>());
        newDiagramField.setText("");
        comboBox.setSelectedItem(diagram);
        deleteDiagram.setEnabled(true);

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
        List<String> allEntities = new ArrayList<>();
        allEntities.add("Alberto");
        allEntities.add("Belen");
        allEntities.add("Cesar");
        allEntities.add("Daniel");
        allEntities.add("Esteban");
        allEntities.add("Fernando");
        allEntities.add("Gaston");
        Map<String,List<String>> diagramMap= new HashMap<>();
        diagramMap.put("Diagram",new ArrayList<>(allEntities));

        DiagramResolverDialog dialog = new DiagramResolverDialog(diagramMap,allEntities);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
