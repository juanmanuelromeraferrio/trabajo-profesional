package ar.fiuba.trabajoprofesional.mdauml.umldraw.shared;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.draw.Label;

public class ConnectionNameLabel extends AbstractCompositeNode implements Label, LabelSource {

    private Label label;
    private BaseConnection connection;

    /**
     * Constructor.
     */
    public ConnectionNameLabel() {
        setLabel(new SimpleLabel());
    }

    /**
     * Returns the wrapped label.
     *
     * @return the wrapped label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets a Label. This method is exposed for unit testing.
     *
     * @param aLabel the label
     */
    public void setLabel(Label aLabel) {
        label = aLabel;
        label.setSource(this);
        label.setParent(this);
    }

    /**
     * Sets the connection.
     *
     * @param conn the Connection
     */
    public void setConnection(BaseConnection conn) {
        connection = conn;
    }

    /**
     * {@inheritDoc}
     */
    public Label getLabelAt(double mx, double my) {
        if (contains(mx, my))
            return this;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public LabelSource getSource() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void setSource(LabelSource aSource) {
    }

    /**
     * {@inheritDoc}
     */
    public String getText() {
        return label.getText();
    }

    /**
     * {@inheritDoc}
     */
    public void setText(String text) {
        label.setText(text);
    }

    /**
     * {@inheritDoc}
     */
    public void setFontType(DrawingContext.FontType aFontType) {
        label.setFontType(aFontType);
    }

    /**
     * {@inheritDoc}
     */
    public void centerHorizontally() {
        label.centerHorizontally();
    }


    /**
     * {@inheritDoc}
     */
    public void centerVertically() {
        label.centerVertically();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(DrawingContext drawingContext) {
        if (getLabelText() != null) {
            label.draw(drawingContext);

        }
    }


    /**
     * {@inheritDoc}
     */
    public String getLabelText() {
        if (connection.getModelElement() == null)
            return "";
        return connection.getModelElement().getName();
    }

    /**
     * {@inheritDoc}
     */
    public void setLabelText(String aText) {
        if (connection.getModelElement() != null) {
            connection.getModelElement().setName(aText);
        }
    }
}