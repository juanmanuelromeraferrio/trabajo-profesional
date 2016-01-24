package ar.fiuba.trabajoprofesional.mdauml.conversion;


import ar.fiuba.trabajoprofesional.mdauml.conversion.model.ConversionModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlClass;
import java.util.List;
import java.util.Map;

public interface ClassDiagramBuilder {

    void buildClassDiagram(Map<Class<? extends UmlClass>,List<UmlClass>> classModel, String diagramName, ConversionModel conversionModel);
}
