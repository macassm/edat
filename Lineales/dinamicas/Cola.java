package Lineales.dinamicas;

public class Cola {
    private Nodo frente;
    private Nodo fin;

    public Cola(){
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elem){
        Nodo nuevo = new Nodo(elem, null);

        if(this.fin == null){
            frente = nuevo;
        }else{
            fin.setEnlace(nuevo);
        }
        fin = nuevo;
        return true;
    }

    public boolean sacar(){
        boolean sacado;

        if(this.fin == null){
            sacado = false;
        }else{
            frente = this.frente.getEnlace();
            if(this.frente == null){
                this.fin = null;
            }

            sacado = true;
        }

        return sacado;
    }

    public boolean esVacia(){
        return this.fin == null;
    }

    public Object obtenerFrente(){
        Object resultado;
        if(this.fin != null){
            resultado = this.frente.getElem();
        }else{
            resultado = null;
        }
        
        return resultado;
    }

    public void vaciar(){
        frente = null;
        fin = null;
    }

    public Cola clone(){
        Cola clon = new Cola();
        Nodo aux = frente;

        while(aux != null){
            clon.poner(aux.getElem());
            aux = aux.getEnlace();
        }
        return clon;
    }

    public String toString(){
        String mensaje = "[ ";
        Nodo iterador = this.frente;

        if(this.fin != null){
            while(iterador != null){
                mensaje += iterador.getElem().toString() +", ";
                iterador = iterador.getEnlace();
            }
            mensaje += " ]";
        }else{
            mensaje = "Cola vacia";
        }
        return mensaje;
    }
}
