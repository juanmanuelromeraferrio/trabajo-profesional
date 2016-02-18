package ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase;


import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.model.ExtendRelation;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.DiagramEditor;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.DeleteElementCommand;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.ArrowConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.ConnectionNameLabel;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Extend extends ArrowConnection {

    private static final String LABEL = "<<extend>>";
    private static Extend prototype;
    private ConnectionNameLabel nameLabel;
    private boolean show = false;
    private ExtentionPointNote extentionPointNote ;


    /**
     * Private constructor.
     */
    private Extend() {
        setConnection(new SimpleConnection());
        setIsDashed(true);
        setOpenHead(true);
        relation=new ExtendRelation();
        setupNameLabel();
        extentionPointNote = new ExtentionPointNote(this);
    }
    @Override
    public Extend clone(){
        Extend cloned= (Extend) super.clone();
        cloned.setupNameLabel();
        cloned.nameLabel.setParent(nameLabel.getParent());
        cloned.show = show;
        cloned.extentionPointNote= new ExtentionPointNote(cloned);
        if(show)
            cloned.extentionPointNote.initialize();
        return cloned;
    }

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static Extend getPrototype() {
        if (prototype == null)
            prototype = new Extend();
        return prototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setParent(CompositeNode parent) {
        super.setParent(parent);
        nameLabel.setParent(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {
        super.draw(drawingContext);
        positionNameLabel();
        nameLabel.draw(drawingContext);

    }

    private ExtentionPointNote buildExtentionPointNote() {
        return null;
    }


    /**
     * Sets the name label.
     */
    public void setupNameLabel() {
        nameLabel = new ConnectionNameLabel();
        nameLabel.setLabelText(LABEL);

    }

    /**
     * Sets the position for the name label.
     */
    private void positionNameLabel() {
        // medium segment
        java.util.List<Line2D> segments = getSegments();
        Line2D middlesegment = segments.get(segments.size() / 2);
        int x = (int) (middlesegment.getX2() + middlesegment.getX1()) / 2;
        int y = (int) (middlesegment.getY2() + middlesegment.getY1()) / 2;
        nameLabel.setAbsolutePos(x, y);
    }

    @Override public void acceptNode(ConnectionVisitor node) {
        node.addConcreteConnection(this);
    }
    @Override public void cancelNode(ConnectionVisitor node){
        node.removeConcreteConnection(this);
    }

    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        if(!this.show && show)
            extentionPointNote.initialize();
        if(this.show && !show)
            deleteExtentionPoint(getDiagram().getEditor());
        this.show = show;
    }

    public void deleteExtentionPoint(DiagramEditor editor){
        ArrayList<DiagramElement> elements = new ArrayList<>();
        elements.add(extentionPointNote);
        DeleteElementCommand command = new DeleteElementCommand(editor, elements);
        editor.execute(command);
    }


    public void refresh() {
        extentionPointNote.refresh();
    }
}