package Jerarquicas;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import Lineales.dinamicas.*;
import Jerarquicas.*;

/**
 *
 * @author Catedra EDAT - FAI - UNCOMA
 *         Ultima modificación: 10/04/2025
 *
 */

/*
  * Se asume que la salida de toString() para Arbol devuelve 
  * un texto que incluye la subcadena del tipo raiz:hijo1,hijo2,...,hijon  y en la siguiente linea, los hijos del hijo1, y así.
  * El texto de salida del toString() puede contener cualquier otro texto antes o despues de la subcadena anterior.
*/

public class testArbolGen {

    private static boolean isSubstring(String s, String rx){
        Pattern pattern = Pattern.compile(rx);
        Matcher matcher = pattern.matcher(s);
        boolean findSubstring = false;
        while (matcher.find()) findSubstring = true;
        return findSubstring;
    }


    @Test
    public void testCreateEmptyGenericTree() {
        ArbolGen a = new ArbolGen();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertTrue(ev);
        String rx="";
        assertEquals(s.matches(rx),true);
    };

    @Test
    public void testAddElementInEmptyGenericTree() {
        ArbolGen a = new ArbolGen();
        boolean i = a.insertar(1, null);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementAsFirstChild() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        boolean ih = a.insertar(2, 1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(ih);
        String rx="^1:2\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementInGenericTreeAsChildOfASecondChild() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        a.insertar(3, 1);
        boolean i = a.insertar(4, 2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:3,2\r?\n3:\r?\n2:4\r?\n4:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementInGenericTreeAsChildOfAnInexistentFather() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        boolean i = a.insertar(4, 2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertFalse(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementInEmtpyTreePositionGiven1() {
        ArbolGen a = new ArbolGen();
        boolean i =a.insertarPorPosicion(1, 1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };
   @Test
    public void testAddElementInEmtpyTreePositionGiven0() {
        ArbolGen a = new ArbolGen();
        boolean i =a.insertarPorPosicion(1, 0);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementInEmptyTreePositionGiven1000() {
        ArbolGen a = new ArbolGen();
        boolean i =a.insertarPorPosicion(1, 1000);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementInPositionGraterThan1() {
        ArbolGen a = new ArbolGen();
        a.insertarPorPosicion(1, 1);
        a.insertarPorPosicion(2, 1);
        boolean i =a.insertarPorPosicion(3, 2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="\"^1:2\r?\n2:3\r?\n3:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAddElementInInexistentFather() {
        ArbolGen a = new ArbolGen();
        a.insertarPorPosicion(1, 1);
        boolean i =a.insertarPorPosicion(2, 3);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertFalse(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    

    @Test
    public void testAnElementIsInTheRootOfTree() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        boolean i =a.pertenece(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAnElementIsInALeafOfTree() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        boolean i =a.pertenece(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertTrue(i);
        String rx="^1:2\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAnElementIsNotInAnEmtpyTree() {
        ArbolGen a = new ArbolGen();
        boolean i =a.pertenece(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertTrue(ev);
        assertFalse(i);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAnElementIsNotInATreeWithOneElement() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        
        boolean i =a.pertenece(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertFalse(i);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAnElementIsNotInATreeWithMoreThanOneElement() {
        ArbolGen a = new ArbolGen();
        a.insertar(1, null);
        a.insertar(2, 1);
        boolean i =a.pertenece(3);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertFalse(i);
        String rx="^1:2\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testIsEmptyTree() {
        ArbolGen a = new ArbolGen();   
        boolean ev = a.esVacio();
        String s = a.toString();
        assertTrue(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testIsNotEmptyTree() {
        ArbolGen a = new ArbolGen();   
        a.insertar(1, null);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testFatherExistElementWithOneOcurrency() {
        ArbolGen a = new ArbolGen();   
        a.insertar(1, null);
        a.insertar(2,1);
        int p = (int) a.padre(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertEquals(1,p);
        String rx="^1:2\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testFatherExistMultipleElementOcurrenciesDifferntFather() {
        ArbolGen a = new ArbolGen();   
        a.insertar(1, null);
        a.insertar(3,1);
        a.insertar(2,1);
        a.insertar(4,1);
        a.insertar(2,4);
        int p = (int) a.padre(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        assertEquals(4,p);
        String rx="^1:4,2,3\r?\n4:2\r?\n2:\r?\n2:\r?\n3:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };
    @Test
    public void testFatherDoesNotExistEmptyTree() {
        ArbolGen a = new ArbolGen();  
        int p = (int) a.padre(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(null,p);
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };
    @Test
    public void testFatherDoesNotExistNonEmptyTree() {
        ArbolGen a = new ArbolGen();   
        a.insertar(1, null);
        int p = (int) a.padre(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(null,p);
        assertFalse(ev);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testFatherOfRoot() {
        ArbolGen a = new ArbolGen();   
        a.insertar(1, null);
        int p = (int) a.padre(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(null,p);
        assertFalse(ev);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testHightEmptyTree() {
        ArbolGen a = new ArbolGen();   
        int h=a.altura();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(-1,h);
        assertTrue(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };
    

    @Test
    public void testHightTreeWithOneElement() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        int h=a.altura();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(0,h);
        assertFalse(ev);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testHightTreeWithMoreThanOneLevel() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        int h=a.altura();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(2,h);
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };


    @Test
    public void testLevelEmptyTree() {
        ArbolGen a = new ArbolGen();   
        int n=a.nivel(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(-1,n);
        assertTrue(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };
    

    @Test
    public void testLevelTreeWithOneElementInTheTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        int n=a.nivel(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(0,n);
        assertFalse(ev);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testLevelTreeWithOneElementNotInTheTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        int n=a.nivel(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(-1,n);
        assertFalse(ev);
        String rx="^1:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testLevelTreeWithMoreThanOneLevelElementInTheTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        int n=a.nivel(5);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(2,n);
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testLevelTreeWithMoreThanOneLevelElementNotInTheTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        int n=a.nivel(8);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertEquals(-1,n);
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
    };

    @Test
    public void testAncestorsEmtpyTree() {
        ArbolGen a = new ArbolGen();  
        Lista l=a.ancestros(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertTrue(l.esVacia());
        assertTrue(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);
    };

    @Test
    public void testAncestorsExistentElementTreeWithOneNode() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        
        Lista l=a.ancestros(1);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertTrue(l.esVacia());
        assertFalse(ev);
        String rx="^1:";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);
    };

    @Test
    public void testAncestorsNonExistentElementTreeWithOneNode() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        
        Lista l=a.ancestros(2);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertTrue(l.esVacia());
        assertFalse(ev);
        String rx="^1:";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);
    };

    @Test
    public void testAncestorsExistentElementInLeafInTreeWithMoreOneNode() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        Lista l=a.ancestros(5);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[1,3\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);

    };

    @Test
    public void testAncestorsExistentElementInInternalNodeInTreeWithMoreOneNode() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        Lista l=a.ancestros(3);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[1\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);

    };

    @Test
    public void testCloneEmtpyTree() {
        ArbolGen a = new ArbolGen();  
        ArbolGen c=a.clone();
        boolean ev = a.esVacio();
        boolean evc = c.esVacio();
        String s = a.toString();
        String sc = c.toString();
        assertTrue(evc);
        assertTrue(ev);
        String rx="";
        String rxc="";
        boolean findSubstring = isSubstring(s,rx);
        boolean findSubstringc = isSubstring(sc,rxc);
        assertTrue(findSubstring);
        assertTrue(findSubstringc);

    };

    @Test
    public void testCloneNonEmtpyTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        ArbolGen c=a.clone();
        boolean ev = a.esVacio();
        boolean evc = c.esVacio();
        String s = a.toString();
        String sc = c.toString();
        assertFalse(evc);
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        boolean findSubstringc = isSubstring(sc,rx);
        assertTrue(findSubstring);
        assertTrue(findSubstringc);

    };

        @Test
    public void testClearEmtpyTree() {
        ArbolGen a = new ArbolGen();  
        a.vaciar();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);

    };

    @Test
    public void testCleanOneNodeTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.vaciar();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);

    };

    @Test
    public void testCleanMoreThanOneNodeTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.vaciar();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);

    };

    @Test
    public void testPreorderListEmptyTree() {
        ArbolGen a = new ArbolGen();  
        Lista l=a.listarPreorden();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);
    };

    @Test
    public void testPreorderListNonEmptyTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        Lista l=a.ancestros(5);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[1,2,3,5,4\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);

    };


    @Test
    public void testPostorderListEmptyTree() {
        ArbolGen a = new ArbolGen();  
        Lista l=a.listarPreorden();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);
    };

    @Test
    public void testPostorderListNonEmptyTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        Lista l=a.ancestros(5);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[4,5,3,2,1\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);

    };

    @Test
    public void testInorderListEmptyTree() {
        ArbolGen a = new ArbolGen();  
        Lista l=a.listarPreorden();
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);
    };

    @Test
    public void testInorderListNonEmptyTree() {
        ArbolGen a = new ArbolGen();  
        a.insertar(1, 1) ;
        a.insertar(4, 1) ;
        a.insertar(3, 1) ;
        a.insertar(2, 1) ;
        a.insertar(5, 3) ;
        Lista l=a.ancestros(5);
        boolean ev = a.esVacio();
        String s = a.toString();
        assertFalse(l.esVacia());
        assertFalse(ev);
        String rx="^1:4,3,2\r?\n4:\r?\n3:5\r?\n5:\r?\n2:$";
        boolean findSubstring = isSubstring(s,rx);
        assertTrue(findSubstring);
        String li=l.toString();
        String rxl="\\[2,1,5,3,4\\]";
        boolean findSubstringl = isSubstring(li,rxl);
        assertTrue(findSubstringl);

    };


}
