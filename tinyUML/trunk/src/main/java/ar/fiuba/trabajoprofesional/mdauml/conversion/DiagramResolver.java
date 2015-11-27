package ar.fiuba.trabajoprofesional.mdauml.conversion;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DiagramResolver {

    Map<String,List<String>> resolveEntitiesByDiagram(Set<String> mainEntities);
}
