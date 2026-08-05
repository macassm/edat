package TPFinal;

import Conjuntistas.ArbolAVL;
import java.util.HashMap;

public class Habitacion implements Comparable<Habitacion>{
    private int codigo;
    private String nombre;
    private int planta;
    private double metrosCuadrados;
    private boolean tieneSalida;
    private ArbolAVL desafios; 
    private HashMap <String, Integer> puntajeEquipos;

    public Habitacion(int codigo, String nombre, int planta, double metrosCuadrados, boolean tieneSalida){
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.tieneSalida = tieneSalida;
        this.desafios = new ArbolAVL();
        this.puntajeEquipos = new HashMap<>();
    }
    
    public Habitacion(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo(){
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlanta() {
        return planta;
    }
 
    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }
 
    public boolean isTieneSalida() {
        return tieneSalida;
    }
 
    public ArbolAVL getDesafios() {
        return desafios;
    }
    // No hay setCodigo(): es el atributo clave, no se puede modificar (pide el enunciado)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public void setPlanta(int planta) {
        this.planta = planta;
    }
 
    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }
 
    public void setTieneSalida(boolean tieneSalida) {
        this.tieneSalida = tieneSalida;
    }
 
    @Override
    public int compareTo(Habitacion otra) {
        return Integer.compare(this.codigo, otra.codigo);
    }
 
    @Override
    public String toString() {
            return "Habitacion " + codigo + " - Nombre: " + nombre + " - Planta: " + planta
            + " - Superficie: " + metrosCuadrados + "m2 - Tiene salida: " + tieneSalida;
    }

    @Override
    public boolean equals(Object otraHabitacion){
        boolean iguales = false;
        if (this == otraHabitacion){
            iguales = true;
        }else{
            if(otraHabitacion != null && otraHabitacion instanceof Habitacion){
                Habitacion otra = (Habitacion) otraHabitacion;
                iguales = this.getCodigo() == otra.getCodigo();
            }
        }
        return iguales;
    }
    @Override
    public int hashCode(){
        return Integer.hashCode(this.getCodigo());
    }

}   