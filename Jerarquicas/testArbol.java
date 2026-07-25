package Jerarquicas;

public class testArbol {
    public static void main(String[] args) {
        

        ArbolBin arbol = new ArbolBin();

        arbol.insertar(5, null, 'I'); // o 'D', da igual porque es raíz
        arbol.insertar(3, 5, 'I');    // hijo izquierdo de 5
        arbol.insertar(7, 5, 'D');    // hijo derecho de 5
        arbol.insertar(15, 20, 'I');  // hijo izquierdo de 20
        

        System.out.println(arbol);

    }
}
