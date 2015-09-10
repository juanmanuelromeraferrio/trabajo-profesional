/**
 * Copyright 2007 Wei-ju Wu
 * <p/>
 * This file is part of TinyUML.
 * <p/>
 * TinyUML is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p/>
 * TinyUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with TinyUML; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */
package ar.fiuba.trabajoprofesional.mdauml.ui.model;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.model.NameChangeListener;
import ar.fiuba.trabajoprofesional.mdauml.model.NamedElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelListener;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlDiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.StructureDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseDiagram;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

/**
 * This class implements a TreeModel to display the diagrams.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class DiagramTreeModel extends DefaultTreeModel
    implements UmlModelListener, NameChangeListener {

    /**
     *
     */
    private static final long serialVersionUID = -3100105764720456764L;

    private UmlModel model;
    private DefaultMutableTreeNode structureFolder, useCaseFolder, modelFolder;

    /**
     * Constructor.
     */
    public DiagramTreeModel() {
        super(new DefaultMutableTreeNode("Root node"));
        structureFolder =
            new DefaultMutableTreeNode(getResourceString("stdcaption.structurediagrams"));
        useCaseFolder = new DefaultMutableTreeNode(getResourceString("stdcaption.usecasediagrams"));
        modelFolder = new DefaultMutableTreeNode(getResourceString("stdcaption.modelfolder"));

        insertNodeInto(useCaseFolder, (DefaultMutableTreeNode) getRoot(), 0);
        insertNodeInto(structureFolder, (DefaultMutableTreeNode) getRoot(), 1);
        insertNodeInto(modelFolder, (DefaultMutableTreeNode) getRoot(), 2);
    }

    /**
     * Returns a string from the resource bundle.
     *
     * @param property the property
     * @return the value from the resource bundle
     */
    private String getResourceString(String property) {
        return ApplicationResources.getInstance().getString(property);
    }

    /**
     * Sets the UmlModel.
     *
     * @param aModel the UmlModel
     */
    public void setModel(UmlModel aModel) {
        cleanupOldStructure();
        buildNewStructure(aModel);
    }

    /**
     * Removes the old structures.
     */
    private void cleanupOldStructure() {
        if (model != null) {
            model.removeModelListener(this);
            for (UmlDiagram diagram : model.getDiagrams()) {
                ((GeneralDiagram) diagram).removeNameChangeListener(this);
            }
        }
        structureFolder.removeAllChildren();
        useCaseFolder.removeAllChildren();
        nodeStructureChanged(structureFolder);
        nodeStructureChanged(useCaseFolder);
    }

    /**
     * Build the new tree structure.
     *
     * @param aModel the model
     */
    private void buildNewStructure(UmlModel aModel) {
        model = aModel;
        aModel.addModelListener(this);
        for (UmlDiagram diagram : model.getDiagrams()) {
            insertToFolder(diagram);
            addNameChangeListener((GeneralDiagram) diagram);
        }
        nodeStructureChanged(structureFolder);
        nodeStructureChanged(useCaseFolder);
        reload();
    }

    @Override public void elementAdded(UmlModelElement element, UmlDiagram diagram) {
        insertToFolder(element, diagram);
        insertToModelFolder(element);
        addNameChangeListener((NamedElement) element);

    }

    private void insertToModelFolder(UmlModelElement element) {
        if (!folderContainsElement(modelFolder, element)) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(element);
            insertNodeInto(child, modelFolder, modelFolder.getChildCount());
        }
    }

    private void addNameChangeListener(NamedElement element) {
        element.addNameChangeListener(this);
    }

    private void insertToFolder(UmlModelElement element, UmlDiagram diagram) {

        DefaultMutableTreeNode diagramNode = null;
        if (diagram instanceof StructureDiagram) {
            diagramNode = getDiagramNode(structureFolder, diagram);
        } else if (diagram instanceof UseCaseDiagram) {
            diagramNode = getDiagramNode(useCaseFolder, diagram);
        }
        if (!folderContainsElement(diagramNode, element)) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(element);
            insertNodeInto(child, diagramNode, diagramNode.getChildCount());
        }

    }

    private boolean folderContainsElement(DefaultMutableTreeNode folder, UmlModelElement element) {
        Enumeration e = folder.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.getUserObject().equals(element)) {
                return true;
            }
        }
        return false;
    }

    private DefaultMutableTreeNode getDiagramNode(DefaultMutableTreeNode folder,
        UmlDiagram diagram) {
        for (int i = 0; i < folder.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) folder.getChildAt(i);
            if (node.getUserObject() == diagram) {
                return node;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void diagramAdded(UmlDiagram diagram) {
        insertToFolder(diagram);
        addNameChangeListener((GeneralDiagram) diagram);
    }

    /**
     * Inserts the specified diagram to the correct folder.
     *
     * @param diagram the diagram
     */
    private void insertToFolder(UmlDiagram diagram) {
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(diagram);
        if (diagram instanceof StructureDiagram) {
            insertNodeInto(child, structureFolder, structureFolder.getChildCount());
        } else if (diagram instanceof UseCaseDiagram) {
            insertNodeInto(child, useCaseFolder, useCaseFolder.getChildCount());
        }
        for(UmlDiagramElement diagElement : diagram.getElements()){
            insertToFolder(diagElement.getModelElement(), diagram);
            insertToModelFolder(diagElement.getModelElement());
        }


    }

    /**
     * Adds a name change listener to the specified diagram.
     *
     * @param diagram the diagram
     */
    private void addNameChangeListener(GeneralDiagram diagram) {
        diagram.addNameChangeListener(this);
    }



    @Override public void elementRemoved(UmlModelElement element, UmlDiagram diagram) {

        if (diagram == null) {
            removeFromFolder(modelFolder, element);
            return;
        }

        DefaultMutableTreeNode diagramNode = null;
        if (diagram instanceof StructureDiagram) {
            diagramNode = getDiagramNode(structureFolder, diagram);
        } else if (diagram instanceof UseCaseDiagram) {
            diagramNode = getDiagramNode(useCaseFolder, diagram);
        }

        removeFromDiagram(diagramNode, element);


    }

    /**
     * {@inheritDoc}
     */
    public void diagramRemoved(UmlDiagram diagram) {
        removeFromFolder(structureFolder, diagram);
        removeFromFolder(useCaseFolder, diagram);
    }

    /**
     * Removes the specified element from the diagram if it is found.
     *
     * @param diagram the diagram
     * @param element the element
     */
    private void removeFromDiagram(DefaultMutableTreeNode diagram, UmlModelElement element) {
        for (int i = 0; i < diagram.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) diagram.getChildAt(i);
            if (node.getUserObject() == element) {
                removeNodeFromParent(node);
                break;
            }
        }
    }

    /**
     * Removes the specified diagram from the folder if it is found.
     *
     * @param folder  the folder
     * @param diagram the diagram
     */
    private void removeFromFolder(DefaultMutableTreeNode folder, UmlDiagram diagram) {
        for (int i = 0; i < folder.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) folder.getChildAt(i);
            if (node.getUserObject() == diagram) {
                removeNodeFromParent(node);
                break;
            }
        }
    }

    /**
     * Removes the specified element from the folder if it is found.
     *
     * @param folder  the folder
     * @param element the element
     */
    private void removeFromFolder(DefaultMutableTreeNode folder, UmlModelElement element) {
        for (int i = 0; i < folder.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) folder.getChildAt(i);
            if (node.getUserObject() == element) {
                removeNodeFromParent(node);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void nameChanged(NamedElement element) {
        searchNodeInFolder(structureFolder, element);
        searchNodeInFolder(useCaseFolder, element);
        searchNodeInFolder(modelFolder, element);
    }

    private void searchNodeInFolder(DefaultMutableTreeNode folder, NamedElement element) {
        for (int i = 0; i < folder.getChildCount(); i++) {
            DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) folder.getChildAt(i);
            for (int j = 0; j < treenode.getChildCount(); j++) {
                DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) treenode.getChildAt(j);
                if (leaf.getUserObject() == element) {
                    nodeChanged(leaf);
                }
            }
            if (treenode.getUserObject() == element) {
                nodeChanged(treenode);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public boolean isLeaf(Object object) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
        if (!(node.getUserObject() instanceof UmlModelElement || node
            .getUserObject() instanceof UmlDiagram)) {
            return false;
        }

        return super.isLeaf(node);
    }

    public TreePath getModelPath() {
        return new TreePath(this.modelFolder.getPath());
    }
}
