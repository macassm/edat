package Lineales.testLineales;
import Lineales.dinamicas.Cola;
public class testCola {
    public static void main(String[] args) {
        Cola miCola = new Cola();

        miCola.poner(1);
        miCola.poner(5);
        miCola.poner(4);
        miCola.poner(3);
        miCola.poner(2);

        System.out.println(miCola);
        miCola.sacar();

        System.out.println(miCola);
        int elfrente = (Integer)miCola.obtenerFrente();
        System.out.println(elfrente);
        miCola.vaciar();
        System.out.println(miCola);
        miCola.poner(1);
        miCola.poner(1000);
        miCola.poner(10);
        miCola.poner(100);
        miCola.poner(1);

        System.out.println(miCola.clone());


  
    }
}
