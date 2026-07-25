package Lineales.Estaticas;

public class Cola {
    private Object [] arreglo;
    private static final int largo = 20;
    private int fin;
    private int frente;


    public Cola(){
        this.arreglo = new Object[largo];
        this.fin = 0; //fin apunta siempre al siguiente elemento
        this.frente = 0;
    }

    public boolean poner(Object unElem){
        boolean colocado = true;
        //Verifica que la cola no este llena inluyendo, 
        // si fin esta en la ultima posicion y frente en la 0
        //  utilizando mod largo
        if( (this.fin +1) % largo != this.frente){
            this.arreglo[this.fin] = unElem;
            this.fin = (this.fin + 1) % largo;
        }else{
            colocado = false;
        }

        return colocado;
    }

    public boolean sacar(){
        boolean exito = true;

        if(!esVacia()){
            this.arreglo[this.frente] = null;
            this.frente = (this.frente+1) % largo;
        }else{
            exito = false;
        }

        return exito;
    }


    public boolean esVacia(){
        //vacia si el frente y el fin apuntan al mismo lugar
        return this.frente == this.fin;
    }

    public Object obtenerFrente(){
        return this.arreglo[frente];
    }

    public void Vaciar(){
        while(this.frente != this.fin){
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % largo;
        }
        this.frente = 0;
        this.fin = 0;
                
    }

    public Cola clone(){
        Cola clon = new Cola();
        int repuestoFrente = this.frente;

        while(repuestoFrente != this.fin){
            clon.poner(this.arreglo[repuestoFrente]);
            repuestoFrente = (repuestoFrente+1) % largo;
        }

        return clon;
    }

    public String toString(){
        String texto = "[ ";

        for(int i = 0; i < largo; i++){
            if(this.arreglo[i] != null){
                texto += this.arreglo[i] + ", ";
            }
        }

        texto += " ]";
        
        return texto;
    }
}
