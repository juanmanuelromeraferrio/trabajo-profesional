package ar.fiuba.trabajoprofesional.mdauml.conversion.dialog;

import ar.fiuba.trabajoprofesional.mdauml.conversion.model.DiagramBuilder;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
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
    private JCheckBox mapToPkg;
    private JComboBox packagesBox;
    private Map<String,DiagramBuilder> diagramMap;
    private List<String> mainEntities;
    private List<UmlPackage> packages;

    public DiagramResolverDialog(Map<String,DiagramBuilder> diagramMap, List<String> mainEntities, List<UmlPackage> packages) {
        setContentPane(contentPane);
        setModal(true);
        this.diagramMap = diagramMap;
        this.mainEntities = mainEntities;
        this.packages = packages;
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
        List<String> selectedDiagramEntities = diagramMap.get(diagrams.get(0)).getMainEntities();
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
        mapToPkg.setText(Msg.get("conversion.dialog.resolver.pkgcheckbox"));

        boolean pkgRelated = diagramMap.get((String)comboBox.getSelectedItem()).isPackageRelated();

        packagesBox.setEnabled(pkgRelated);
        if(diagramMap.get(comboBoxModel.getSelectedItem()).getUmlPackage()!=null) {
            DefaultComboBoxModel<UmlPackage> packageBoxModel = new DefaultComboBoxModel<>();
            packageBoxModel.addElement(diagramMap.get(comboBoxModel.getSelectedItem()).getUmlPackage());
            packagesBox.setModel(packageBoxModel);
            packagesBox.setSelectedItem(diagramMap.get(comboBoxModel.getSelectedItem()).getUmlPackage());
        }
        mapToPkg.setSelected(pkgRelated);
        if(packages.isEmpty())
            mapToPkg.setEnabled(false);
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
        pack();
        setResizable(false);
        mapToPkg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCheckbox();
            }


        });
        packagesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPackageSelection();
            }
        });
    }

    private void onPackageSelection() {
        UmlPackage selectedPackage = (UmlPackage) packagesBox.getSelectedItem();
        String diagram = (String) comboBox.getSelectedItem();
        diagramMap.get(diagram).setUmlPackage(selectedPackage);
    }

    private void onCheckbox() {
        boolean checked = mapToPkg.isSelected();
        packagesBox.setEnabled(checked);
        if(diagramMap.get(comboBox.getSelectedItem()).getUmlPackage()!=null)
            packagesBox.setSelectedItem(diagramMap.get(comboBox.getSelectedItem()).getUmlPackage());
        String diagram = (String) comboBox.getSelectedItem();
        diagramMap.get(diagram).setPackageRelated(checked);
        if(checked)
            diagramMap.get(diagram).setUmlPackage((UmlPackage) packagesBox.getSelectedItem());
        else
            diagramMap.get(diagram).setUmlPackage(null);
    }

    private void onRename() {
        String diagram = (String) comboBox.getSelectedItem();
        DiagramBuilder builder = diagramMap.get(diagram);

        String renameDiagram = newDiagramField.getText().trim();
        if(renameDiagram.isEmpty())
            return;
        Set<String> diagrams = diagramMap.keySet();
        if(diagrams.contains(renameDiagram))
            return;
        diagramMap.remove(diagram);
        builder.setName(renameDiagram);
        diagramMap.put(renameDiagram,builder);
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
        for(String entity : diagramMap.get(diagram).getMainEntities())
            ((DefaultListModel)diagramEntities.getModel()).addElement(entity);

        mapToPkg.setSelected(diagramMap.get(diagram).isPackageRelated());
        packagesBox.setEnabled(diagramMap.get(diagram).isPackageRelated());
        List<UmlPackage> unselectedPackages = new ArrayList<>(packages);
        for(DiagramBuilder builder :diagramMap.values()){
            if(builder.getUmlPackage()!=null && !builder.getName().equals(diagram))
                unselectedPackages.remove(builder.getUmlPackage());
        }
        DefaultComboBoxModel<UmlPackage> packageBoxModel = new DefaultComboBoxModel<>();
        for(UmlPackage aPackage: unselectedPackages)
            packageBoxModel.addElement(aPackage);
        packagesBox.setModel(packageBoxModel);
        if(unselectedPackages.isEmpty()) {
            mapToPkg.setEnabled(false);
            packagesBox.setEnabled(false);
        }else{
            mapToPkg.setEnabled(true);
            packagesBox.setEnabled(mapToPkg.isSelected());
        }



        if(diagramMap.get(diagram).getUmlPackage()!=null)
            packagesBox.setSelectedItem(diagramMap.get(diagram).getUmlPackage());

    }

    private void onRemove() {
        List<String> selected = diagramEntities.getSelectedValuesList();
        if(selected.isEmpty())
            return;
        for(String entity : selected){
            ((DefaultListModel)diagramEntities.getModel()).removeElement(entity);
        }
        String diagram = (String) comboBox.getSelectedItem();
        diagramMap.get(diagram).getMainEntities().removeAll(selected);
    }

    private void onAdd() {

        List<String> newDiagramEntities = allEntities.getSelectedValuesList();
        if(newDiagramEntities.isEmpty())
            return;
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
        diagramMap.get(diagram).setMainEntities(newDiagramEntities);

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
        diagramMap.put(newDiagramField.getText(),new DiagramBuilder());
        newDiagramField.setText("");
        comboBox.setSelectedItem(diagram);
        mapToPkg.setSelected(diagramMap.get(newDiagramField.getText()).isPackageRelated());
        packagesBox.setEnabled(diagramMap.get(newDiagramField.getText()).isPackageRelated());

        if(diagramMap.get(comboBox.getSelectedItem()).getUmlPackage()!=null)
            packagesBox.setSelectedItem(diagramMap.get(comboBox.getSelectedItem()).getUmlPackage());
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

}
