package Jerarquicas;

public class NodoArbol {
private Object elem;
private NodoArbol izquierdo,derecho;


public NodoArbol(){
    this.elem = null;
    this.izquierdo = null;
    this.derecho = null;
}

public NodoArbol(Object elementoNuevo, NodoArbol unIzq, NodoArbol unDer){
    this.elem = elementoNuevo;
    this.izquierdo = unIzq;
    this.derecho = unDer;
}

public Object getElem(){
    return elem;
}

public NodoArbol getIzquierdo(){
    return this.izquierdo;
}

public NodoArbol getDerecho(){
    return this.derecho;
}

public void setElem(Object elemento){
    this.elem = elemento;
}

public void setIzquierdo(NodoArbol unIzq){
    this.izquierdo = unIzq;
}

public void setDerecho(NodoArbol unDer){
    this.derecho = unDer;
}




}
