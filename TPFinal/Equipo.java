package TPFinal;

public class Equipo {

    private String nombre;                     // clave en la tabla Hash de equipos (unico)
    private int puntajeExigido;                 // puntaje necesario para salir de la casa
    private int puntajeTotal;                   // acumulado en lo que va del juego
    private int codigoHabitacionActual;
    private int puntajeActualEnHabitacion;       // acumulado dentro de la habitacion donde esta ubicado
 

    public Equipo(String nombre, int puntajeExigido, int puntajeTotal,int codigoHabitacionActual, int puntajeActualEnHabitacion){
        this.nombre = nombre;
        this.puntajeExigido = puntajeExigido;
        this.puntajeTotal = puntajeTotal;
        this.codigoHabitacionActual = codigoHabitacionActual;
        this.puntajeActualEnHabitacion = puntajeActualEnHabitacion;
    }
    public Equipo(String nombre){
        this.nombre = nombre;
        this.puntajeExigido = 0;
        this.puntajeTotal = 0;
        this.codigoHabitacionActual = -1;
        this.puntajeActualEnHabitacion = 0;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public int getPuntajeExigido() {
        return puntajeExigido;
    }
 
    public int getPuntajeTotal() {
        return puntajeTotal;
    }
 
    public int getCodigoHabitacionActual() {
        return codigoHabitacionActual;
    }
 
    public int getPuntajeActualEnHabitacion() {
        return puntajeActualEnHabitacion;
    }
 
    // No hay setNombre(): es la clave, no se modifica
 
    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }
 
    public void setCodigoHabitacionActual(int codigoHabitacionActual) {
        this.codigoHabitacionActual = codigoHabitacionActual;
    }
 
    public void setPuntajeActualEnHabitacion(int puntajeActualEnHabitacion) {
        this.puntajeActualEnHabitacion = puntajeActualEnHabitacion;
    }
    public void setPuntajeExigido(int puntajeNuevo){
        this.puntajeExigido = puntajeNuevo;
    }
    @Override
    public String toString() {
        return "Equipo: " + nombre + " Puntaje exigido: " + puntajeExigido
                + " Puntaje total: " + puntajeTotal + " Habitacion actual:" + codigoHabitacionActual
                + " Puntaje en habitacion: " + puntajeActualEnHabitacion;
    }
    @Override
    public boolean equals(Object otroEquipo){
        boolean iguales = false;
        if(this == otroEquipo){
            iguales = true;
        }else{
            if(otroEquipo != null && otroEquipo instanceof Equipo){
                Equipo otro = (Equipo) otroEquipo;
                //al ser un string la clave, podría ingresar un nulo, se hace este paso para evitar errores por excepción
                if(this.nombre != null){
                    iguales = this.nombre.equals(otro.getNombre());
                }else{
                    iguales = otro.getNombre() == null;
                }
            }
        }
        return iguales;
    }

    @Override
    public int hashCode(){
        return (this.nombre != null) ? this.nombre.hashCode() : 0;
    }
}