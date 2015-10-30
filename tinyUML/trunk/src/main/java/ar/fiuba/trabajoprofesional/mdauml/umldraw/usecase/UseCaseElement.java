package ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.draw.DrawingContext.FontType;
import ar.fiuba.trabajoprofesional.mdauml.draw.Label;
import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.*;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

import java.awt.*;
import java.awt.geom.Dimension2D;

/**
 * This class represents a UseCase element in the editor. It is responsible for rendering the
 * information in the editor.
 *
 * @author Juan Manuel Romera
 * @version 1.0
 */
public final class UseCaseElement extends AbstractCompositeNode
    implements LabelSource, UmlModelElementListener,PackageListener,UmlNode {

    private static final Color BACKGROUND = Color.WHITE;
    private static final double DEFAULT_HEIGHT = 40;
    private static final double DEFAULT_WIDHT = 70;
    private static UseCaseElement prototype;
    private UmlUseCase useCase;
    private Label label;
    private Compartment mainCompartment;

    /**
     * Private constructor.
     */
    private UseCaseElement() {
        mainCompartment = new EllipseCompartment();
        mainCompartment.setParent(this);
        mainCompartment.setBackground(BACKGROUND);
        label = new SimpleLabel();
        label.setParent(this);
        label.setSource(this);
        label.setFontType(FontType.ELEMENT_NAME);
        mainCompartment.addLabel(label);

        setSize(DEFAULT_WIDHT, DEFAULT_HEIGHT);
        mainCompartment.setSize(getSize().getWidth(), getSize().getHeight());

    }

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static UseCaseElement getPrototype() {
        if (prototype == null)
            prototype = new UseCaseElement();
        return prototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override public Object clone() {
        UseCaseElement cloned = (UseCaseElement) super.clone();
        cloned.label = (Label) label.clone();
        cloned.label.setSource(cloned);
        cloned.label.setParent(cloned);

        cloned.mainCompartment = (Compartment) mainCompartment.clone();
        cloned.mainCompartment.setParent(cloned);
        cloned.mainCompartment.removeAllLabels();
        cloned.mainCompartment.addLabel(cloned.label);


        if (useCase != null) {
            cloned.useCase = (UmlUseCase) useCase.clone();
        }

        return cloned;

    }

    /**
     * Returns the main label for testing purposes.
     *
     * @return the main label
     */
    public Label getMainLabel() {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    public UmlModelElement getModelElement() {
        return useCase;
    }

    @Override
    public void setModelElement(UmlModelElement model) {
        if(model instanceof UmlUseCase)
            this.useCase = (UmlUseCase) model;
        else throw new IllegalArgumentException("UmlUseCase expected");
    }

    /**
     * {@inheritDoc}
     */
    public String getLabelText() {
        return getModelElement().getName();
    }

    /**
     * {@inheritDoc}
     */
    public void setLabelText(String aText) {
        getModelElement().setName(aText);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void recalculateSize(DrawingContext drawingContext) {
        mainCompartment.recalculateSize(drawingContext);
        notifyNodeResized();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {
        if (!isValid()) {
            recalculateSize(drawingContext);
        }
        mainCompartment.draw(drawingContext);

    }


    /**
     * {@inheritDoc}
     */
    @Override public Dimension2D getMinimumSize() {
        Dimension2D minMainSize = mainCompartment.getMinimumSize();
        return minMainSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override public Dimension2D getSize() {
        Dimension2D mainSize = mainCompartment.getSize();
        return mainSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setMinimumSize(double width, double height) {
        throw new UnsupportedOperationException("setMinimumSize() not supported");
    }


    /**
     * {@inheritDoc}
     */
    @Override public boolean isValid() {
        return mainCompartment.isValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void invalidate() {
        mainCompartment.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setSize(double width, double height) {
        mainCompartment.setSize(width, height);
        invalidate();
    }

    @Override public Label getLabelAt(double mx, double my) {
        if (inLabelArea(mx, my))
            return label;
        return null;
    }

    private boolean inLabelArea(double mx, double my) {
        double horizontalMargin = (getSize().getWidth() - label.getSize().getWidth()) / 2;
        double verticalMargin = (getSize().getHeight() - label.getSize().getHeight()) / 2;

        double labelX1 = getAbsoluteX1() + horizontalMargin;
        double labelX2 = getAbsoluteX2() - horizontalMargin;
        double labelY1 = getAbsoluteY1() + verticalMargin;
        double labelY2 = getAbsoluteY2() - verticalMargin;

        return mx >= labelX1 && mx <= labelX2 && my >= labelY1 && my <= labelY2;
    }


    @Override public void elementChanged(UmlModelElement element) {
        // TODO Auto-generated method stub

    }


    @Override
    public void addConcreteConnection(Nest nest) throws AddConnectionException {
        Relation relation = (Relation) nest.getModelElement();

        UmlModelElement element1 = relation.getElement1();

        if(element1 == useCase)
            throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.nest.invalid"));

        if(! (element1 instanceof UmlPackage ) )
            throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.usecase.nest.withoutPkg"));

        useCase.setPackageRelation((NestRelation) nest.getModelElement());

    }


    @Override
    public void addConcreteConnection(Association association) throws AddConnectionException {
        Relation relation = (Relation) association.getModelElement();

        UmlModelElement element1 = relation.getElement1();
        UmlModelElement element2 = relation.getElement2();

        if (element1 != this.useCase && element1 instanceof UmlActor) {
            this.useCase.addUmlActor((UmlActor) element1);

        } else if (element2 != this.useCase && element2 instanceof UmlActor) {
            this.useCase.addUmlActor((UmlActor) element2);

        }else
            throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.usecase.association.withoutactor"));

    }

    @Override
    public void addConcreteConnection(Extend extend) throws AddConnectionException {
        Relation relation = (Relation) extend.getModelElement();

        UmlModelElement element1 = relation.getElement1();
        UmlModelElement element2 = relation.getElement2();

        if(element1==this.useCase && element2 instanceof UmlUseCase) {
            if(element2 == this.useCase)
                throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.usecase.extend.autoreferencial"));
            this.useCase.addExtend((ExtendRelation) extend.getModelElement());
        }
        else if(element2!=this.useCase || !(element1 instanceof UmlUseCase))
            throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.usecase.extend.withoutUsecase"));

    }

    @Override
    public void addConcreteConnection(Include include) throws AddConnectionException {
        Relation relation = (Relation) include.getModelElement();

        UmlModelElement element1 = relation.getElement1();
        UmlModelElement element2 = relation.getElement2();

        if(element1==this.useCase && element2 instanceof UmlUseCase) {
            if(element2 == this.useCase)
                throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.usecase.include.autoreferencial"));
            this.useCase.addInclude((IncludeRelation) include.getModelElement());
        }
        else if(element2!=this.useCase || !(element1 instanceof UmlUseCase))
            throw new AddConnectionException(ApplicationResources.getInstance().getString("error.connection.usecase.include.withoutUsecase"));

    }

    @Override
    public void addConcreteConnection(NoteConnection connection) throws AddConnectionException {

    }

    @Override
    public boolean allowsConnection(Nest connection) {
        return true;
    }

    @Override
    public boolean allowsConnection(Include connection) {
        return true;
    }

    @Override
    public boolean allowsConnection(Association connection) {
        return true;
    }

    @Override
    public boolean allowsConnection(Extend connection) {
        return true;
    }

    @Override
    public boolean allowsConnection(NoteConnection connection) {
        return true;
    }


    @Override public void removeConcreteConnection(Include include) {

        Relation relation = (Relation) include.getModelElement();

        UmlModelElement element1 = relation.getElement1();
        if(this.useCase==element1)
            useCase.removeInclude((IncludeRelation) include.getModelElement());

    }

    @Override public void removeConcreteConnection(Extend extend) {

        Relation relation = (Relation) extend.getModelElement();

        UmlModelElement element1 = relation.getElement1();
        if(this.useCase==element1)
            useCase.removeExtend((ExtendRelation) extend.getModelElement());
    }


    @Override public void removeConcreteConnection(Association association) {

        Relation relation = (Relation) association.getModelElement();

        UmlModelElement element1 = relation.getElement1();
        UmlModelElement element2 = relation.getElement2();

        if (element1 != this.useCase && element1 instanceof UmlActor) {
            this.useCase.removeUmlActor((UmlActor) element1);
        } else if (element2 != this.useCase && element2 instanceof UmlActor) {
            this.useCase.removeUmlActor((UmlActor) element2);
        }

    }


    @Override
    public void addToPackage(UmlPackage umlPackage, PackageableUmlModelElement packageableUmlModelElement) {

    }

    @Override
    public void removeFromPackage(UmlPackage umlPackage, PackageableUmlModelElement packageableUmlModelElement) {
        if(this.getModelElement()==packageableUmlModelElement)
            removeExistingConnection(Nest.class);
    }
}
