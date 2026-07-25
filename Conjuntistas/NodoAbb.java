package Conjuntistas;

public class NodoAbb {
    private Comparable elem;
    private NodoAbb hijoIzquierdo;
    private NodoAbb hijoDerecho;
  

    public NodoAbb(Comparable unElem, NodoAbb unIzq, NodoAbb unDer){
        elem = unElem;
        hijoIzquierdo = unIzq;
        hijoDerecho = unDer;
    }

    public Comparable getElem(){
        return this.elem;
    }

    public NodoAbb getHijoIzquierdo(){
        return hijoIzquierdo;
    }

    public NodoAbb getHijoDerecho(){
        return hijoDerecho;
    }

    public void setElem(Comparable unElem){
        this.elem = unElem;
    }

    public void setHijoIzquierdo(NodoAbb unIzq){
        this.hijoIzquierdo = unIzq;
    }

    public void setHijoDerecho(NodoAbb unDer){
        this.hijoDerecho = unDer;
    }


}
