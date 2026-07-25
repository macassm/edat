package Jerarquicas;

public class NodoGen {
    

    private Object elem;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;


    public NodoGen (Object elem, NodoGen hijoIzquierdo, NodoGen hermanoDerecho){
        this.elem = elem;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hermanoDerecho = hermanoDerecho;        
    }

    public Object getElem(){
        return this.elem;
    }

    public NodoGen getHijoIzquierdo(){
        return this.hijoIzquierdo;
    }

    public NodoGen getHermanoDerecho(){
        return this.hermanoDerecho;
    }

    public void setElem(Object elemNuevo){
        this.elem = elemNuevo;
    }

    public void setHijoIzquierdo(NodoGen hijoNuevo){
        this.hijoIzquierdo = hijoNuevo;
    }

    public void setHermanoDerecho(NodoGen hermanoNuevo){
        this.hermanoDerecho = hermanoNuevo;
    }

}
