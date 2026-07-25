package Jerarquicas;

import static org.junit.Assert.*;
import org.junit.Test;
import Lineales.dinamicas.*;

public class testArbolGenericoGrupo {

    //ESVACIO

    @Test
    public void testEsVacioArbolVacio() {
        ArbolGen a = new ArbolGen();
        assertTrue(a.esVacio());
    }

    @Test
    public void testEsVacioArbolConUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar("Hola", null);
        assertFalse(a.esVacio());
    }

    @Test
    public void testEsVacioArbolConVariosNodos() {
        ArbolGen a = new ArbolGen();
        a.insertar("River", null);
        a.insertar("Boca", 1);
        a.insertar("Racing", 1);
        assertFalse(a.esVacio());
    }

    //INSERTAR 

    @Test
    public void testInsertarEnArbolVacio() {
        ArbolGen a = new ArbolGen();
        boolean i = a.insertar(1, null);
        assertTrue(i);
        assertFalse(a.esVacio());
    }

    @Test
    public void testInsertarPrimerHijo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        boolean i = a.insertar(2, 1);
        assertTrue(i);
    }

    @Test
    public void testInsertarSegundoHijo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        boolean i = a.insertar(3, 1);
        assertTrue(i);
    }

    @Test
    public void testInsertarHijoDeHijo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        boolean i = a.insertar(4, 2);
        assertTrue(i);
    }

    @Test
    public void testInsertarPadreInexistente() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        boolean i = a.insertar(4, 99);
        assertFalse(i);
    }

    @Test
    public void testInsertarEnArbolVacioPadreNoNull() {
        ArbolGen a = new ArbolGen();
        boolean i = a.insertar(1, 5);
        assertFalse(i);
    }

    //INSERTAR POR POSICION 

    @Test
    public void testInsertarPorPosicionArbolVacioPosicion0() {
        ArbolGen a = new ArbolGen();
        boolean i = a.insertarPorPosicion(1, 0);
        assertTrue(i);
        assertFalse(a.esVacio());
    }

    @Test
    public void testInsertarPorPosicionArbolVacioPosicion1() {
        ArbolGen a = new ArbolGen();
        boolean i = a.insertarPorPosicion(1, 1);
        assertTrue(i);
        assertFalse(a.esVacio());
    }

    @Test
    public void testInsertarPorPosicionArbolVacioPosicion1000() {
        ArbolGen a = new ArbolGen();
        boolean i = a.insertarPorPosicion(1, 1000);
        assertTrue(i);
        assertFalse(a.esVacio());
    }

    @Test
    public void testInsertarPorPosicionEnPosicion1() {
        ArbolGen a = new ArbolGen();
        a.insertarPorPosicion(1, 1);
        boolean i = a.insertarPorPosicion(2, 1);
        assertTrue(i);
    }

    @Test
    public void testInsertarPorPosicionEnPosicion2() {
        ArbolGen a = new ArbolGen();
        a.insertarPorPosicion(1, 1);
        a.insertarPorPosicion(2, 1);
        boolean i = a.insertarPorPosicion(3, 2);
        assertTrue(i);
    }

    @Test
    public void testInsertarPorPosicionPosicionInexistente() {
        ArbolGen a = new ArbolGen();
        a.insertarPorPosicion(1, 1);
        boolean i = a.insertarPorPosicion(2, 99);
        assertFalse(i);
    }

    //PERTENECE 

    @Test
    public void testPerteneceEnArbolVacio() {
        ArbolGen a = new ArbolGen();
        assertFalse(a.pertenece(1));
    }

    @Test
    public void testPerteneceRaiz() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertTrue(a.pertenece(1));
    }

    @Test
    public void testPerteneceHoja() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        assertTrue(a.pertenece(2));
    }

    @Test
    public void testPerteneceNodoInterno() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 2);
        assertTrue(a.pertenece(2));
    }

    @Test
    public void testPerteneceElementoNoEstaUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertFalse(a.pertenece(99));
    }

    @Test
    public void testPerteneceElementoNoEstaVariosNodos() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        assertFalse(a.pertenece(99));
    }

    //VACIAR 

    @Test
    public void testVaciarArbolVacio() {
        ArbolGen a = new ArbolGen();
        a.vaciar();
        assertTrue(a.esVacio());
    }

    @Test
    public void testVaciarArbolUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.vaciar();
        assertTrue(a.esVacio());
    }

    @Test
    public void testVaciarArbolVariosNodos() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        a.vaciar();
        assertTrue(a.esVacio());
    }

    //PADRE

    @Test
    public void testPadreEnArbolVacio() {
        ArbolGen a = new ArbolGen();
        assertNull(a.padre(1));
    }

    @Test
    public void testPadreDeLaRaiz() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertNull(a.padre(1));
    }

    @Test
    public void testPadreExisteUnNivel() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        assertEquals(1, a.padre(2));
    }

    @Test
    public void testPadreExisteDosNiveles() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 2);
        assertEquals(2, a.padre(3));
    }

    @Test
    public void testPadreElementoNoExiste() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertNull(a.padre(99));
    }

    @Test
    public void testPadreMultiplesOcurrencias() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(3, 1);
        a.insertar(2, 1);
        a.insertar(4, 1);
        a.insertar(2, 4);
        assertEquals(4, a.padre(2));
    }

    //ALTURA

    @Test
    public void testAlturaArbolVacio() {
        ArbolGen a = new ArbolGen();
        assertEquals(-1, a.altura());
    }

    @Test
    public void testAlturaUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertEquals(0, a.altura());
    }

    @Test
    public void testAlturaDosniveles() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        assertEquals(1, a.altura());
    }

    @Test
    public void testAlturaTresNiveles() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 2);
        assertEquals(2, a.altura());
    }

    @Test
    public void testAlturaRamasMasLarga() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        a.insertar(4, 1);
        a.insertar(5, 3);
        assertEquals(2, a.altura());
    }

    //NIVEL

    @Test
    public void testNivelArbolVacio() {
        ArbolGen a = new ArbolGen();
        assertEquals(-1, a.nivel(1));
    }

    @Test
    public void testNivelRaiz() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertEquals(0, a.nivel(1));
    }

    @Test
    public void testNivelHijo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        assertEquals(1, a.nivel(2));
    }

    @Test
    public void testNivelNieto() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 2);
        assertEquals(2, a.nivel(3));
    }

    @Test
    public void testNivelElementoNoEstaUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        assertEquals(-1, a.nivel(99));
    }

    @Test
    public void testNivelElementoNoEstaVariosNodos() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        assertEquals(-1, a.nivel(99));
    }

    //ANCESTROS

    @Test
    public void testAncestrosArbolVacio() {
        ArbolGen a = new ArbolGen();
        Lista l = a.ancestros(1);
        assertTrue(l.esVacia());
    }

    @Test
    public void testAncestrosRaiz() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        Lista l = a.ancestros(1);
        assertTrue(l.esVacia());
    }

    @Test
    public void testAncestrosElementoNoExisteUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        Lista l = a.ancestros(99);
        assertTrue(l.esVacia());
    }

    @Test
    public void testAncestrosElementoNoExisteVariosNodos() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        Lista l = a.ancestros(99);
        assertTrue(l.esVacia());
    }

    @Test
    public void testAncestrosHoja() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 2);
        Lista l = a.ancestros(3);
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
        assertEquals(2, l.recuperar(2));
    }

    @Test
    public void testAncestrosNodoInterno() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 2);
        Lista l = a.ancestros(2);
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
    }

    @Test
    public void testAncestrosMultiplesNiveles() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(4, 1);
        a.insertar(3, 1);
        a.insertar(2, 1);
        a.insertar(5, 3);
        Lista l = a.ancestros(5);
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
        assertEquals(3, l.recuperar(2));
    }

    //CLONE

    @Test
    public void testCloneArbolVacio() {
        ArbolGen a = new ArbolGen();
        ArbolGen c = a.clone();
        assertTrue(c.esVacio());
    }

    @Test
    public void testCloneArbolUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        ArbolGen c = a.clone();
        assertFalse(c.esVacio());
        assertTrue(c.pertenece(1));
    }

    @Test
    public void testCloneArbolVariosNodos() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        ArbolGen c = a.clone();
        assertFalse(c.esVacio());
        assertTrue(c.pertenece(1));
        assertTrue(c.pertenece(2));
        assertTrue(c.pertenece(3));
    }

    @Test
    public void testCloneEsIndependiente() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        ArbolGen c = a.clone();
        a.vaciar();
        assertFalse(c.esVacio());
    }

    //LISTAR PREORDEN

    @Test
    public void testPreordenArbolVacio() {
        ArbolGen a = new ArbolGen();
        Lista l = a.listarPreorden();
        assertTrue(l.esVacia());
    }

    @Test
    public void testPreordenUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        Lista l = a.listarPreorden();
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
    }

    @Test
    public void testPreordenVariosNodos() {
        ArbolGen a = new ArbolGen();
        
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        a.insertar(4, 2);
        Lista l = a.listarPreorden();
        // Preorden: 1, 2, 4, 3
        assertEquals(1, l.recuperar(1));
        assertEquals(2, l.recuperar(2));
        assertEquals(4, l.recuperar(3));
        assertEquals(3, l.recuperar(4));
    }

    //LISTAR POSORDEN

    @Test
    public void testPosordenArbolVacio() {
        ArbolGen a = new ArbolGen();
        Lista l = a.listarPosorden();
        assertTrue(l.esVacia());
    }

    @Test
    public void testPosordenUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        Lista l = a.listarPosorden();
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
    }

    @Test
    public void testPosordenVariosNodos() {
        ArbolGen a = new ArbolGen();
       
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        a.insertar(4, 2);
        Lista l = a.listarPosorden();
        // Posorden: 4, 2, 3, 1
        assertEquals(4, l.recuperar(1));
        assertEquals(2, l.recuperar(2));
        assertEquals(3, l.recuperar(3));
        assertEquals(1, l.recuperar(4));
    }

    //LISTAR INORDEN

    @Test
    public void testInordenArbolVacio() {
        ArbolGen a = new ArbolGen();
        Lista l = a.listarInorden();
        assertTrue(l.esVacia());
    }

    @Test
    public void testInordenUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        Lista l = a.listarInorden();
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
    }

    @Test
    public void testInordenVariosNodos() {
        ArbolGen a = new ArbolGen();
        
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        Lista l = a.listarInorden();
        // Inorden: primer hijo (2), padre (1), resto de hijos (3)
        assertFalse(l.esVacia());
        assertEquals(2, l.recuperar(1));
        assertEquals(1, l.recuperar(2));
        assertEquals(3, l.recuperar(3));
    }

    //LISTAR POR NIVELES

    @Test
    public void testPorNivelesArbolVacio() {
        ArbolGen a = new ArbolGen();
        Lista l = a.listarPorNiveles();
        assertTrue(l.esVacia());
    }

    @Test
    public void testPorNivelesUnNodo() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        Lista l = a.listarPorNiveles();
        assertFalse(l.esVacia());
        assertEquals(1, l.recuperar(1));
    }

    @Test
    public void testPorNivelesVariosNodos() {
        ArbolGen a = new ArbolGen();
      
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        a.insertar(4, 2);
        Lista l = a.listarPorNiveles();
        // Nivel 0: 1, nivel 1: 2,3, nivel 2: 4
        assertEquals(1, l.recuperar(1));
        assertEquals(2, l.recuperar(2));
        assertEquals(3, l.recuperar(3));
        assertEquals(4, l.recuperar(4));
    }
}
