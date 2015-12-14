package ar.fiuba.trabajoprofesional.mdauml.conversion.model;

import ar.fiuba.trabajoprofesional.mdauml.draw.Diagram;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;
import ar.fiuba.trabajoprofesional.mdauml.util.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiagramBuilder {

    private String name;
    private boolean packageRelated=false;
    private UmlPackage umlPackage;
    private List<String> mainEntities=new ArrayList<>();

    public ConversionDiagram build(Map<String,List<UmlUseCase>> mainEntityMap){
        ConversionDiagram conversionDiagram = new ConversionDiagram();
        conversionDiagram.setName(name);

        for(String mainEntity: mainEntities){
            Control control = conversionDiagram.getControl(StringHelper.toUpperCamelCase(mainEntity) + Msg.get("conversion.names.control"));
            for(UmlUseCase useCase : mainEntityMap.get(mainEntity)){
                if(isPackageRelated() && (useCase.getPackage()==null || !useCase.getPackage().equals(umlPackage)))
                    continue;
                control.addMethod("+"+StringHelper.toLowerCamelCase(useCase.getName())+"()");
                for(String entityName : useCase.getAllEntities()){
                    Entity entity = conversionDiagram.getEntity(StringHelper.toUpperCamelCase(entityName));
                    conversionDiagram.addRelation(new SimpleRelation(control,entity));
                }
                for(UmlActor mainActor : useCase.getMainActors()){
                    Boundary boundary = conversionDiagram.getBoundary(StringHelper.toUpperCamelCase(mainActor.getName()) + Msg.get("conversion.names.boundary"));
                    boundary.addMethod(StringHelper.toLowerCamelCase(useCase.getName()));
                    conversionDiagram.addRelation(new SimpleRelation(boundary,control));
                }
            }
        }
        return conversionDiagram;
    }

    public UmlPackage getUmlPackage() {
        return umlPackage;
    }

    public void setUmlPackage(UmlPackage umlPackage) {
        this.umlPackage = umlPackage;
    }

    public boolean isPackageRelated() {
        return packageRelated;
    }

    public void setPackageRelated(boolean packageRelated) {
        this.packageRelated = packageRelated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMainEntities() {
        return mainEntities;
    }

    public void setMainEntities(List<String> mainEntities) {
        this.mainEntities = mainEntities;
    }
}
