package grafos;

import Lineales.dinamicas.Cola;
import Lineales.dinamicas.Lista;

public class Grafo {

    // Variables
    private NodoVert inicio;

    // Constructor
    public Grafo() {
        inicio = null;
    }

    // Propias
    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(Object desde, Object hasta, Object etiqueta) {
        boolean logrado = false;
        NodoVert auxDesde = this.ubicarVertice(desde);
        NodoVert auxHasta = this.ubicarVertice(hasta);

        if (auxDesde != null && auxHasta != null) {
            boolean insertadoIda = insertarAdyacente(auxDesde, auxHasta, etiqueta);
            boolean insertadoVuelta = insertarAdyacente(auxHasta, auxDesde, etiqueta);
            if(insertadoIda && insertadoVuelta){
                logrado = true;
            }
        }

        return logrado;
    }
    private boolean insertarAdyacente(NodoVert origen, NodoVert destino, Object etiqueta){
        boolean valor = false;
        NodoAdy actual = origen.getPrimerAdy();
        if(!existeAdyacente(actual, destino)){
            if(actual!=null){
                NodoAdy ultimo = encontrarUltimoAdyacente(actual);
                ultimo.setSigAdyacente(new NodoAdy(destino, etiqueta));
            }else{
                origen.setPrimerAdy(new NodoAdy(destino, etiqueta));
            }
            valor = true;
        }
        return valor;
    }
    private boolean existeAdyacente(NodoAdy primero, NodoVert destino){
        boolean valor = false;
        NodoAdy actual = primero;
        while(actual != null && !valor){
            if(actual.getVertice().getElem().equals(destino.getElem())){
                valor = true;
            }else{
                actual = actual.getSigAdyacente();
            }
        }
        return valor;
    }
    private NodoAdy encontrarUltimoAdyacente(NodoAdy primero){
        NodoAdy ultimo = primero;
        while(ultimo.getSigAdyacente() != null){
            ultimo = ultimo.getSigAdyacente();
        }
        return ultimo;
    }

    public boolean existeVertice(Object elem) {
        boolean existe = false;
        NodoVert aux = this.inicio;

        if (elem != null) {
            while (aux != null && !existe) {
                if (aux.getElem().equals(elem)) {
                    existe = true;
                } else {
                    aux = aux.getSigVertice();
                }

            }
        }
        return existe;
    }

    public boolean eliminarVertice(Object buscado) {
        boolean exito = false;
        if (inicio != null) {
            if (inicio.getElem().equals(buscado)) {
                this.inicio = inicio.getSigVertice();
                if (inicio != null) {
                    NodoVert aux = this.inicio;
                    while (aux != null) {
                        this.eliminarArcos(aux.getPrimerAdy(), buscado);
                        if (aux.getPrimerAdy() != null && aux.getPrimerAdy().getVertice().getElem().equals(buscado)) {
                            aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
                        }
                        aux = aux.getSigVertice();
                    }
                }
                exito = true;
            } else {
                NodoVert aux = this.inicio;
                NodoVert auxSiguiente = aux.getSigVertice();
                while (aux != null) {
                    if (auxSiguiente != null && auxSiguiente.getElem().equals(buscado)) {
                        aux.setSigVertice(auxSiguiente.getSigVertice());
                    }
                    this.eliminarArcos(aux.getPrimerAdy(), buscado);
                    if (aux.getPrimerAdy() != null && aux.getPrimerAdy().getVertice().getElem().equals(buscado)) {
                        aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
                    }
                    aux = aux.getSigVertice();
                    if (aux != null) {
                        auxSiguiente = aux.getSigVertice();
                    }
                }
                exito = true;
            }
        }
        return exito;
    }

    private void eliminarArcos(NodoAdy inicio, Object buscado) {
        if (inicio != null) {
            NodoAdy aux = inicio;
            NodoAdy auxSiguiente = inicio.getSigAdyacente();
            while (aux != null && auxSiguiente != null) {
                if (auxSiguiente.getVertice().getElem().equals(buscado)) {
                    aux.setSigAdyacente(auxSiguiente.getSigAdyacente());
                }else{
                    aux = aux.getSigAdyacente();
                }
                if (aux != null) {
                    auxSiguiente = aux.getSigAdyacente();
                }
            }
        }
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert desde = ubicarVertice(origen);
        NodoVert hasta = ubicarVertice(destino);
       if(desde != null && hasta != null){
        boolean eliminadoIda = eliminarAdyacente(hasta, desde);
        boolean eliminadoVuelta = eliminarAdyacente(desde, hasta);
        exito = eliminadoIda && eliminadoVuelta;
       }
        return exito;
    }
    private boolean eliminarAdyacente(NodoVert origen, NodoVert destino){
        boolean exito = false;
        NodoAdy actual = origen.getPrimerAdy();
        NodoAdy anterior = null;
        while(actual!=null && !exito){
            if(actual.getVertice().getElem().equals(destino.getElem())){
                if(anterior == null){
                    origen.setPrimerAdy(actual.getSigAdyacente());
                }else{
                    anterior.setSigAdyacente(actual.getSigAdyacente());
                }
                exito = true;
            }else{
                anterior = actual;
                actual = actual.getSigAdyacente();
            }
        }
        return exito;
    }

    public boolean modificarArco(Object origen, Object destino, Object etiquetaNueva){
        boolean exito = false;
        NodoVert desde = ubicarVertice(origen);
        NodoVert hasta = ubicarVertice(destino);

        if(desde != null && hasta != null){
            boolean modificarIda = modificarAdyacente(desde, hasta, etiquetaNueva);
            boolean modificarVuelta = modificarAdyacente(hasta, desde, etiquetaNueva);
            exito = modificarIda && modificarVuelta;
        }
        
        
        return exito;
    }
    private boolean modificarAdyacente(NodoVert origen, NodoVert destino, Object nuevaEtiqueta){
        boolean exito = false;
        NodoAdy actual = origen.getPrimerAdy();
        while(actual != null && !exito){
            if(actual.getVertice().getElem().equals(destino.getElem())){
                actual.setEtiqueta(nuevaEtiqueta);
                exito = true;
            }else{
                actual = actual.getSigAdyacente();
            }
        }
        return exito;
    }

    public boolean existeCamino(Object origen, Object destino) {
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        boolean existe = false;
        if (vertOrigen != null && vertDestino != null) {
            existe = existeCaminoAux(vertOrigen, destino, new Lista());
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVert actual, Object destino, Lista visitados) {
        boolean encontrado = false;
        if (actual != null && !pertenece(visitados, actual.getElem())) {
            if (actual.getElem().equals(destino)) {
                encontrado = true;
            } else {
                visitados.insertar(actual.getElem(), visitados.longitud() + 1);
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null && !encontrado) {
                    encontrado = existeCaminoAux(ady.getVertice(), destino, visitados);
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return encontrado;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista caminoActual = new Lista();
        Lista caminoMax = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            caminoMasLargoAux(vertOrigen, destino, caminoActual, caminoMax, new Lista());
        }
        return caminoMax;
    }

    private void caminoMasLargoAux(NodoVert actual, Object destino, Lista camino, Lista caminoMax, Lista visitados) {
        if (!pertenece(visitados, actual.getElem())) {
            camino.insertar(actual.getElem(), camino.longitud() + 1);
            visitados.insertar(actual.getElem(), visitados.longitud() + 1);

            if (actual.getElem().equals(destino)) {
                if (camino.longitud() > caminoMax.longitud()) {
                    copiarLista(camino, caminoMax);
                }
            } else {
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null) {
                    caminoMasLargoAux(ady.getVertice(), destino, camino, caminoMax, visitados);
                    ady = ady.getSigAdyacente();
                }
            }

            camino.eliminar(camino.longitud());
            visitados.eliminar(visitados.longitud());
        }
    }

    private boolean pertenece(Lista lista, Object elem) {
        int i = 1;
        boolean encontrado = false;
        while (i <= lista.longitud() && !encontrado) {
            if (lista.recuperar(i).equals(elem)) {
                encontrado = true;
            } else {
                i++;
            }
        }
        return encontrado;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista mejorCamino = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            Lista visitados = new Lista();
            int[] mejorCaminoLong = {0};
            caminoMasCortoAux(vertOrigen, destino, visitados, mejorCamino, 0, mejorCaminoLong);
        }
        return mejorCamino;
    }

    private void caminoMasCortoAux(NodoVert actual, Object destino, Lista visitados, Lista mejorCamino, int visitadosLong, int[] mejorCaminoLong) {
        visitadosLong++;
        visitados.insertar(actual.getElem(), visitadosLong + 1);

        if (actual.getElem().equals(destino)) {
            if (mejorCaminoLong[0] == 0 || visitadosLong < mejorCaminoLong[0]) {
                copiarLista(visitados, mejorCamino);
                mejorCaminoLong[0] = visitadosLong;
            }
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                if (!pertenece(visitados, ady.getVertice().getElem())) {
                    // PODA: Solo profundiza si el camino actual aún puede ser más corto que el mejor hallado
                    if (mejorCaminoLong[0] == 0 || visitadosLong < mejorCaminoLong[0]) {
                        caminoMasCortoAux(ady.getVertice(), destino, visitados, mejorCamino, visitadosLong, mejorCaminoLong);
                    }
                }
                ady = ady.getSigAdyacente();
            }
        }

        visitados.eliminar(visitadosLong);
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        profundidadDesde(inicio, visitados);
        return visitados;
    }

    private void profundidadDesde(NodoVert nodo, Lista visitados) {
        if (nodo != null && !pertenece(visitados, nodo.getElem())) {
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null) {
                profundidadDesde(ady.getVertice(), visitados);
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        Cola q = new Cola();
        if (inicio != null) {
            q.poner(inicio);
            while (!q.esVacia()) {
                NodoVert nodo = (NodoVert) q.obtenerFrente();
                q.sacar();
                if (!pertenece(visitados, nodo.getElem())) {
                    visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
                    NodoAdy ady = nodo.getPrimerAdy();
                    while (ady != null) {
                        q.poner(ady.getVertice());
                        ady = ady.getSigAdyacente();
                    }
                }
            }
        }
        return visitados;
    }

    private void copiarLista(Lista origen, Lista destino) {
        destino.vaciar();
        for (int i = 1; i <= origen.longitud(); i++) {
            destino.insertar(origen.recuperar(i), i);
        }
    }

    private boolean eliminarArcoAux(NodoVert nodo, Object etiqueta) {
        boolean logrado = false;
        if (nodo != null) {
            NodoAdy arcoActual = nodo.getPrimerAdy();
            NodoAdy arcoAnterior = nodo.getPrimerAdy();
            while (arcoActual != null && !logrado) {
                if (arcoActual.getEtiqueta().equals(etiqueta) && arcoActual == nodo.getPrimerAdy()) {
                    nodo.setPrimerAdy(arcoActual.getSigAdyacente());
                    logrado = true;
                } else if (arcoActual.getEtiqueta().equals(etiqueta) && arcoActual != nodo.getPrimerAdy()) {
                    arcoAnterior.setSigAdyacente(arcoActual.getSigAdyacente());
                    ;
                    logrado = true;
                }
                arcoAnterior = arcoActual;
                arcoActual = arcoActual.getSigAdyacente();
            }
        }
        return logrado;
    }

    public boolean existeArco(Object desde, Object hasta) {
        boolean existe = false;
        if (this.inicio != null) {
            NodoVert aux = this.inicio;
            while (aux != null && !existe) {
                if (aux.getElem().equals(desde)) {
                    existe = true;
                } else {
                    aux = aux.getSigVertice();
                }
            }
            if (existe) {
                existe = recorrerArcosAux(aux, hasta);
            }
        }
        return existe;
    }

    private boolean recorrerArcosAux(NodoVert vertice, Object buscado) {
        boolean encontrado = false;
        if (vertice != null) {
            NodoAdy aux = vertice.getPrimerAdy();
            while (aux != null && !encontrado) {
                if (aux.getVertice().getElem().equals(buscado)) {
                    encontrado = true;
                }
                aux = aux.getSigAdyacente();
            }
        }
        return encontrado;
    }

    public boolean vacio() {
        return inicio == null;
    }

    public String toString() {

        String string = "";
        NodoVert verticeAux = this.inicio;
        NodoAdy nodoAdyacente = null;

        while (verticeAux != null) {
            string = string + "\nVertice: " + verticeAux.getElem() + " [";
            nodoAdyacente = verticeAux.getPrimerAdy();
            while (nodoAdyacente != null) {
                string += nodoAdyacente.getVertice().getElem() + "," + nodoAdyacente.getEtiqueta() + ";";
                nodoAdyacente = nodoAdyacente.getSigAdyacente();
            }
            string += "]";
            verticeAux = verticeAux.getSigVertice();
        }

        return string;

    }

public Lista caminoMasCortoPorEtiquetaMinima(Object desde, Object hasta) {
        Lista mejorCamino = new Lista();
        NodoVert vertOrigen = ubicarVertice(desde);
        NodoVert vertDestino = ubicarVertice(hasta);

        if (vertOrigen != null && vertDestino != null) {
            Lista visitados = new Lista();
            int[] minPuntaje = { Integer.MAX_VALUE };
            // Le pasamos 0 como la longitud inicial de visitados
            caminoEtiquetaMinimaAux(vertOrigen, hasta, visitados, mejorCamino, 0, minPuntaje, 0); 
        }
        return mejorCamino;
    }

    private void caminoEtiquetaMinimaAux(NodoVert actual, Object destino, Lista visitados, Lista mejorCamino, int puntajeAcumulado, int[] minPuntaje, int longVisitados) {
        
        // Insertamos en la longitud actual + 1
        visitados.insertar(actual.getElem(), longVisitados + 1);

        if (actual.getElem().equals(destino)) {
            if (puntajeAcumulado < minPuntaje[0]) {
                minPuntaje[0] = puntajeAcumulado;
                copiarLista(visitados, mejorCamino);
            }
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                int pesoArco = (int) ady.getEtiqueta();

                if (!pertenece(visitados, vecino.getElem()) && (puntajeAcumulado + pesoArco < minPuntaje[0])) {
                    // En la llamada recursiva, mandamos longVisitados + 1. 
                    // Cuando esta llamada termine (backtrack), nuestra variable local longVisitados seguirá intacta.
                    caminoEtiquetaMinimaAux(vecino, destino, visitados, mejorCamino, puntajeAcumulado + pesoArco, minPuntaje, longVisitados + 1);
                }
                ady = ady.getSigAdyacente();
            }
        }

        // Usamos nuestra variable para eliminar, sin llamar JAMÁS a .longitud()
        visitados.eliminar(longVisitados + 1);
    }

    public Lista obtenerTodosLosCaminos(Object origen, Object destino) {
        Lista caminos = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);

        if (nodoOrigen != null && nodoDestino != null) {
            Lista caminoActual = new Lista();
            Lista visitados = new Lista(); // reemplazo de HashSet
            obtenerTodosLosCaminosAux(nodoOrigen, destino, caminoActual, caminos, visitados);
        }

        return caminos;
    }

    private void obtenerTodosLosCaminosAux(NodoVert actual, Object destino, Lista caminoActual, Lista caminos,
            Lista visitados) {
        visitados.insertar(actual.getElem(), visitados.longitud() + 1);
        caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);

        if (actual.getElem().equals(destino)) {
            Lista copia = copiarLista(caminoActual);
            caminos.insertar(copia, caminos.longitud() + 1);
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                if (!contiene(visitados, vecino.getElem())) {
                    obtenerTodosLosCaminosAux(vecino, destino, caminoActual, caminos, visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }

        // backtrack
        visitados.eliminar(visitados.longitud());
        caminoActual.eliminar(caminoActual.longitud());
    }

    private Lista copiarLista(Lista original) {
        Lista copia = new Lista();
        for (int i = 1; i <= original.longitud(); i++) {
            Object elem = original.recuperar(i);
            copia.insertar(elem, i);
        }
        return copia;
    }

    private boolean contiene(Lista lista, Object elem) {
        boolean encontrado = false;
        int i = 1;
        while (i <= lista.longitud() && !encontrado) {
            if (lista.recuperar(i).equals(elem)) {
                encontrado = true;
            } else {
                i++;
            }
        }
        return encontrado;
    }

    public boolean modificarVertice(Object aCambiar, Object nuevoDato) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(aCambiar);
        if (aux != null) {
            aux.setElem(nuevoDato);
            exito = true;
        }
        return exito;
    }   
}
