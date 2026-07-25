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

    public void recalcularAltura(){

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