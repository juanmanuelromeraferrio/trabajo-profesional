package ar.fiuba.trabajoprofesional.mdauml.conversion;


import ar.fiuba.trabajoprofesional.mdauml.conversion.model.IConversionModel;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public interface ClassDiagramBuilder {

    void buildClassDiagram(Project project, String diagramName, IConversionModel conversionModel);
}
