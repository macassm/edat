package Conjuntistas;

import Lineales.dinamicas.Lista;

public class ArbolABB {
    private NodoAbb raiz;

    public ArbolABB(){
        this.raiz = null;
    }

    public boolean insertar(Comparable unElem){
        boolean insertado = false;
        if(this.raiz == null){
            this.raiz = new NodoAbb(unElem, null, null);
            insertado = true; 
        }else{
            insertado = insertarAux(this.raiz, unElem);
        }

        return insertado;
    }

    private boolean insertarAux(NodoAbb n, Comparable unElem){
        boolean logrado = false;

        if(n != null){
            if(n.getElem().compareTo(unElem) == 0){
                logrado = false;
            }else if(n.getElem().compareTo(unElem) > 0){
               
                if(n.getHijoIzquierdo() == null){
                    n.setHijoIzquierdo(new NodoAbb(unElem, null, null));
                    logrado = true;
                }else{
                    logrado = insertarAux(n.getHijoIzquierdo(), unElem); 
                }
            }else{
                if(n.getHijoDerecho() == null){
                    n.setHijoDerecho(new NodoAbb(unElem, null, null));
                    logrado = true;
                }else{
                    logrado = insertarAux(n.getHijoDerecho(), unElem);
                }
            }
        }
        return logrado;
    }

    public boolean eliminar(Comparable elem) {
        boolean[] exito = { false };
        if (this.raiz != null) {
            this.raiz = eliminarRec(this.raiz, elem, exito);
        }
        return exito[0];
    }


   private NodoAbb eliminarRec(NodoAbb n, Comparable elem, boolean[] exito) {
    NodoAbb eliminado = n;

        if (n != null) {                                   
            int comparar = elem.compareTo(n.getElem());

            if (comparar == 0) {
                if (n.getHijoIzquierdo() == null && n.getHijoDerecho() == null) {
                    eliminado = null;
                } else if (n.getHijoIzquierdo() != null && n.getHijoDerecho() == null) {
                    eliminado = n.getHijoIzquierdo();
                } else if (n.getHijoIzquierdo() == null && n.getHijoDerecho() != null) {
                    eliminado = n.getHijoDerecho();
                } else {
                    Object mayor = buscarMayor(n.getHijoIzquierdo());
                    n.setHijoIzquierdo(eliminarRec(n.getHijoIzquierdo(), (Comparable) mayor, exito));
                    n.setElem((Comparable)mayor);
                }
                exito[0] = true;

            } else if (comparar < 0) {
                n.setHijoIzquierdo(eliminarRec(n.getHijoIzquierdo(), elem, exito));
            } else {
                n.setHijoDerecho(eliminarRec(n.getHijoDerecho(), elem, exito));
            }
        }
        return eliminado;

    }

    private Object buscarMayor(NodoAbb n) {
        Object mayor = null;
        if (n != null) {
            mayor = n.getElem();
            if (n.getHijoDerecho() != null) {
                mayor = buscarMayor(n.getHijoDerecho());
            }
        }

        return mayor;
    }

    public boolean pertenece(Object elem){
        return perteneceRecursivo(this.raiz, (Comparable) elem);
    }

    private boolean perteneceRecursivo(NodoAbb n, Comparable elem){
        boolean pertenece = false;
        int comparacion;

        if(n != null){
            comparacion = elem.compareTo(n.getElem());

            if(comparacion == 0){
                pertenece = true;
            }else{
                if(comparacion < 0){
                    pertenece = perteneceRecursivo(n.getHijoIzquierdo(), elem);
                }else{
                    pertenece = perteneceRecursivo(n.getHijoDerecho(), elem);
                }
            }
        }

        return pertenece;
    }

    public Object  minimoElem(){
        Object minimo = null;
        NodoAbb actual = this.raiz; 

        while(actual != null){
            minimo = actual.getElem();
            actual = actual.getHijoIzquierdo();
        }

        return minimo;
    }

    public Object  maximoElem(){
        Object maximo = null;
        NodoAbb actual = this.raiz; 

        while(actual != null){
            maximo = actual.getElem();
            actual = actual.getHijoDerecho();
        }

        return maximo;
    }

    public Lista listar(){
        Lista listado = new Lista();
        listarAux(this.raiz, listado);
        return listado;
    }

    private void listarAux(NodoAbb actual, Lista listado){

        if(actual != null){
            listarAux(actual.getHijoIzquierdo(), listado);
            listado.insertar(actual.getElem(), listado.longitud()+1);
            listarAux(actual.getHijoDerecho(), listado);
        }

    }

    public Lista listarRango(Object elemMinimo, Object elemMaximo) {
        Lista lista = new Lista();
        if (((Comparable) elemMinimo).compareTo(elemMaximo) <= 0) {
            listarRangoRecursivo(this.raiz, elemMinimo, elemMaximo, lista);
        }
        return lista;
    }

    private void listarRangoRecursivo(NodoAbb nodoActual, Object elemMinimo, Object elemMaximo, Lista lista) {
        if (nodoActual != null) {
            Comparable elem = (Comparable) nodoActual.getElem();
            int comparacionMin = elem.compareTo(elemMinimo);
            int comparacionMax = elem.compareTo(elemMaximo);

            if (comparacionMin > 0) {
                listarRangoRecursivo(nodoActual.getHijoIzquierdo(), elemMinimo, elemMaximo, lista);
            }
            if (comparacionMin >= 0 && comparacionMax <= 0) {
                lista.insertar(nodoActual.getElem(), lista.longitud() + 1);
            }
            if (comparacionMax < 0) {
                listarRangoRecursivo(nodoActual.getHijoDerecho(), elemMinimo, elemMaximo, lista);
            }
        }
    }


    public boolean vacio(){
        return this.raiz == null;
    }


    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////Practica para parcial///////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    

    public void eliminarMinimo(){
        NodoAbb aux = this.raiz;
        NodoAbb padre = null;
        
        if(this.raiz != null){

            if(this.raiz.getHijoIzquierdo() == null){
                this.raiz = aux.getHijoDerecho(); 
            }else{
                while(aux.getHijoIzquierdo() != null){
                    padre = aux;
                    aux = aux.getHijoIzquierdo();
                }
                padre.setHijoIzquierdo(aux.getHijoDerecho());
            }
            
        }
    }

    public NodoAbb clonarParteInvertida(Comparable elem){
        NodoAbb clon = null;
        if(this.raiz != null){
            clon = clonarInvertidaAux(this.raiz,elem);
        }
        return clon;
    }

    private NodoAbb clonarInvertidaAux(NodoAbb n, Comparable elem){
        NodoAbb resultado = null;
        if(n != null){
            int comp = n.getElem().compareTo(elem);

            if(comp > 0){
                resultado = clonarInvertidaAux(n.getHijoIzquierdo(), elem);
            }else if(comp < 0){
                resultado = clonarInvertidaAux(n.getHijoDerecho(), elem);
            }else{
                resultado = clonEinversion(n);
            }
        }

        return resultado;
    }

    private NodoAbb clonEinversion(NodoAbb n){
        NodoAbb nuevo = null;
        if(n != null){
            nuevo = new NodoAbb(n.getElem(), null, null);
            NodoAbb aux = n.getHijoDerecho();
            nuevo.setHijoDerecho(clonEinversion(n.getHijoIzquierdo())); 
            nuevo.setHijoIzquierdo(clonEinversion(aux)); 

        }
        return nuevo;
    }

    public Comparable mejorCandidato(Comparable unElem){
        Comparable mejor = -1;
        if(this.raiz != null){
            mejor = auxMejor(this.raiz, unElem);
        }

        return mejor;
    }

    private Comparable auxMejor(NodoAbb n, Comparable buscado){
        Comparable retorno = -1; 
        if(n != null){
            int comp = n.getElem().compareTo(buscado);
            if(comp > 0){
                retorno = auxMejor(n.getHijoIzquierdo(), buscado);
            }
            if(comp < 0){
                retorno = auxMejor(n.getHijoDerecho(), buscado);
            }
            if(comp == 0){
                retorno = auxCandidato(n); 
            }
        }
        return retorno;
    }

    private Comparable auxCandidato(NodoAbb n){
        Comparable devolucion = -1;
        if(n.getHijoIzquierdo() != null && n.getHijoDerecho() == null){
            devolucion = n.getHijoIzquierdo().getElem();
        }
        if(n.getHijoIzquierdo() == null && n.getHijoDerecho() != null){
            devolucion = n.getHijoDerecho().getElem();
        }
        if(n.getHijoIzquierdo() != null && n.getHijoDerecho() != null){
            NodoAbb mayorIzq = n.getHijoIzquierdo();
            while(mayorIzq.getHijoDerecho() != null){
                mayorIzq = mayorIzq.getHijoDerecho();
            }
            NodoAbb menorDer = n.getHijoDerecho();
            while(menorDer.getHijoIzquierdo() != null){
                menorDer = menorDer.getHijoIzquierdo();
            }
            int comp1 = mayorIzq.getElem().compareTo(n.getElem());
            int comp2 = menorDer.getElem().compareTo(n.getElem());
            
            if(comp1 < 0){
                comp1 = -comp1;
            }
            if(comp2 < 0){
                comp2 = -comp2;
            }

            if(comp1 > comp2){
                devolucion = menorDer.getElem();
            }else {
                devolucion = mayorIzq.getElem();
            }

        }
        return devolucion;
    }

    public Lista listarMayorIgual(Comparable unElem){
        Lista ls = new Lista();
        if(this.raiz != null){
            auxListarMayor(this.raiz,ls,unElem);
        }
        return ls;
    }

    private void auxListarMayor(NodoAbb n, Lista ls, Comparable unElem){

        if(n != null){
            int comp = n.getElem().compareTo(unElem);

            if(comp < 0){
                auxListarMayor(n.getHijoDerecho(), ls, unElem);
            }else{
                ls.insertar(n.getElem(), ls.longitud()+1);
                auxListarMayor(n.getHijoIzquierdo(), ls, unElem);
                auxListarMayor(n.getHijoDerecho(), ls, unElem);
            }
        }   
    }

    public Lista listarMenor(Comparable unElem){
        Lista ls = new Lista();
        if(this.raiz != null){
            auxListarMenor(this.raiz,ls,unElem);
        }
        return ls;
    }

    private void auxListarMenor(NodoAbb n,Lista ls,Comparable unElem){

        if(n != null){

            int comp = n.getElem().compareTo(unElem);

            if(comp >= 0){
                auxListarMenor(n.getHijoIzquierdo(), ls, unElem);
            }else{
                ls.insertar(n.getElem(), ls.longitud()+1);
                auxListarMenor(n.getHijoIzquierdo(), ls, unElem);
                auxListarMenor(n.getHijoDerecho(), ls, unElem);
            }
        }
    }

}