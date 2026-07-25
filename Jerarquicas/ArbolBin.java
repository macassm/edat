package Jerarquicas;

import Lineales.dinamicas.*;

public class ArbolBin {
    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char posHijo) {
        boolean realizado = true;

        if (raiz == null) {
            if (elemPadre == null) {
                this.raiz = new NodoArbol(elemNuevo, null, null);
            } else {
                realizado = false;
            }

        } else {
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre);
            if (nPadre != null) {
                if (posHijo == 'I' && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if (posHijo == 'D' && nPadre.getDerecho() == null) {
                    nPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    realizado = false;
                }
            } else {
                realizado = false;
            }
        }
        return realizado;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {

        NodoArbol resultado = null;

        if (n != null) {
            if (n.getElem().equals(buscado)) {
                resultado = n;
            } else {
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }

        return resultado;

    }

    private NodoArbol obtenerNodoPorPosicion(NodoArbol nodoActual, int posicion, int[] arr) {
        NodoArbol resultado = null;

        if (nodoActual != null) {
            arr[0] += 1;
            if (arr[0] == posicion) {
                resultado = nodoActual;
            } else {
                resultado = obtenerNodoPorPosicion(nodoActual.getIzquierdo(), posicion, arr);
                if (resultado == null) {
                    resultado = obtenerNodoPorPosicion(nodoActual.getDerecho(), posicion, arr);
                }
            }
        }
        return resultado;
    }

    public boolean insertarPorPosicion(Object elemNuevo, int posPadre, char posHijo) {

        boolean exito = false;

        if (posPadre <= cantidadElementos(this.raiz)) {
            int[] arr = { 0 };

            NodoArbol nodo = obtenerNodoPorPosicion(raiz, posPadre, arr);

            if (nodo != null) {
                if (posHijo == 'I' && nodo.getIzquierdo() == null) {
                    nodo.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                    exito = true;
                } else if (posHijo == 'D' && nodo.getDerecho() == null) {
                    nodo.setDerecho(new NodoArbol(elemNuevo, null, null));
                    exito = true;
                }
            }
        }
        return exito;

    }

    private int cantidadElementos(NodoArbol nodo) {
        int elementos = 0;

        if (nodo != null) {
            elementos += 1 + cantidadElementos(nodo.getIzquierdo())
                    + cantidadElementos(nodo.getDerecho());
        }
        return elementos;
    }

    public Object padre(Object elemento) {
        Object padre = obtenerPadre(this.raiz, elemento);
        return padre;
    }

    private Object obtenerPadre(NodoArbol n, Object buscado) {
        Object padreEncontrado = null;
        if (n != null) {
            if ((n.getIzquierdo() != null && n.getIzquierdo().getElem().equals(buscado))
                    || (n.getDerecho() != null && n.getDerecho().getElem().equals(buscado))) {

                padreEncontrado = n.getElem();

            } else {
                padreEncontrado = obtenerPadre(n.getIzquierdo(), buscado);
                if (padreEncontrado == null) {
                    padreEncontrado = obtenerPadre(n.getDerecho(), buscado);
                }
            }
        }
        return padreEncontrado;
    }

    public int altura() {
        int altura = -1;
        altura = calcularAltura(this.raiz, altura);
        return altura;
    }

    private int calcularAltura(NodoArbol n, int alt) {
        int izq, der, alturaReal;
        alturaReal = alt;
        if (n != null) {
            alturaReal++;
            izq = calcularAltura(n.getIzquierdo(), alturaReal);
            der = calcularAltura(n.getDerecho(), alturaReal);

            if (izq > alturaReal) {
                alturaReal = izq;
            }
            if (der > alturaReal) {
                alturaReal = der;
            }
        }
        return alturaReal;
    }

    public int nivel(Object elem) {
        int nivel = -1;
        nivel = calcularNivel(elem, nivel, this.raiz);
        return nivel;
    }

    private int calcularNivel(Object elem, int nivel, NodoArbol nodo) {
        int nivelReal = nivel;
        int nivelEncontrado = -1;

        if (nodo != null) {
            nivelReal++;
            if (nodo.getElem().equals(elem)) {
                nivelEncontrado = nivelReal;
            } else {
                nivelEncontrado = calcularNivel(elem, nivelReal, nodo.getIzquierdo());
                if (nivelEncontrado == -1) {
                    nivelEncontrado = calcularNivel(elem, nivelReal, nodo.getDerecho());
                }
            }
        }

        return nivelEncontrado;
    }

    public Lista listarPreorden() {
        Lista preorden = new Lista();
        recorrerPreorden(preorden, this.raiz);
        return preorden;
    }

    private void recorrerPreorden(Lista lista, NodoArbol padre) {
        if (padre != null) {
            lista.insertar(padre.getElem(), lista.longitud() + 1);
            recorrerPreorden(lista, padre.getIzquierdo());
            recorrerPreorden(lista, padre.getDerecho());
        }
    }

    public Lista listarInorden() {
        Lista inorden = new Lista();
        recorrerInorden(inorden, this.raiz);
        return inorden;
    }

    private void recorrerInorden(Lista lista, NodoArbol padre) {
        if (padre != null) {
            recorrerInorden(lista, padre.getIzquierdo());
            lista.insertar(padre.getElem(), lista.longitud() + 1);
            recorrerInorden(lista, padre.getDerecho());
        }
    }

    public Lista listarPosorden() {
        Lista posorden = new Lista();
        recorrerPosorden(posorden, this.raiz);
        return posorden;
    }

    private void recorrerPosorden(Lista lista, NodoArbol padre) {
        if (padre != null) {
            recorrerPosorden(lista, padre.getIzquierdo());
            recorrerPosorden(lista, padre.getDerecho());
            lista.insertar(padre.getElem(), lista.longitud() + 1);
        }
    }

    public Lista listarPorNiveles(Lista lista, NodoArbol padre) {
        Lista arbolNivel = new Lista();
        Cola cola = new Cola();
        int contador = 1;
        NodoArbol nodoActual;

        if (this.raiz != null) {
            cola.poner(this.raiz);
            while (!cola.esVacia()) {
                nodoActual = (NodoArbol) cola.obtenerFrente();
                arbolNivel.insertar(nodoActual.getElem(), contador);
                cola.sacar();
                contador++;
                if (nodoActual.getIzquierdo() != null) {
                    cola.poner(nodoActual.getIzquierdo());
                }
                if (nodoActual.getDerecho() != null) {
                    cola.poner(nodoActual.getDerecho());
                }

            }

        }
        return arbolNivel;
    }

    public ArbolBin clon() {
        ArbolBin clon = new ArbolBin();
        clon.raiz = clon.recorrerClon(this.raiz);
        return clon;
    }

    private NodoArbol recorrerClon(NodoArbol nodoActual) {
        NodoArbol nodo = null;
        if (nodoActual != null) {
            nodo = new NodoArbol(nodoActual.getElem(),
                    recorrerClon(nodoActual.getIzquierdo()),
                    recorrerClon(nodoActual.getDerecho()));
        }
        return nodo;
    }

    public String toString() {
        String mensaje = padresEhijos(this.raiz);

        return mensaje;
    }

    private String padresEhijos(NodoArbol padre) {
        String retorno = "";

        if (padre != null) {
            retorno += "Padre: " + padre.getElem().toString();

            if (padre.getIzquierdo() != null) {
                retorno += ". Hijo I: " + padre.getIzquierdo().getElem().toString();
            }
            if (padre.getDerecho() != null) {
                retorno += ". Hijo D: " + padre.getDerecho().getElem().toString();
            }

            retorno += "\n";

            retorno += padresEhijos(padre.getIzquierdo());
            retorno += padresEhijos(padre.getDerecho());
        }
        return retorno;
    }

    public boolean verificarPatron(Lista unLista) {
        boolean verifica;
        int i = 1;
        if (unLista.esVacia() || this.raiz == null) {
            verifica = false;
        } else {
            verifica = auxiliarPatron(unLista, this.raiz, i);
        }
        return verifica;
    }

    private boolean auxiliarPatron(Lista unaLista, NodoArbol padre, int i) {
        boolean ok = true;
        if (padre == null) {
            ok = false;
        } else if (!padre.getElem().equals(unaLista.recuperar(i))) {
            ok = false;
        } else if (i == unaLista.longitud()) {
            if (padre.getDerecho() != null || padre.getIzquierdo() != null) {
                ok = false;
            }
        } else {
            ok = auxiliarPatron(unaLista, padre.getIzquierdo(), i + 1)
                    || auxiliarPatron(unaLista, padre.getDerecho(), i + 1);
        }

        return ok;
    }

    // Implementar la operación frontera() que devuelve una lista con la secuencia
    // formada por los elementos
    // almacenados en las hojas del árbol binario, tomadas de izquierda a derecha.

    public Lista frontera() {
        Lista aux = new Lista();

        if (this.raiz != null) {
            auxFrontera(this.raiz, aux);
        }
        return aux;
    }

    private void auxFrontera(NodoArbol padre, Lista aux) {

        if (padre != null) {
            if (padre.getDerecho() == null && padre.getIzquierdo() == null) {
                aux.insertar(padre.getElem(), aux.longitud() + 1);
            } else {
                auxFrontera(padre.getIzquierdo(), aux);
                auxFrontera(padre.getDerecho(), aux);
            }
        }

    }

    public ArbolBin clonarInvertido() {
        ArbolBin nuevo = new ArbolBin();

        if (this.raiz != null) {
            nuevo.raiz = auxClonar(this.raiz);
        }
        return nuevo;
    }

    private NodoArbol auxClonar(NodoArbol padre) {

        NodoArbol nodoNuevo = null;

        if (padre != null) {
            nodoNuevo = new NodoArbol(padre.getElem(), null, null);
            nodoNuevo.setIzquierdo(auxClonar(padre.getDerecho()));
            nodoNuevo.setDerecho(auxClonar(padre.getIzquierdo()));
        }
        return nodoNuevo;
    }

    public boolean equels(ArbolBin otro) {
        boolean igualdad = false;

        if (this.raiz != null && otro.raiz != null) {
            igualdad = recorrerIgualdad(otro.raiz, this.raiz);
        } else if (this.raiz == null && otro.raiz == null) {
            igualdad = true;
        }

        return igualdad;
    }

    private boolean recorrerIgualdad(NodoArbol otroPadre, NodoArbol padre) {
        boolean verifica = true;

        if (otroPadre == null && padre == null) {
            verifica = true;
        } else if (otroPadre == null || padre == null) {
            verifica = false;
        } else if (!otroPadre.getElem().equals(padre.getElem())) {
            verifica = false;
        } else {
            verifica = recorrerIgualdad(otroPadre.getIzquierdo(), padre.getIzquierdo()) &&
                    recorrerIgualdad(otroPadre.getDerecho(), padre.getDerecho());
        }
        return verifica;
    }

    public boolean estaRepetido(Object n) {
        int cont[] = { 0 };

        return verificaRepeticion(this.raiz, n, cont);
    }

    private boolean verificaRepeticion(NodoArbol padre, Object elem, int[] cont) {
        boolean repite = false;
        if (padre != null) {

            if (padre.getElem().equals(elem)) {
                cont[0]++;
                if (cont[0] < 2) {
                    repite = verificaRepeticion(padre.getIzquierdo(), elem, cont);
                    if (!repite) {
                        repite = verificaRepeticion(padre.getDerecho(), elem, cont);
                    }
                } else {
                    repite = true;
                }
            }
        }
        return repite;
    }

}