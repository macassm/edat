package Lineales.testLineales;
import Lineales.dinamicas.*;


public class testPila {
    public static void main(String[] args) {
            
      //  Pila pila = new Pila();
        Pila alumnos = new Pila();

        Alumno a1 = new Alumno("Fai 5025", "leo", "messi");
        Alumno a2 = new Alumno("Fai 4428", "fran", "alonso");
        Alumno a3 = new Alumno("Fai 3343", "abril", "landro");

        alumnos.apilar(a1);
        alumnos.apilar(a2);
        alumnos.apilar(a3);



        String hola = alumnos.toString();

        System.out.println(hola);

        Pila clon = alumnos.clone();

        String hola2 = clon.toString();

        System.out.println(hola2);

        alumnos.vaciar();

        String hola3 = alumnos.toString();

        System.out.println(hola3);

        /* boolean desapiladof = alumnos.desapilar();
        System.out.println(desapiladof);
        Alumno valor = (Alumno)alumnos.obtenerTope();
        System.out.println(valor);
        System.out.println(capicua(alumnos));*/

        
    }

    public static boolean capicua(Pila palabras){
        boolean verifica = true;
        Pila inverso = new Pila();
        Pila pCopia = palabras.clone();
    
        while(!pCopia.esVacia()){
            inverso.apilar(pCopia.obtenerTope());
            pCopia.desapilar();
        }

        pCopia = palabras.clone();

        while(!pCopia.esVacia() && verifica){
            if(pCopia.obtenerTope().equals(inverso.obtenerTope())){
                inverso.desapilar();
                pCopia.desapilar();
            }else{
                verifica = false;
            }
        }


        return verifica;
    }
}