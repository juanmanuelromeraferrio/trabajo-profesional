package ar.fiuba.trabajoprofesional.mdauml.conversion;

import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionException;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface Converter {

    void convert(Project project) throws ConversionException;

}
