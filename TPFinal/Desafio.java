package TPFinal;

public class Desafio implements Comparable<Desafio>{
    
    private int puntaje;               // clave dentro del AVL de desafios de SU habitacion
    private int codigoHabitacion;      // a que habitacion pertenece
    private String nombre;
    private String tipo;               // logico, matematico, busqueda, destreza, ingenio, etc
 
    // Constructor 
    public Desafio(int puntaje, int codigoHabitacion, String nombre, String tipo) {
        this.puntaje = puntaje;
        this.codigoHabitacion = codigoHabitacion;
        this.nombre = nombre;
        this.tipo = tipo;
    }
 
    // Constructor "liviano": solo la clave, para buscar/eliminar en el AVL de una habitacion
    public Desafio(int puntaje) {
        this.puntaje = puntaje;
    }
    public Desafio(int puntaje, int codigoHabitacion){
        this.puntaje = puntaje;
        this.codigoHabitacion = codigoHabitacion;
    }
    public int getPuntaje() {
        return puntaje;
    }
 
    public int getCodigoHabitacion() {
        return codigoHabitacion;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public String getTipo() {
        return tipo;
    }
 
    // No hay setPuntaje() ni setCodigoHabitacion(): son parte de la clave, no se modifican
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
 
    @Override
    public boolean equals(Object otroDesafio){
        boolean iguales = false;
        if(this == otroDesafio){
            iguales = true;
        } else{
            if(otroDesafio != null && otroDesafio instanceof Desafio){
                Desafio otro = (Desafio) otroDesafio;
                iguales = this.puntaje == otro.puntaje && this.codigoHabitacion == otro.codigoHabitacion;
            }
        }
        return iguales;
    }
   @Override
    public int compareTo(Desafio otro) {
        // Comparamos si los puntajes son iguales
        int resultado = Integer.compare(this.puntaje, otro.puntaje);
        
        // Si el puntaje es exactamente igual, desempatamos por habitación
        if (resultado == 0) {
            resultado = Integer.compare(this.codigoHabitacion, otro.codigoHabitacion);
        }
        
        return resultado;
    }
 
    @Override
    public String toString() {
        return "Desafio: "+ nombre +  " Puntaje:" + puntaje + " Habitacion:" + codigoHabitacion
                + " Tipo: " + tipo;
    }
    @Override
    public int hashCode(){
        return Integer.hashCode(this.puntaje);
    }
} 