package Lineales.dinamicas;

public class Alumno {
    private String legajo;
    private String nombre, apellido;


    public Alumno(String unLegajo){
        this.legajo = unLegajo;
    }

    public Alumno(String unLegajo, String unNombre, String unApellido){
        legajo = unLegajo;
        nombre = unNombre;
        apellido = unApellido;
    }

    public String toString() {
    return "Alumno{" +
            "" + legajo  + " " + nombre  +
            " " + apellido + '}';
    }
    
}
