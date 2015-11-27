package ar.fiuba.trabajoprofesional.mdauml.conversion;


import ar.fiuba.trabajoprofesional.mdauml.exception.ValidateException;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;

public interface Validator {

    void validate(UmlModel model) throws ValidateException;
}
