package Lineales.Estaticas;
public class Pila {
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 20;

    public Pila(){
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }

    public boolean apilar (Object elem){
        
        boolean apilado = false;

        if(this.tope < TAMANIO-1){
            apilado = true;
            this.tope++;
            arreglo[this.tope] = elem;
        }

        return apilado;
    }

    public void vaciar(){
        this.tope = -1;
    }

    public Object obtenerTope(){
        Object resultado;
        if(!this.esVacia()){
            resultado = arreglo[this.tope];
        }else{
            resultado = null;
        }
        return resultado;
    }

    public boolean esVacia(){
        return this.tope == -1;
    }

    public boolean desapilar (){
        boolean desapilado = false;
        
        if (!this.esVacia()){
            this.arreglo[tope] = null;
            this.tope--;
            desapilado = true;
        }

        return desapilado;
    }

    public String toString(){
        String texto = "[";

        if(!this.esVacia()){
            for(int i = 0; i<=this.tope; i++){
                texto+= this.arreglo[i].toString()+" ";
            }
        }

        texto+= "]";

        return texto;
    }

    public Pila clone(){
        Pila auxiliar = new Pila();
        for(int i = 0; i<=this.tope; i++){
            //Consultar si se puede usar el apilar.
            auxiliar.apilar(this.arreglo[i]);
        } 
        auxiliar.tope = this.tope;

        return auxiliar;
    }

}