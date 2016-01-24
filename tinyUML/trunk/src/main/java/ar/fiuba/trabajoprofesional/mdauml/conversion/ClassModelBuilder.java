package ar.fiuba.trabajoprofesional.mdauml.conversion;

import ar.fiuba.trabajoprofesional.mdauml.conversion.model.*;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlClass;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;

import java.util.List;
import java.util.Map;

public interface ClassModelBuilder {

    ConversionModel buildConversionModel(Map<String,List<UmlUseCase>> mainEntityMap);

    Map<Class<? extends UmlClass>, List<UmlClass>> buildUmlModel(ConversionModel model);

}
