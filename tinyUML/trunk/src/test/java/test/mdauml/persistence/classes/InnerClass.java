package test.mdauml.persistence.classes;

import java.util.ArrayList;
import java.util.List;


public class InnerClass {

    private List<TheInner> children = new ArrayList<>();

    public void addChild(String child){
        children.add(new TheInner(child));
    }

    public List<TheInner> getChildren() {
        return children;
    }

    public void setChildren(List<TheInner> children) {
        this.children = children;
    }


    public boolean equals(Object o){
        if(o instanceof  InnerClass){
            InnerClass i = (InnerClass) o ;
            if(i.children == null || children==null)
                return false;
            if(i.children.size()!=children.size())
                return false;
            int j=0;
            for(TheInner inner : children){
                if(!inner.something.equals(i.children.get(j++).something))
                    return false;
            }
            return true;
        }
        return false;
    }
    public class TheInner{

        private String something;

        public TheInner(String something){
            this.something=something;
        }

        public String getSomething() {
            return something;
        }

        public void setSomething(String something) {
            this.something = something;
        }

    }

}
