package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;


public class UmlActorXmlizable extends AbstractUmlModelElementXmlizable{
  
  public static final String CLASS_TAG ="UmlActor";
  public static final String DESCRIPTION_TAG = "description";

  public UmlActorXmlizable(UmlActor instance) {
    super(instance);
  }
  public UmlActorXmlizable(){
    super();
  }

  @Override
  public Element toXml(Element root) throws Exception {
    UmlActor castedInstance = (UmlActor)instance;
    Element element = XmlHelper.getNewElement(root, CLASS_TAG);
    XmlHelper.addAtribute(root, element, ObjectXmlizable.ID_ATTR, this.id.toString());
    element.appendChild(super.toXml(root));
    Element description = XmlHelper.addChildElement(root,element,DESCRIPTION_TAG);
    description.setTextContent(castedInstance.getDescription());
    return element;
  }

  @Override
  public Object fromXml(Element element) throws Exception {
    if(instance == null){
      String id = element.getAttribute(ID_ATTR);
      this.instance = UmlActor.getPrototype().clone();
      this.id = Long.valueOf(id);
      Registerer.register(this.id, instance);      
    }
    Element abstractUmlModelElement = XmlHelper.getChild(element, AbstractUmlModelElementXmlizable.CLASS_TAG);
    super.fromXml(abstractUmlModelElement);
    Element description = XmlHelper.getChild(element, DESCRIPTION_TAG);
    ((UmlActor)instance).setDescription(description.getNodeValue());  
    return instance;
    
  }

}
