package ar.fiuba.trabajoprofesional.mdauml.model;


public interface PackageListener {


    void removeFromPackage(UmlPackage umlPackage,PackageableUmlModelElement packageableUmlModelElement);
    void addToPackage(UmlPackage umlPackage,PackageableUmlModelElement packageableUmlModelElement);

}
