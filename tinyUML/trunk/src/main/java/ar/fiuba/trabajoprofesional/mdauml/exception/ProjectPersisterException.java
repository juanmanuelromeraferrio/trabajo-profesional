package ar.fiuba.trabajoprofesional.mdauml.exception;

public class ProjectPersisterException  extends Exception{
    public ProjectPersisterException(String msg){
      super(msg);
    }
    public ProjectPersisterException(String msg,Throwable e){
      super(msg,e);
    }
}
