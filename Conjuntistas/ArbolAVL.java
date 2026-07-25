package Conjuntistas;

import Lineales.dinamicas.Lista;

public class ArbolAVL{
    private NodoAVL raiz;

    public ArbolAVL(){
        this.raiz = null;
    }

    public boolean insertar(Comparable unElem){
        boolean [] exito = {false};

        if(this.raiz == null){
            this.raiz = new NodoAVL(unElem, null, null);
            exito [0] = true;
        }else{
            this.raiz = insertarAux(raiz, unElem, exito);
        }
    
        return exito[0];
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable unElem, boolean [] exito){
    
        if(n != null){
            int comp = unElem.compareTo(n.getElem());
            if(comp != 0){
                if(comp < 0){
                    if(n.getIzquierdo() != null){
                        n.setIzquierdo(insertarAux(n.getIzquierdo(), unElem, exito));
                    }else{
                        n.setIzquierdo(new NodoAVL(unElem, null, null));
                        exito[0] = true;
                    }
                }else{
                    if(n.getDerecho() != null){
                        n.setDerecho(insertarAux(n.getDerecho(), unElem, exito));
                    }else{
                        n.setDerecho(new NodoAVL(unElem, null, null));
                        exito [0] = true;
                    }
                }
            }
        }
        n = balanceador(n,n,exito);
        return n;
    }

    private NodoAVL balanceador(NodoAVL n, NodoAVL balanceNodo, boolean [] exito){
        if(exito[0] && balanceNodo != null){
            balanceNodo.recalcularAltura();
            int balance = calcularBalance(balanceNodo);
            if(balance > 1 ){
                int balanceHijo = calcularBalance(balanceNodo.getIzquierdo());
                if(balanceHijo < 0){
                    balanceNodo.setIzquierdo(balancearDer(balanceNodo.getIzquierdo()));
                }
                balanceNodo = balancearIzq(balanceNodo);
            }
            if(balance < -1){
                int balanceHijo = calcularBalance(balanceNodo.getDerecho());
                if(balanceHijo > 0){
                    balanceNodo.setDerecho(balancearIzq(balanceNodo.getDerecho()));
                }
                balanceNodo = balancearDer(balanceNodo);
            }
        }
        return balanceNodo;
    }

    private NodoAVL balancearIzq(NodoAVL r) {
        // rotacion a der
        NodoAVL h;
        // rotacion simple
        h = r.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVL balancearDer(NodoAVL r) {
        // rotacion a izq
        NodoAVL h;
        // rotacion simple
        h = r.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private int calcularBalance(NodoAVL n) {
        int altDer = (n.getDerecho() != null) ? n.getDerecho().getAltura() : -1;
        int altIzq = (n.getIzquierdo() != null) ? n.getIzquierdo().getAltura() : -1;
        int balance = altIzq - altDer;
        return balance;
    }

    public boolean eliminar(Comparable elem) {
        boolean[] exito = {false};
        if(this.raiz != null){
            this.raiz = eliminarRec(this.raiz,elem,exito);
        }
        return exito[0];
    }

    private NodoAVL eliminarRec(NodoAVL n, Comparable elem, boolean [] exito){
        NodoAVL eliminado = n;
        if(n != null){
            int comparar = elem.compareTo(n.getElem());
            if(comparar == 0){
                if(n.getDerecho() == null && n.getIzquierdo() == null){
                    eliminado = null;
                }
                if(n.getIzquierdo() != null && n.getDerecho() == null){
                    eliminado = n.getIzquierdo();
                }
                if(n.getIzquierdo() == null && n.getDerecho() != null){
                    eliminado = n.getDerecho();
                }
                if(n.getIzquierdo() != null && n.getDerecho() != null){
                    Object mayor = buscarMayor(n.getIzquierdo());
                    n.setIzquierdo(eliminarRec(n.getIzquierdo(), (Comparable) mayor, exito));
                    n.setElem((Comparable)mayor);
                }
                exito[0] = true;
            }else{
                if(comparar < 0){
                    n.setIzquierdo(eliminarRec(n.getIzquierdo(), elem, exito));
                }else{
                    n.setDerecho(eliminarRec(n.getDerecho(), elem, exito));

                }
            }
        }
        eliminado = balanceador(n, eliminado, exito);
        return eliminado;
    }


    private Object buscarMayor(NodoAVL n) {
        Object mayor = null;
        if (n != null) {
            mayor = n.getElem();
            if (n.getDerecho() != null) {
                mayor = buscarMayor(n.getDerecho());
            }
        }

        return mayor;
    }


    public boolean pertenece(Object elem){
        return perteneceRecursivo(this.raiz, (Comparable) elem);
    }

    private boolean perteneceRecursivo(NodoAVL n, Comparable elem){
        boolean pertenece = false;
        int comparacion;

        if(n != null){
            comparacion = elem.compareTo(n.getElem());

            if(comparacion == 0){
                pertenece = true;
            }else{
                if(comparacion < 0){
                    pertenece = perteneceRecursivo(n.getIzquierdo(), elem);
                }else{
                    pertenece = perteneceRecursivo(n.getDerecho(), elem);
                }
            }
        }

        return pertenece;
    }

    public Object  minimoElem(){
        Object minimo = null;
        NodoAVL actual = this.raiz; 

        while(actual != null){
            minimo = actual.getElem();
            actual = actual.getIzquierdo();
        }

        return minimo;
    }

    public Object  maximoElem(){
        Object maximo = null;
        NodoAVL actual = this.raiz; 

        while(actual != null){
            maximo = actual.getElem();
            actual = actual.getDerecho();
        }

        return maximo;
    }

    public Lista listar(){
        Lista listado = new Lista();
        listarAux(this.raiz, listado);
        return listado;
    }

    private void listarAux(NodoAVL actual, Lista listado){

        if(actual != null){
            listarAux(actual.getIzquierdo(), listado);
            listado.insertar(actual.getElem(), listado.longitud()+1);
            listarAux(actual.getDerecho(), listado);
        }

    }

    public Lista listarRango(Object elemMinimo, Object elemMaximo) {
        Lista lista = new Lista();
        if (((Comparable) elemMinimo).compareTo(elemMaximo) <= 0) {
            listarRangoRecursivo(this.raiz, elemMinimo, elemMaximo, lista);
        }
        return lista;
    }

    private void listarRangoRecursivo(NodoAVL nodoActual, Object elemMinimo, Object elemMaximo, Lista lista) {
        if (nodoActual != null) {
            Comparable elem = (Comparable) nodoActual.getElem();
            int comparacionMin = elem.compareTo(elemMinimo);
            int comparacionMax = elem.compareTo(elemMaximo);

            if (comparacionMin > 0) {
                listarRangoRecursivo(nodoActual.getIzquierdo(), elemMinimo, elemMaximo, lista);
            }
            if (comparacionMin >= 0 && comparacionMax <= 0) {
                lista.insertar(nodoActual.getElem(), lista.longitud() + 1);
            }
            if (comparacionMax < 0) {
                listarRangoRecursivo(nodoActual.getDerecho(), elemMinimo, elemMaximo, lista);
            }
        }
    }


    public boolean vacio(){
        return this.raiz == null;
    }

    public void vaciar(){
        this.raiz = null;
    }
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVL nodoActual) {
        String texto = "";

        if (nodoActual != null) {
            texto += nodoActual.getElem().toString() + " -> ";
            texto += "HI: ";
            if (nodoActual.getIzquierdo() != null) {
                texto += nodoActual.getIzquierdo().getElem();
            } else {
                texto += "N/A";
            }

            texto += " HD: ";

            if (nodoActual.getDerecho() != null) {
                texto += nodoActual.getDerecho().getElem();
            } else {
                texto += "N/A";
            }

            texto += "\n";

            texto += toStringAux(nodoActual.getIzquierdo());
            texto += toStringAux(nodoActual.getDerecho());

        }

        return texto;
    }

}