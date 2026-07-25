package Conjuntistas;
public class NodoAVL {

private Comparable elem;
private NodoAVL izquierdo,derecho;
private int altura;


    public NodoAVL(Comparable unElem, NodoAVL izq, NodoAVL der){
        this.elem = unElem;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0;
    }

public void recalcularAltura() {
        int altIzq = (this.izquierdo != null) ? this.izquierdo.getAltura() : -1;
        int altDer = (this.derecho != null) ? this.derecho.getAltura() : -1;
        this.altura = Math.max(altIzq, altDer) + 1;
    }

    public int getAltura(){
        return this.altura;
    }

    public Comparable getElem(){
        return this.elem;
    }

    public NodoAVL getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVL getDerecho(){
        return this.derecho;
    }

    public void setElem(Comparable elemento){
        this.elem = elemento;
    }

    public void setIzquierdo(NodoAVL unIzq){
        this.izquierdo = unIzq;
    }

    public void setDerecho(NodoAVL unDer){
        this.derecho = unDer;
    }

}