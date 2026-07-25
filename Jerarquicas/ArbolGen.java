package Jerarquicas;
import Lineales.dinamicas.*;

public class ArbolGen {
    
    
    private NodoGen raiz;

    public ArbolGen(){
        this.raiz = null;
    }

    public boolean insertar(Object elemento, Object padre){
        boolean insertado = true;

            //Si la raiz es nula el elemento se inserta ahi.
            if(this.raiz == null){
                this.raiz = new NodoGen(elemento,null,null);
            }else{

                //Caso contrario se busca al padre con el metodo obtenerNodoGen.
                NodoGen nPadre = obtenerNodoGen(this.raiz, padre);

                //Verifica que lo haya encontrado.
                if(nPadre != null){

                    //Crea el nodo con el elemento traido por parametro.
                    NodoGen nuevoHijo = new NodoGen(elemento, null, null);
                    
                    //Verifica si el hijo izquierdo esta vacio para hacer la insercion.
                    if(nPadre.getHijoIzquierdo() == null){
                        nPadre.setHijoIzquierdo(nuevoHijo);
                    }else{
                        //Sino utiliza un auxiliar para recorrer los hermanos 
                        // hasta encontrar el primer hermano nulo e insertarlo.
                        NodoGen aux = nPadre.getHijoIzquierdo();

                        while(aux.getHermanoDerecho() != null){
                            aux = aux.getHermanoDerecho();
                        }
                        aux.setHermanoDerecho(nuevoHijo);

                    }
                }else{
                    //Si no lo encuentra devuelve false.
                    insertado = false;
                }
            }

        return insertado;
    }

    private NodoGen obtenerNodoGen(NodoGen padre, Object elemPadre){
        NodoGen devolucion = null;
        
        //Si el padre (raiz) es distinto de null verifica si contiene a elemPadre.
        if(padre != null){
            //Si es igual lo devuelve.
            if(padre.getElem().equals(elemPadre)){
                devolucion = padre;
            }else{
                //Sino baja al hijo izquierdo.
                NodoGen aux = padre.getHijoIzquierdo();

                
                while(aux != null && devolucion == null){

                    //Sigue bajando hasta el ultimo hijo izquierdo y verifica
                    //en la recursion si el elemento esta ahi.
                    devolucion = obtenerNodoGen(aux, elemPadre);

                    //Recorre los hermanos derechos del nivel y de cada nivel en la recursion.
                    aux = aux.getHermanoDerecho();
                }
            }

        }
        
        return devolucion;
    }


    public boolean insertarPorPosicion(Object elem, int pos){
        //Se crea un arreglo para "recordar" la posicion.
        int[] arr = {1};
        boolean insertado;
        NodoGen nuevo= new NodoGen(elem,null,null);

        //Si la raiz es nula se inserta ahi, sin importar la posicion.
        if(this.raiz == null){
            this.raiz = nuevo;
            insertado = true;
        }else{
            //Llama recursivamente para recorrer el arbol.
            insertado = recorrerPorPosicion(elem,this.raiz,pos,arr);
        }
        return insertado;
    }

    private boolean recorrerPorPosicion(Object elem, NodoGen n, int pos, int[] arr){
        boolean hecho = false;


        //Verifica si el padre (raiz) es distinto de null.
        if(n != null){
            //Si el arreglo (posicion actual) es igual a la posicion se inserta el elemento.
            if(arr[0] == pos){
                NodoGen nuevoHijo = new NodoGen(elem, null, null);
                //Verifica si el hijo izquierdo esta libre.
                //Sino recorre hasta encontrar el primer hermano libre. 
                if(n.getHijoIzquierdo() == null){
                    n.setHijoIzquierdo(nuevoHijo);
                } else {
                    NodoGen aux = n.getHijoIzquierdo();
                    while(aux.getHermanoDerecho() != null){
                        aux = aux.getHermanoDerecho();
                    }
                    aux.setHermanoDerecho(nuevoHijo);
                }
                hecho = true;
            } else {
                //Caso contrario baja al hijo izquierdo.

                NodoGen hijo = n.getHijoIzquierdo();
                //Verifica que no sea null y utiliza bandera para recorrer lo necesario.
                while(!hecho && hijo != null){
                    //Aumenta la posicion.
                    arr[0]++;
                    //Llama recursivamente con el hijo y despues llama a los hermanos.
                    hecho = recorrerPorPosicion(elem, hijo, pos, arr);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }

        return hecho;
    }

    //Crea una lista y llama recursivamente para recorrer el arbol con un auxiliar.
    public Lista ancestros(Object elem){
        Lista ancestros = new Lista();
        int [] largoLis = {0};
        recorrerAncestros(ancestros,this.raiz,elem,largoLis);
        return ancestros;
    }

    private boolean recorrerAncestros(Lista ancestros, NodoGen n, Object elem, int[]largoLis){
        boolean encontrado = false;

        //Si el padre (raiz) es distinto de null verifica si el elemento es igual al del parametro.
        if(n != null){
            if(n.getElem().equals(elem)){
                encontrado = true;
            }
            //Si no lo encontro aumenta el largo de la lista inserta el elemento actual.
            if(!encontrado){
                largoLis[0]++;
                ancestros.insertar(n.getElem(), largoLis[0]);
                NodoGen hijo = n.getHijoIzquierdo();

                //Llama recursivamente a los hijos izquierdos de cada nivel.
                while(hijo != null && !encontrado){
                    //Recorre mientras no lo haya encontrado y el nivel tenga hermanos.
                    encontrado = recorrerAncestros(ancestros, hijo, elem, largoLis);
                    hijo = hijo.getHermanoDerecho();
                }

                //Si no lo encuentra finalmente vacia la lista.
                if(!encontrado){
                    ancestros.eliminar(largoLis[0]);
                    largoLis[0]--;
                }
            }
        }
        return encontrado;
    }

    //Utiliza un entero al que le asigna un metodo recursivo que retorna entero.
    public int altura(){
        int alt = recorrerAltura(this.raiz, -1);
        return alt;
    }

    
    private int recorrerAltura(NodoGen n, int altura){
        //Si el padre (raiz) es distinto de nulo, itera el entero y llama a su hijo izquierdo.
        if(n != null){
            altura++;
            NodoGen hijo = n.getHijoIzquierdo();
            //Crea una nueva variable al cual le asigna la altura actual.
            int alturaMayor = altura;

            //Recorre todos los hijos del nodo.
            while(hijo != null){
                int alturaLado = recorrerAltura(hijo, altura);
                hijo = hijo.getHermanoDerecho();
                //Por cada hijo, compara si la altura que devolvió ese camino
                // es mayor que alturaMayor, y si lo es, la actualiza.
                if(alturaMayor < alturaLado){
                    alturaMayor = alturaLado;
                }
            }
            //Si hay una alturaMayor mas grande que altura la actualiza para devolverla.
            if(altura < alturaMayor){
                altura = alturaMayor;
            }

        }
        return altura;
    }


    //Utiliza un entero al que le asigna un metodo recursivo que retorna entero.
    public int nivel(Object elem){
        int nivel = buscarNodoNivel(this.raiz,0, elem);
        return nivel;
    }

    private int buscarNodoNivel(NodoGen n, int nivel, Object elem){
        int nivelElem = -1;
        //Si el padre (raiz) es distinto de nulo verifica 
        //si tiene el elemento parametrizado.
        if(n != null){

            //Si es igual devuelve el nivel actual.
            if(n.getElem().equals(elem)){
                nivelElem = nivel;
            }else{
                //Sino lo itera y llama recursivamente a los hijos izquierdo.
                nivel++;
                NodoGen hijo = n.getHijoIzquierdo();
                //Mientras sea distinto de null los hermanos y el elemento no se haya encontrado
                //Llama recursivamente con el hijo izquierdo y con sus hermanos derechos del nodo actual.
                while(hijo != null && nivelElem == -1){
                    nivelElem = buscarNodoNivel(hijo, nivel, elem);
                    hijo = hijo.getHermanoDerecho();
                }
            }

        }

        return nivelElem;
    }


    //Es vacio si la raiz es nula.
    public boolean esVacio(){
        return this.raiz == null;
    }

    //Se vacia asignandole nula a la raiz haciendo que pierda toda referencia.
    public void vaciar(){
        this.raiz = null;
    }

    public boolean pertenece(Object elem){
        boolean verificar = false;

        //Si la raiz es distinta de nula llama recursivamente al metodo auxiliar.
        if(this.raiz != null){
            verificar = buscaElementos(this.raiz, elem);
        }

        return verificar;
    }

    private boolean buscaElementos(NodoGen padre, Object buscado){
        boolean ret = false;

        //Si el padre (raiz) es distinto de nulo compara si tiene al elemento parametrizado.
        if(padre != null){
            //Si cumple al retorno se le asigna true.
            if(padre.getElem().equals(buscado)){
                ret = true;
            }else{

                //Si no lo encontro llama recursivamente a los hijos izquierdos de cada nodo. 
                if(!ret){
                    NodoGen aux = padre.getHijoIzquierdo();
                    //Recorre los hermanos derechos mientras sean distintos de null 
                    //y no se haya encontrado el elemento.
                    while(aux != null && !ret){
                        ret = buscaElementos(aux, buscado);
                        aux = aux.getHermanoDerecho();

                    }
                }

            }
        }
        
        return ret;
    }

    //Le asigna al retorno el retorno de la devolucion del metodo auxiliar.
    public Object padre(Object elem){
        Object padre = buscarPadre(this.raiz,elem);
        return padre;
    }

    private Object buscarPadre(NodoGen n, Object buscado){
        Object padre = null;
        //Si el padre (raiz) es distinto de nulo baja al hijo y pregunta a cada uno 
        // si es distinto de null y si tiene el elemento, y lo devuelve.
        if(n != null){
            NodoGen hijo = n.getHijoIzquierdo();
            while(hijo != null && padre == null){

                if(hijo.getElem().equals(buscado)){
                    padre = n.getElem();
                }else{
                    padre = buscarPadre(hijo, buscado);
                }
                hijo = hijo.getHermanoDerecho();
            }
        }
        return padre;
    }
 

    public Lista listarPreorden(){
        Lista salida = new Lista();
        listarPreordenaux(this.raiz,salida);        
        return salida;
    }

    private void listarPreordenaux(NodoGen padre, Lista salida){

        //Si el padre (raiz) es distinto de nulo inserta el nodo actual
        //llama al izquierdo lo inserta y despues a sus hermanos.
        if(padre != null){
           salida.insertar(padre.getElem(), salida.longitud()+1);
           
           NodoGen hijo = padre.getHijoIzquierdo();

           while(hijo != null){
                listarPreordenaux(hijo, salida);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    public Lista listarInorden(){
        Lista salida = new Lista();
        listarInordenAux(this.raiz, salida);
        return salida;
    }

    private void listarInordenAux(NodoGen padre, Lista ls){
        //Si el padre (raiz) es distinto de nulo llama al izquierdo lo inserta, 
        // despues al padre y despues a sus hermanos.
        if(padre != null){
            if(padre.getHijoIzquierdo() != null){
                listarInordenAux(padre.getHijoIzquierdo(), ls);
            }

            ls.insertar(padre.getElem(), ls.longitud()+1);
            

            if(padre.getHijoIzquierdo() != null){
                NodoGen hijo = padre.getHijoIzquierdo().getHermanoDerecho();
                while(hijo != null){
                    listarInordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }   
        }
    }

    public Lista listarPosorden(){
        Lista salida = new Lista();
        listarPosordenAux(this.raiz, salida);
        return salida;
    }

    private void listarPosordenAux(NodoGen padre, Lista ls){
        //Si el padre (raiz) es distinto de nulo inserta el hijo izquierdo
        //despues llama a sus hermanos, los insertan y despues inserta a su padre.
        if(padre != null){
            
            if(padre.getHijoIzquierdo() != null){
                listarPosordenAux(padre.getHijoIzquierdo(), ls);
            }

            if(padre.getHijoIzquierdo() != null){
                NodoGen hijo = padre.getHijoIzquierdo().getHermanoDerecho();
                while(hijo != null){
                    listarPosordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }

            ls.insertar(padre.getElem(), ls.longitud()+1);
        }

    }

    public Lista listarPorNiveles(){
        Lista lista = new Lista();
        Cola cola = new Cola();
        NodoGen aux;

        //Si el padre (raiz) es distinto de nulo lo pone en la cola.
        if(this.raiz != null){
            cola.poner(this.raiz);
            //Mientras la cola no sea vacia.
            while(!cola.esVacia()){

                //Castea el frente y se lo asigna a un nodo auxiliar.
                aux = (NodoGen) cola.obtenerFrente();
                //Lo desencola y lo inserta.
                cola.sacar();
                lista.insertar(aux.getElem(), lista.longitud()+1);

                //Recorre los hermanos del hijo actual.
                NodoGen hijo = aux.getHijoIzquierdo();

                //Mientras los hermanos sean distintos de null, los pone en la cola.
                while(hijo != null){
                    cola.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return lista;
    }

   @Override
    public ArbolGen clone() {
        //Declara un nuevo arbol vacio y llama un metodo privado
        //auxiliar y a su raiz le asigna el retorno.
        ArbolGen clon = new ArbolGen();
        clon.raiz = recorrerClon(this.raiz);
        return clon;
    }

    private NodoGen recorrerClon(NodoGen nodoActual) {
        NodoGen nodo = null;
        //Si el padre (raiz) es distinto de nulo hace lo siguiente:

        /*Al nodo de retorno le pone el elemento del nodo actual, a sus le 
        asigna el llamado recursivo con el hijo izquierdo y el hijo derecho respectivamente
        permitiendo que se enlacen recursivamente */
        if (nodoActual != null) {
            nodo = new NodoGen(nodoActual.getElem(),
                    recorrerClon(nodoActual.getHijoIzquierdo()),
                    recorrerClon(nodoActual.getHermanoDerecho()));
        }
        return nodo;
    }

    public String toString(){
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen padre){
        String s = "";
        //Si el padre (raiz) es distinto de nulo al elemento lo ingresa en el texto y a sus hijos los pondra delante del ':'.
        if(padre != null){

            s+= padre.getElem().toString() + ": ";
            //Recorre los hijos mientras sean distintos de null y los agreaga delante del ':' separados con coma.
            NodoGen hijo = padre.getHijoIzquierdo();
            while(hijo != null){
                s+= hijo.getElem().toString()+ ", ";
                hijo = hijo.getHermanoDerecho();
            }

            //Luego llama al hijo izquierdo del nodo actual
            hijo = padre.getHijoIzquierdo();
            //Si es null realiza un salto de linea con \n y le concatena todos sus hijos 
            // con el llamado recursivo y luego llama a sus hermanos.
            while(hijo != null){
                s+= "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return s;
    }


    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////Practica para parcial///////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    

    public boolean verificarCamino(Lista ls){
        boolean verificado = false;
        int i = 1;
        if(this.raiz != null){
            verificado = verificarCaminoAux(this.raiz,ls,i);
        }
        return verificado;
    }

    private boolean verificarCaminoAux(NodoGen n, Lista ls, int i){

        boolean ret = false;
        if(n != null){
            if(n.getElem().equals(ls.recuperar(i))){
                if(i == ls.longitud()){
                    ret = true;
                }else{
                    NodoGen hijo = n.getHijoIzquierdo();

                    while(hijo != null && !ret){
                        ret = verificarCaminoAux(hijo, ls, i+1);
                        hijo = hijo.getHermanoDerecho();
                    }
                }
            }
        }
        return ret;
    }



    public Lista listarEntreNiveles(int i1, int i2){
        Lista ls = new Lista();
        if(this.raiz != null && i1 >= 0 && i2 >= 0){

            if(i1 >= i2){
                listarEntreNivelesAux(this.raiz,ls,i2, i1, 0);
            }else{
                listarEntreNivelesAux(this.raiz,ls,i1, i2, 0);
            }
        }
        return ls;
    }

    private void listarEntreNivelesAux(NodoGen n, Lista ls, int min, int max,int nivel){
        if(n != null && nivel <= max){

            if(nivel >= min){
                ls.insertar(n.getElem(), ls.longitud()+1);
            }

            NodoGen hijo = n.getHijoIzquierdo();

            while(hijo != null){
                listarEntreNivelesAux(hijo, ls, min, max, nivel+1);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    public boolean jerarquizar(Object unElem){
        boolean exito = false;
        if(this.raiz != null){
            exito = AuxJerarquizar(this.raiz,unElem);
        }
        return exito;
    }

    private boolean AuxJerarquizar(NodoGen n, Object buscado){
        boolean logrado = false;
        if(!this.raiz.getElem().equals(buscado)){
            

            NodoGen aux = n.getHijoIzquierdo();
            NodoGen hermanoIzq = null;

            while(aux != null && !logrado){
                if(aux.getElem().equals(buscado)){
                    NodoGen hermanoPadre = n.getHermanoDerecho();
                    n.setHermanoDerecho(aux);
                    logrado = true;
                    if(hermanoIzq != null){
                        hermanoIzq.setHermanoDerecho(aux.getHermanoDerecho());                        
                    }else{
                        n.setHijoIzquierdo(aux.getHermanoDerecho());
                    }
                    aux.setHermanoDerecho(hermanoPadre);
                }else{
                    hermanoIzq = aux;
                    aux = aux.getHermanoDerecho();
                }
            }

            if(!logrado){
                aux = n.getHijoIzquierdo();
                while(aux != null && !logrado){
                    logrado = AuxJerarquizar(aux, buscado);
                    aux = aux.getHermanoDerecho();
                }
                
            }
            

        }
        return logrado;
    }

    public Lista listarHastaNivel(int nivel){
        Lista ls = new Lista();
        if(this.raiz != null){
            auxListarHastaNivel(this.raiz,ls,0, nivel);
        }
        return ls;
    }

    private void auxListarHastaNivel(NodoGen n, Lista ls, int nivActual, int nivel){

        if(nivActual <= nivel){
            if(n != null){
                ls.insertar(n.getElem(), ls.longitud()+1);
                NodoGen aux = n.getHijoIzquierdo();
                while(aux != null){
                    auxListarHastaNivel(aux, ls, nivActual+1, nivel);
                    aux = aux.getHermanoDerecho();
                }
            }
        } 
    }

    public boolean eliminar(Object unElem){
        boolean realizado = false;
        if(this.raiz != null){
            if(this.raiz.getElem().equals(unElem)){
                this.raiz = null;
                realizado = true;
            }else{
                realizado = auxEliminar(this.raiz, unElem); 
            }
        }
        return realizado;
    }

    private boolean auxEliminar(NodoGen n,Object unElem){
        boolean retorno = false;
        NodoGen anterior = null;
        NodoGen actual = n.getHijoIzquierdo();
        while(actual != null && !retorno){

            if(actual.getElem().equals(unElem)){
                if(anterior == null){
                    n.setHijoIzquierdo(actual.getHermanoDerecho());
                }else{
                    anterior.setHermanoDerecho(actual.getHermanoDerecho());
                }
                retorno = true;
            }else{
                anterior = actual;
                actual = actual.getHermanoDerecho();
            }    
        }

        if(!retorno){
            actual = n.getHijoIzquierdo();
            while(actual != null && !retorno){
                retorno = auxEliminar(actual, unElem);
                actual = actual.getHermanoDerecho();
            }

        }
        return retorno;
    } 

}