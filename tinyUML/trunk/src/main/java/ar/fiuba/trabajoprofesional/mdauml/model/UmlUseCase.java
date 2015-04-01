package ar.fiuba.trabajoprofesional.mdauml.model;

/**
 * This class represents an UML UseCase
 * 
 * @author Juan Manuel Romera
 *
 */
public class UmlUseCase extends AbstractUmlModelElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8599134739003834715L;
	private static UmlUseCase prototype;

	/**
	 * Returns the prototype instance.
	 * 
	 * @return the prototype instance
	 */
	public static UmlUseCase getPrototype() {
		if (prototype == null)
			prototype = new UmlUseCase();
		return prototype;
	}

	/**
	 * Constructor.
	 */
	private UmlUseCase() {
	}

}
