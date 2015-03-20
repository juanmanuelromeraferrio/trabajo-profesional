package org.tinyuml.umldraw.usecase;

import java.awt.Color;

import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.Compartment;
import org.tinyuml.draw.DrawingContext;
import org.tinyuml.draw.SimpleLabel;
import org.tinyuml.draw.DrawingContext.FontType;
import org.tinyuml.draw.EllipseCompartment;
import org.tinyuml.draw.Label;
import org.tinyuml.draw.LabelSource;
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

  private UmlUseCase useCase;
  private Compartment compartment;
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
    compartment = new EllipseCompartment();
    compartment.setParent(this);
    compartment.setBackground(BACKGROUND);
    label = new SimpleLabel();
    label.setParent(this);
    label.setFontType(FontType.ELEMENT_NAME);
    label.setSource(this);
    compartment.addLabel(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object clone() {
    UseCaseElement cloned = (UseCaseElement) super.clone();
    cloned.label = (Label) label.clone();
    cloned.label.setSource(cloned);
    cloned.compartment = (Compartment) compartment.clone();
    cloned.compartment.setParent(cloned);
    cloned.compartment.removeAllLabels();
    cloned.compartment.addLabel(cloned.label);

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

    compartment.draw(drawingContext);
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
    Label labelAt = compartment.getLabelAt(mx, my);
    if (labelAt == null)
      labelAt = compartment.getLabelAt(mx, my);
    return labelAt;
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
