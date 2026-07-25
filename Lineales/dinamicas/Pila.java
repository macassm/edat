package Lineales.dinamicas;

public class Pila {
    private Nodo tope;

    public Pila(){
        this.tope = null;
    }

    public boolean apilar(Object nuevoElem){

        //Crear nuevo nodo delante de la antigua cabecera
        Nodo nuevo = new Nodo(nuevoElem, this.tope);

        //Actualizar tope que apunte a nodo nuevo.
        this.tope = nuevo;

        //Nunca error de pila llena, devuelve true
        return true;
    }

    public boolean desapilar(){
        boolean desapilado;

        if(this.tope == null){
            desapilado = false;
        }else{
            this.tope = this.tope.getEnlace();
            desapilado = true;
        }

        return desapilado;
    }

    public boolean esVacia(){
        return this.tope == null;
    }

    public void vaciar(){
        this.tope = null;        
    }

    public Pila clone(){
        Pila clon = new Pila();
        Pila auxPila = new Pila();

        Nodo aux = this.tope;
        while(aux != null){
            auxPila.apilar(aux.getElem());
            aux = aux.getEnlace();
        }

        while(!auxPila.esVacia()){
            clon.apilar(auxPila.obtenerTope());
            auxPila.desapilar();
        }
        return clon;
    }

    public Object obtenerTope(){
        Object devolver;
        if(this.tope!=null){
            devolver = this.tope.getElem();
        }else{
            devolver = null;
        }
        return devolver; 
    }

    public String toString(){
        String s = "";

        if(this.tope == null){
            s = "Pila vacia";
        }else{
            //Se ubica para recorrer la pila.
            Nodo aux = this.tope;
            s = "[ ";

            while(aux != null){
                //Agrega el texto del elem y avanza

                s += aux.getElem().toString();
                aux = aux.getEnlace();
                if(aux  != null){
                    s += " , ";
                } 
            }
            s += "]";
        }


        return s;
    }




}
