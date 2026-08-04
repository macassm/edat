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

    public boolean insertarArco(Object desde, Object hasta, int etiqueta) {
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
    private boolean insertarAdyacente(NodoVert origen, NodoVert destino, int etiqueta){
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
        if (this.inicio != null) {
            //  Revisamos si el vértice a borrar es el primero de la lista
            if (this.inicio.getElem().equals(buscado)) {

                eliminarArcosHaciaVertice(this.inicio); // Borra los arcos
                this.inicio = this.inicio.getSigVertice(); // Lo desengancha
                exito = true;
            } else {
                NodoVert aux = this.inicio;
                while (aux.getSigVertice() != null && !exito) {
                    if (aux.getSigVertice().getElem().equals(buscado)) {
                        NodoVert aBorrar = aux.getSigVertice();
                        eliminarArcosHaciaVertice(aBorrar); // Borra los arcos de vuelta
                        aux.setSigVertice(aBorrar.getSigVertice()); // Lo desengancha del grafo y se pierde su información
                        exito = true;
                    } else {
                        aux = aux.getSigVertice();
                    }
                }
            }
        }
        return exito;
    }

    private void eliminarArcosHaciaVertice(NodoVert verticeABorrar) {
        // Nos paramos alrededor del nodo que queremos borrar
        NodoAdy ady = verticeABorrar.getPrimerAdy();
        Object buscado = verticeABorrar.getElem();
        
        // Por cada nodo alrededor, vamos a él y borramos el arco que vuelve
        while (ady != null) {
            NodoVert vecino = ady.getVertice();
            eliminarArcoUnidireccional(vecino, buscado);
            ady = ady.getSigAdyacente();
        }
    }

    private void eliminarArcoUnidireccional(NodoVert origen, Object destinoBuscado) {
        boolean eliminado = false;
        if (origen != null && origen.getPrimerAdy() != null) {
            //revisamos si es el primer adyacente el que había que borrar, sino buscamos los siguientes
            if (origen.getPrimerAdy().getVertice().getElem().equals(destinoBuscado)) {
                origen.setPrimerAdy(origen.getPrimerAdy().getSigAdyacente());
            } else {
                NodoAdy auxAdy = origen.getPrimerAdy();
                //Como no era el primer adyacente el buscado, mientras haya otros adyacentes y no hayamos eliminado el deseado
                while (auxAdy.getSigAdyacente() != null && !eliminado) {
                    //preguntamos si el siguiente es el buscaddo y lo borramos, sino pasamos al siguiente
                    if (auxAdy.getSigAdyacente().getVertice().getElem().equals(destinoBuscado)) {
                        auxAdy.setSigAdyacente(auxAdy.getSigAdyacente().getSigAdyacente());
                        eliminado = true;
                    } else{
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                }
            }
        }
    }

    public boolean modificarArco(Object origen, Object destino, int etiquetaNueva){
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
    private boolean modificarAdyacente(NodoVert origen, NodoVert destino, int nuevaEtiqueta){
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

public boolean esPosibleLlegar(Object vertice1, Object vertice2, int maxPuntos){
        boolean exito = false;
        NodoVert desde = ubicarVertice(vertice1);
        NodoVert hasta = ubicarVertice(vertice2);
        
        if (desde != null && hasta != null) {
            // Pasamos 0 como costo acumulado inicial, y 0 como longitud de la lista
            exito = esPosibleLlegarAux(desde, vertice2, 0, maxPuntos, new Lista(), 0);
        }
        return exito;
    }

    private boolean esPosibleLlegarAux(NodoVert actual, Object destino, int costoAcum, int maxPuntos, Lista visitados, int visitadosLong) {
        boolean encontrado = false;
        visitadosLong++;
        visitados.insertar(actual.getElem(), visitadosLong);

        if (actual.getElem().equals(destino)) {
            encontrado = true;
        } else {
            NodoAdy nodo = actual.getPrimerAdy();
            while (nodo != null && !encontrado) {
                int pesoArco = nodo.getEtiqueta();
                
                // Solo avanza si no lo visitó Y si sumar esta puerta NO supera el límite total
                if (!pertenece(visitados, nodo.getVertice().getElem()) && (costoAcum + pesoArco <= maxPuntos)) {
                    encontrado = esPosibleLlegarAux(nodo.getVertice(), destino, costoAcum + pesoArco, maxPuntos, visitados, visitadosLong);
                }
                nodo = nodo.getSigAdyacente();
            }
        }
        
        visitados.eliminar(visitadosLong);
        return encontrado;
    }

    public Lista minimoPuntaje(Object origen, Object destino, int[] puntajeMinimo) {
        NodoVert desde = ubicarVertice(origen);
        NodoVert hasta = ubicarVertice(destino);

        //creamos un arreglo para mantener el valor de la Lista a través de llamadas recursivas
        Lista[] mejorCamino = new Lista[1];
        mejorCamino[0] = new Lista();

        puntajeMinimo[0] = -1;

        if(desde != null && hasta != null){
            Lista visitados = new Lista();
            minimoPuntajeAux(desde, destino, visitados, 0 , mejorCamino, puntajeMinimo, 0);
        }
    
    return mejorCamino[0];
    }
    
    private void minimoPuntajeAux(NodoVert actual, Object destino, Lista visitados, int cantVisitados, Lista[] mejorCamino, int[] puntajeMinimo, int costoActual) {
            //Verifico que el nodo vertice actual sea distinto de null y que no me este parando en una habitacion que ya visite
            if(actual != null && !pertenece(visitados, actual.getElem())){
                //Agrego la habitacion actual a visitados
                cantVisitados++;
                visitados.insertar(actual.getElem(), cantVisitados);
                //Verifico si estoy en el destino, si me encuentro en el destino verifico si es el primer camino que encontre o si el costo actual es menor al mínimo puntaje
                //Guardo el costoActual en puntaje minimo y copio la lista en mejorCamino
                if(actual.getElem().equals(destino)){
                    if(puntajeMinimo[0] == -1 || costoActual < puntajeMinimo[0]){
                        puntajeMinimo[0] = costoActual;
                        mejorCamino[0] = visitados.clone();
                    }
                }else{
                    //Tomo el primer adyacente del nodo en el que estoy parado para pasar a las siguientes habitaciones
                    NodoAdy siguiente = actual.getPrimerAdy();
                    while(siguiente != null){
                        //Verifico que la habitacion que voy a visitar no este en la lista visitados
                        if(!pertenece(visitados, siguiente.getVertice().getElem())){
                            //En nuevoCosto guardo el maximo entre el costoActual y el costo de pasar a la siguiente habitacion, luego lo paso como costoActual
                            int nuevoCosto = Math.max(costoActual, siguiente.getEtiqueta());
                            //Llamado recursivo con la siguiente habitacion, si no hay un camino todavía o el nuevo costo es mejor que el puntaje alcanzado anteriormente
                            if(puntajeMinimo[0] == -1 || nuevoCosto < puntajeMinimo[0]){
                                minimoPuntajeAux(siguiente.getVertice(), destino, visitados, cantVisitados, mejorCamino, puntajeMinimo, nuevoCosto);
                            }
                        }
                        siguiente = siguiente.getSigAdyacente();
                    }
                }
            }
            //Vacio la lista de visitados una vez que termina un camino
            visitados.eliminar(cantVisitados);
    }
    public Lista caminosSinPasarPor(Object origen, Object destino, Object aEvitar, int maxCosto) {
        Lista caminos = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);

        // Si el origen o el destino son justo la habitación prohibida, no buscamos
        if (vertOrigen != null && vertDestino != null && !origen.equals(aEvitar) && !destino.equals(aEvitar)) {
            Lista caminoActual = new Lista();
            caminosSinPasarPorAux(vertOrigen, destino, aEvitar, maxCosto, 0, caminoActual, 0, caminos);
        }
        return caminos;
    }

    private void caminosSinPasarPorAux(NodoVert actual, Object destino, Object aEvitar, int maxCosto, int costoAcum, Lista caminoActual, int caminoLong, Lista caminos) {
        caminoLong++;
        caminoActual.insertar(actual.getElem(), caminoLong);

        if (actual.getElem().equals(destino)) {
            // Como buscamos TODOS los caminos, guardamos una copia y dejamos que la recursión siga buscando
            caminos.insertar(caminoActual.clone(), caminos.longitud() + 1);
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                int pesoArco = ady.getEtiqueta();

                // Ignora si es el nodo prohibido, si ya fue visitado o si supera el costo
                if (!vecino.getElem().equals(aEvitar) && 
                    !pertenece(caminoActual, vecino.getElem()) && 
                    (costoAcum + pesoArco <= maxCosto)) {
                    
                    caminosSinPasarPorAux(vecino, destino, aEvitar, maxCosto, costoAcum + pesoArco, caminoActual, caminoLong, caminos);
                }
                ady = ady.getSigAdyacente();
            }
        }
        
        caminoActual.eliminar(caminoLong);
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
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);

        Lista[] caminoMax = new Lista[1];
        caminoMax[0] = new Lista();
        int[] maxNodosVisitados = {0};

        if (vertOrigen != null && vertDestino != null) {
            Lista caminoActual = new Lista();
            caminoMasLargoAux(vertOrigen, destino, caminoActual, caminoMax, 0, maxNodosVisitados);
        }
        return caminoMax[0];
    }

    private void caminoMasLargoAux(NodoVert actual, Object destino, Lista camino, Lista[] caminoMax, int nodosVisitados, int[] maxNodosVisitados) {
        if (!pertenece(camino, actual.getElem())) {
            nodosVisitados++;
            camino.insertar(actual.getElem(), nodosVisitados);

            if (actual.getElem().equals(destino)) {

                if (nodosVisitados > maxNodosVisitados[0]) {
                    maxNodosVisitados[0] = nodosVisitados;
                    caminoMax[0] = camino.clone();
                }
            } else {
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null) {
                    caminoMasLargoAux(ady.getVertice(), destino, camino, caminoMax, nodosVisitados, maxNodosVisitados);
                    ady = ady.getSigAdyacente();
                }
            }

            camino.eliminar(nodosVisitados);
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
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);

        Lista[] mejorCamino = new Lista[1];
        mejorCamino[0] = new Lista();

        if (vertOrigen != null && vertDestino != null) {
            Lista visitados = new Lista();
            int[] mejorCaminoLong = {0};
            caminoMasCortoAux(vertOrigen, destino, visitados, mejorCamino, 0, mejorCaminoLong);
        }
        return mejorCamino[0];
    }

    private void caminoMasCortoAux(NodoVert actual, Object destino, Lista visitados, Lista[] mejorCamino, int visitadosLong, int[] mejorCaminoLong) {
        visitadosLong++;
        visitados.insertar(actual.getElem(), visitadosLong);

        if (actual.getElem().equals(destino)) {
            if (mejorCaminoLong[0] == 0 || visitadosLong < mejorCaminoLong[0]) {
                mejorCamino[0] = visitados.clone();
                mejorCaminoLong[0] = visitadosLong;
            }
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                if (!pertenece(visitados, ady.getVertice().getElem())) {
                    // PODA: Solo profundiza si el camino actual aun puede ser mas corto que el mejor hallado
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

    private boolean eliminarArcoAux(NodoVert nodo, int etiqueta) {
        boolean logrado = false;
        if (nodo != null) {
            NodoAdy arcoActual = nodo.getPrimerAdy();
            NodoAdy arcoAnterior = nodo.getPrimerAdy();
            while (arcoActual != null && !logrado) {
                if (arcoActual.getEtiqueta() == etiqueta  && arcoActual == nodo.getPrimerAdy()) {
                    nodo.setPrimerAdy(arcoActual.getSigAdyacente());
                    logrado = true;
                } else if (arcoActual.getEtiqueta() == etiqueta && arcoActual != nodo.getPrimerAdy()) {
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
        NodoVert vertOrigen = ubicarVertice(desde); 
        
        if (vertOrigen != null) {
            // Buscamos en los adyacentes de origen
            NodoAdy ady = vertOrigen.getPrimerAdy();
            while (ady != null && !existe) {
                if (ady.getVertice().getElem().equals(hasta)) {
                    existe = true;
                }
                ady = ady.getSigAdyacente();
            }
        }
        return existe;
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
        NodoVert vertOrigen = ubicarVertice(desde);
        NodoVert vertDestino = ubicarVertice(hasta);

        Lista[] mejorCamino = new Lista[1];
        mejorCamino[0] = new Lista();

        if (vertOrigen != null && vertDestino != null) {
            Lista visitados = new Lista();
            int[] minPuntaje = { Integer.MAX_VALUE };
            // Le pasamos 0 como la longitud inicial de visitados
            caminoEtiquetaMinimaAux(vertOrigen, hasta, visitados, mejorCamino, 0, minPuntaje, 0); 
        }
        return mejorCamino[0];
    }

    private void caminoEtiquetaMinimaAux(NodoVert actual, Object destino, Lista visitados, Lista[] mejorCamino, int puntajeAcumulado, int[] minPuntaje, int longVisitados) {
        
        // Insertamos en la longitud actual + 1
        visitados.insertar(actual.getElem(), longVisitados + 1);

        if (actual.getElem().equals(destino)) {
            if (puntajeAcumulado < minPuntaje[0]) {
                minPuntaje[0] = puntajeAcumulado;
                mejorCamino[0] = visitados.clone();
            }
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                int pesoArco = ady.getEtiqueta();

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
            Lista copia = caminoActual.clone();
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
