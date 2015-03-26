package org.tinyuml.umldraw.usecase;

import java.awt.Color;
import java.awt.geom.Dimension2D;

import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.Compartment;
import org.tinyuml.draw.DrawingContext;
import org.tinyuml.draw.DrawingContext.FontType;
import org.tinyuml.draw.EllipseCompartment;
import org.tinyuml.draw.Label;
import org.tinyuml.draw.LabelSource;
import org.tinyuml.draw.SimpleLabel;
import org.tinyuml.model.RelationEndType;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlModelElement;
import org.tinyuml.model.UmlModelElementListener;
import org.tinyuml.model.UmlUseCase;
import org.tinyuml.umldraw.shared.UmlNode;

/**
 * This class represents a UseCase element in the editor. It is responsible for rendering the
 * information in the editor.
 *
 * @author Juan Manuel Romera
 * @version 1.0
 */
public final class UseCaseElement extends AbstractCompositeNode implements LabelSource, UmlNode,
    UmlModelElementListener {

  private static final long serialVersionUID = 8767029215902619069L;

  private static final Color BACKGROUND = Color.WHITE;
  private static final double DEFAULT_HEIGHT = 40;
  private static final double DEFAULT_WIDHT = 70;

  private UmlUseCase useCase;
  private Label label;
  private Compartment mainCompartment;

  private static UseCaseElement prototype;

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
   * {@inheritDoc}
   */
  @Override
  public Object clone() {
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
   * Sets the model element.
   * 
   * @param UseCase the model element
   */
  public void setModelElement(UmlUseCase UseCase) {
    this.useCase = UseCase;
  }

  /**
   * {@inheritDoc}
   */
  public UmlModelElement getModelElement() {
    return useCase;
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
  @Override
  public void recalculateSize(DrawingContext drawingContext) {
    mainCompartment.recalculateSize(drawingContext);
    notifyNodeResized();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void draw(DrawingContext drawingContext) {
    if (!isValid()) {
      recalculateSize(drawingContext);
    }
    mainCompartment.draw(drawingContext);

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Dimension2D getMinimumSize() {
    Dimension2D minMainSize = mainCompartment.getMinimumSize();
    return minMainSize;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dimension2D getSize() {
    Dimension2D mainSize = mainCompartment.getSize();
    return mainSize;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumSize(double width, double height) {
    throw new UnsupportedOperationException("setMinimumSize() not supported");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    return mainCompartment.isValid();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invalidate() {
    mainCompartment.invalidate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(double width, double height) {
    mainCompartment.setSize(width, height);
    invalidate();
  }

  @Override
  public Label getLabelAt(double mx, double my) {
    if (inInnerArea(mx, my))
      return label;
    return null;
  }

  private boolean inInnerArea(double mx, double my) {
    return mx >= (getAbsoluteX1()) && mx <= (getAbsoluteX2()) && my >= (getAbsoluteY1())
        && my <= (getAbsoluteY2());
  }

  @Override
  public void elementChanged(UmlModelElement element) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean acceptsConnection(RelationType associationType, RelationEndType as, UmlNode with) {
    return true;
  }
}
