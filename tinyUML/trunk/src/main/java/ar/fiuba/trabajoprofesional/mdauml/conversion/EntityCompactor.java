package ar.fiuba.trabajoprofesional.mdauml.conversion;


import ar.fiuba.trabajoprofesional.mdauml.exception.CompactorException;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;

public interface EntityCompactor {

    UmlModel compact(final UmlModel model)throws CompactorException;
}
