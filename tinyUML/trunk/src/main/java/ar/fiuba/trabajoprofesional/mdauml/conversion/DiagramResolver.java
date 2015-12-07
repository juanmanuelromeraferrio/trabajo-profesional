package ar.fiuba.trabajoprofesional.mdauml.conversion;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DiagramResolver {

    Map<String,List<String>> resolveEntitiesByDiagram(Map<String, List<UmlUseCase>> mainEntityMap , List<UmlPackage> packages);
}
