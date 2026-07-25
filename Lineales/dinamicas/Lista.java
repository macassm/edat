package Lineales.dinamicas;

public class Lista {
    private Nodo cabecera;
   
    public Lista(){
        this.cabecera = null;
    }

    public boolean insertar(Object elem, int pos){
        boolean exito = true;
       
        if(pos < 1 ){
            exito = false;
        }else{
            Nodo nuevo = new Nodo(elem, null);
            if(pos == 1){
                nuevo.setEnlace(this.cabecera);
                this.cabecera = nuevo;
            }else{
                int i = 1;
                Nodo auxiliar = this.cabecera;
                while(i < pos-1 && auxiliar != null){
                    auxiliar = auxiliar.getEnlace();
                    i++;
                }
                if(auxiliar == null){
                    exito = false;
                }else{
                    nuevo.setEnlace(auxiliar.getEnlace());
                    auxiliar.setEnlace(nuevo);
                }
            }

        }

        return exito;
    }

    public boolean eliminar(int pos){
        boolean eliminado = true;

        if(pos < 1){
            eliminado = false;
        }else if(this.cabecera == null){
            eliminado = false;
        }else{
            Nodo ayuda = new Nodo(null, null);
            if(pos == 1){
                ayuda = this.cabecera.getEnlace();
                this.cabecera = ayuda; 
                eliminado = true;
            }else{
                int i = 1;
                Nodo recorre = this.cabecera;
                while(i < pos-1 && recorre != null){
                    recorre = recorre.getEnlace();
                    i++;
                }
                if(recorre == null || recorre.getEnlace() == null){
                    eliminado = false;
                }else{
                    recorre.setEnlace(recorre.getEnlace().getEnlace());
                    eliminado = true;
                }
            }
        }
        return eliminado;
    }

        
    public Object recuperar(int pos){
        Object retorno = null;

        if(pos >= 1 && this.cabecera != null){
            int i = 1;
            Nodo recorre = this.cabecera;

            while(recorre != null && i < pos){
                recorre = recorre.getEnlace();
                i++;
            }

            if(recorre != null){
                retorno = recorre.getElem();
            }
        }  

        return retorno;
    }
    public int localizar(Object buscado){
        int i = 1;
        Nodo recorre = this.cabecera;
        int ubicacion = -1;
        while(recorre != null){
            if(recorre.getElem().equals(buscado)){
                ubicacion = i;
                recorre = null;
            }else{
                recorre = recorre.getEnlace();
                i++;
            }
        }
        return ubicacion;
    }
    public int longitud(){
        int largo = 0;
        Nodo medir = this.cabecera;
        while(medir != null){
            largo++;
            medir = medir.getEnlace();
        }
        return largo;
    }

    public void vaciar(){
        this.cabecera = null;
    }
    public boolean esVacia(){
        return this.cabecera == null;
    }

    public Lista clone(){
        Lista clon = new Lista();
        Nodo nodoAux, nuevoNodoAux, nuevoNodo;


        if(this.cabecera != null){
            nodoAux = this.cabecera;
            clon.cabecera = new Nodo(nodoAux.getElem(),null);
            nuevoNodoAux = clon.cabecera;
            while(nodoAux.getEnlace() != null){
                nodoAux = nodoAux.getEnlace();

                nuevoNodo = new Nodo(nodoAux.getElem(), null);
                nuevoNodoAux.setEnlace(nuevoNodo);
                nuevoNodoAux = nuevoNodoAux.getEnlace();
            }
        }        
        return clon;
    }

    public String toString(){
        String texto = "[ ";
        Nodo puntero = this.cabecera;
        while(puntero != null){
            if(puntero.getEnlace() != null){
                texto += puntero.getElem() + ", ";
                puntero = puntero.getEnlace();
            }else{
                texto += puntero.getElem();
                puntero = puntero.getEnlace();
            }
        }
        texto += " ]";
        return texto;
    }

public void invertir(){
        
        Nodo anterior = null;
        Nodo siguiente;
        Nodo actual = this.cabecera;

        while(actual != null){
            siguiente = actual.getEnlace();
            actual.setEnlace(anterior);
            anterior = actual;
            actual = siguiente;
        }
        this.cabecera = anterior;
    }

    //Ejercicio simulacro parcial 2019//


    public Lista obtenerMultiplos(int num){
        Lista aux = new Lista();
        int i = 1;
        Nodo nodoActual = this.cabecera;
        Nodo ultimo = null;

        while(nodoActual != null){
            if(i % num == 0){
                Nodo nuevo = new Nodo(nodoActual.getElem(), null);

                if(aux.cabecera == null){
                    // primer nodo
                    aux.cabecera = nuevo;
                }else{
                    ultimo.setEnlace(nuevo);
                }
                ultimo = nuevo;
            }
            nodoActual = nodoActual.getEnlace();
            i++;
        }
        return aux;
    }

    public void eliminarApariciones(Object elem){

        Nodo nodoActual = this.cabecera;
        Nodo anterior = null;

        while(nodoActual != null) {

            if(nodoActual.getElem().equals(elem)){
                if(anterior == null){
                    this.cabecera = nodoActual.getEnlace();
                }else{
                    anterior.setEnlace(nodoActual.getEnlace());
                }

                nodoActual = nodoActual.getEnlace();

            }else{
                anterior = nodoActual;
                nodoActual = nodoActual.getEnlace();
            }
        }
    }

    public void agregarElemento(Object nuevo, int x){

        if(this.cabecera != null){
            this.cabecera = new Nodo(nuevo, cabecera);

            Nodo actual = this.cabecera.getEnlace();
            int i = 1;

            while(actual != null){
                if(i % x == 0){
                    Nodo nuevoNodo = new Nodo(nuevo, actual.getEnlace());

                    actual.setEnlace(nuevoNodo);

                    actual = nuevoNodo.getEnlace();
                }else{
                    actual = actual.getEnlace();
                }
                i++;
            }

        }
    
    }

    public Lista intercalar(Lista l2){
        Lista retorno = new Lista();

        if(this.cabecera == null && l2.cabecera != null){
            retorno = clonacionIntercalar(l2);
        }else if(l2.cabecera == null && this.cabecera != null){
            retorno = clonacionIntercalar(this);
        }else if(l2.cabecera == null && this.cabecera == null){
            //Nada, vuelve vacia.
        }else{
            int i = 1;
            Nodo auxThis = this.cabecera;
            Nodo auxL2 = l2.cabecera;
            Nodo indice = null;

            while(auxThis != null || auxL2 != null){
                

                if(i % 2 != 0 && auxThis != null){
                    Nodo nuevo = new Nodo(auxThis.getElem(), null);
                    if(retorno.cabecera == null){
                        retorno.cabecera = nuevo;
                        indice = nuevo;
                    }else{
                        indice.setEnlace(nuevo);
                        indice = nuevo;
                    }
                    auxThis = auxThis.getEnlace();
                }else if(i % 2 == 0 && auxL2 != null){
                    Nodo nuevol2 = new Nodo(auxL2.getElem(), null);
                    indice.setEnlace(nuevol2);
                    indice = nuevol2;
                    auxL2 = auxL2.getEnlace();
                }else{
                    if(auxL2 == null && auxThis != null){ 
                        Nodo soloThis = new Nodo(auxThis.getElem(), null); 
                        indice.setEnlace(soloThis); 
                        indice = soloThis;
                        auxThis = auxThis.getEnlace(); 
                    }else if(auxL2 != null && auxThis == null){ 
                        Nodo soloL2 = new Nodo(auxL2.getElem(), null);
                        indice.setEnlace(soloL2); 
                        indice = soloL2;
                        auxL2 = auxL2.getEnlace(); 
                        
                    }
                }
                i++;
            }
            
        }
        return retorno;
    }

    private Lista clonacionIntercalar(Lista a){
        Lista clon = new Lista();
        Nodo nodoAux, nuevoNodoAux, nuevoNodo;


        if(a.cabecera != null){
            nodoAux = a.cabecera;
            clon.cabecera = new Nodo(nodoAux.getElem(),null);
            nuevoNodoAux = clon.cabecera;
            while(nodoAux.getEnlace() != null){
                nodoAux = nodoAux.getEnlace();

                nuevoNodo = new Nodo(nodoAux.getElem(), null);
                nuevoNodoAux.setEnlace(nuevoNodo);
                nuevoNodoAux = nuevoNodoAux.getEnlace();
            }
        }        
        return clon;
    }

    public boolean insertarAntesDe(Object elem1, Object elem2){
        boolean exito = false;
        Nodo auxCabecera = this.cabecera;
        Nodo anterior;
        Nodo nuevo;

        while(auxCabecera != null){
 
            if(this.cabecera.getElem().equals(elem2)){
                Nodo nuevaCabecera = new Nodo(elem1, auxCabecera);
                anterior = nuevaCabecera;
                this.cabecera = nuevaCabecera;
                auxCabecera = auxCabecera.getEnlace();
                exito = true;
            }else{
                anterior = auxCabecera;
                auxCabecera = auxCabecera.getEnlace();

                if(auxCabecera != null){
                    if(auxCabecera.getElem().equals(elem2)){
                        nuevo = new Nodo(elem1, null);
                        anterior.setEnlace(nuevo);
                        nuevo.setEnlace(auxCabecera);
                        exito = true;
                    }
                }
                
            }
        }

        return exito;
    }

    public boolean moverAAnteultimaPosicion(int pos){
        boolean exito = false;
        int i = 1;
        Nodo puntero = this.cabecera;
        Nodo anterior = null;
        Nodo auxiliar = null;
        boolean bandera = false; 

        if(pos < longitud()-1){
            while(i <= pos && !bandera){
                if(i == pos){
                    //En el primer nodo.
                    if(anterior == null){
                        auxiliar = puntero;
                        this.cabecera = puntero.getEnlace();
                    }else{
                        auxiliar = puntero;
                        anterior.setEnlace(puntero.getEnlace());
                    }
                    i++;
                    bandera = true;
                }else{
                    anterior = puntero;
                    puntero = puntero.getEnlace();
                    i++;
                }    
            }
        }

        if(bandera){
            Nodo ult = this.cabecera;
            Nodo anteUlt = null;

            while(ult.getEnlace() != null){
                anteUlt = ult;
                ult = ult.getEnlace();
            }


            if(anteUlt == null){
                auxiliar.setEnlace(ult);
                this.cabecera = auxiliar;
            }else{
                anteUlt.setEnlace(auxiliar);
                auxiliar.setEnlace(ult);
            }
            
            exito = true;
        }

        return exito;
    }
}