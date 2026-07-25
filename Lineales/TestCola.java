package Lineales;

import Lineales.Estaticas.Cola;
import Lineales.Estaticas.Fecha;

public class TestCola {
    public static void main(String[] args) {
        
        Cola unaCola = new Cola();
        Cola otraCola = new Cola();
        Fecha fecha1 = new Fecha(27, "Junio", 2006);
        Fecha fecha2 = new Fecha(25, "Octubre", 1970);
        Fecha fecha3 = new Fecha(13, "Enero", 1973);
        Fecha fecha4 = new Fecha(3, "Julio", 2001);

        unaCola.poner(fecha1);
        unaCola.poner(fecha2);
        unaCola.poner(fecha3);
        unaCola.poner(fecha4);

        System.out.println(unaCola);
        otraCola = unaCola.clone();

        boolean sacado = unaCola.sacar();
        System.out.println(sacado);

        System.out.println(unaCola);

        Fecha frente = (Fecha) unaCola.obtenerFrente();
        System.out.println(frente);

        System.out.println(otraCola);

        boolean vacio = unaCola.esVacia();
        System.out.println(vacio);
        
        unaCola.Vaciar();
        vacio = unaCola.esVacia();
        System.out.println(vacio);



    }
}
