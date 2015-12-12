package ar.fiuba.trabajoprofesional.mdauml.conversion;


import ar.fiuba.trabajoprofesional.mdauml.conversion.model.IConversionDiagram;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface ClassDiagramBuilder {

    void buildClassDiagram(Project project, String diagramName, IConversionDiagram conversionModel);
}
