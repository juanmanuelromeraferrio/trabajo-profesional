package org.tinyuml.umldraw.usecase;

import java.awt.Color;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.DoubleDimension;
import org.tinyuml.draw.DrawingContext;
import org.tinyuml.draw.DrawingContext.FontType;
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
    setSize(DEFAULT_WIDHT, DEFAULT_HEIGHT);
    label = new SimpleLabel();
    label.setParent(this);
    label.setFontType(FontType.ELEMENT_NAME);
    label.setSource(this);
    this.addChild(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object clone() {
    UseCaseElement cloned = (UseCaseElement) super.clone();

    if (!cloned.getChildren().isEmpty()) {
      cloned.label = (Label) cloned.getChildren().get(0);
      cloned.label.setSource(cloned);
    }

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
  public void draw(DrawingContext drawingContext) {
    if (!isValid()) {
      label.setFontType(getMainLabelFontType());
      recalculateSize(drawingContext);
    }

//    Point2D origin = new Point2D.Double(getAbsoluteX1(), getAbsoluteY1());
//    Dimension2D dimension = new DoubleDimension(getSize().getWidth(), getSize().getHeight());
//    drawingContext.drawEllipse(origin, dimension, Color.WHITE);
    label.centerHorizontally();
    label.draw(drawingContext);

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    return label.isValid();
  }

  /**
   * Returns the main label font type. Dependent whether the class is abstract or not.
   * 
   * @return the FontType for the main label
   */
  private FontType getMainLabelFontType() {
    return FontType.ELEMENT_NAME;
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
    // TODO Auto-generated method stub
    return false;
  }
}
