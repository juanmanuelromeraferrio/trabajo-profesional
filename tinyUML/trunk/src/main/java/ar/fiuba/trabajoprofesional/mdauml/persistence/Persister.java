package ar.fiuba.trabajoprofesional.mdauml.persistence;

public interface Persister {
  
  public void save(String path) throws Exception;
  public void load(String path) throws Exception;
  

}
