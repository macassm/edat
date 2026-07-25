package Conjuntistas;

public class TestAVL {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       INICIANDO BATERÍA DE TESTS AVL            ");
        System.out.println("=================================================\n");

        testRotacionSimpleDerecha();
        testRotacionSimpleIzquierda();
        testRotacionDobleIzquierdaDerecha();
        testRotacionDobleDerechaIzquierda();
    }

    private static void testRotacionSimpleDerecha() {
        System.out.println("--- TEST 1: Rotación Simple a la Derecha (Caso LL) ---");
        System.out.println("Insertando: 30, 20, 10 (Desbalancea hacia la izquierda)");
        ArbolAVL arbol = new ArbolAVL();
        
        arbol.insertar(30);
        arbol.insertar(20);
        arbol.insertar(10); // ¡AQUÍ DEBE ROTAR!
        
        System.out.println("Estructura resultante esperada: Raíz 20, Hijos 10 y 30");
        System.out.println("Tu Árbol:");
        System.out.println(arbol.toString());
    }

    private static void testRotacionSimpleIzquierda() {
        System.out.println("--- TEST 2: Rotación Simple a la Izquierda (Caso RR) ---");
        System.out.println("Insertando: 10, 20, 30 (Desbalancea hacia la derecha)");
        ArbolAVL arbol = new ArbolAVL();
        
        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(30); // ¡AQUÍ DEBE ROTAR!
        
        System.out.println("Estructura resultante esperada: Raíz 20, Hijos 10 y 30");
        System.out.println("Tu Árbol:");
        System.out.println(arbol.toString());
    }

    private static void testRotacionDobleIzquierdaDerecha() {
        System.out.println("--- TEST 3: Rotación Doble Izquierda-Derecha (Caso LR) ---");
        System.out.println("Insertando: 30, 10, 20 (Desbalancea en zig-zag por izquierda)");
        ArbolAVL arbol = new ArbolAVL();
        
        arbol.insertar(30);
        arbol.insertar(10);
        arbol.insertar(20); // ¡AQUÍ DEBE HACER ROTACIÓN DOBLE!
        
        System.out.println("Estructura resultante esperada: Raíz 20, Hijos 10 y 30");
        System.out.println("Tu Árbol:");
        System.out.println(arbol.toString());
    }

    private static void testRotacionDobleDerechaIzquierda() {
        System.out.println("--- TEST 4: Rotación Doble Derecha-Izquierda (Caso RL) ---");
        System.out.println("Insertando: 10, 30, 20 (Desbalancea en zig-zag por derecha)");
        ArbolAVL arbol = new ArbolAVL();
        
        arbol.insertar(10);
        arbol.insertar(30);
        arbol.insertar(20); // ¡AQUÍ DEBE HACER ROTACIÓN DOBLE!
        
        System.out.println("Estructura resultante esperada: Raíz 20, Hijos 10 y 30");
        System.out.println("Tu Árbol:");
        System.out.println(arbol.toString());
    }
}
