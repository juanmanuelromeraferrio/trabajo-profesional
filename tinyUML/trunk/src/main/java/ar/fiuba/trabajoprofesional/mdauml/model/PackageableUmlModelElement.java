package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.ArrayList;
import java.util.List;

public abstract class PackageableUmlModelElement extends AbstractUmlModelElement {


    private NestRelation packageRelation;
    private List<PackageListener> listeners  = new ArrayList<>();
    private boolean isPackaged(){
        return packageRelation!=null;
    }

    public void setPackageRelation(NestRelation nestRelation){
        unpack();
        packageRelation=nestRelation;
        notifyPackaged();

    }
    public UmlPackage getPackage(){
        if(!isPackaged())
            return null;
        return (UmlPackage) packageRelation.getNesting();
    }

    public void unpack(){
        if(isPackaged()){
            UmlPackage oldPkg = (UmlPackage) packageRelation.getNesting();
            packageRelation = null;
            notifyUnpackaged(oldPkg);
        }
    }

    private void notifyPackaged() {
        for(PackageListener l : listeners){
            l.addToPackage((UmlPackage) packageRelation.getNesting(),this);
        }

    }

    private void notifyUnpackaged(UmlPackage pkg){
        for(PackageListener l : listeners){
            l.removeFromPackage(pkg,this);
        }
    }
}
