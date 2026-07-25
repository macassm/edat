package Lineales.dinamicas;

public class mixLineales {
    public static void main(String[] args) {
        
         //GENERAR COLA DE INVERSION DE CARACTERES

        Cola c2 = new Cola();
        c2.poner('A');
        c2.poner('B');
        c2.poner('C');
        c2.poner('$');
        c2.poner('K');
        c2.poner('M');
        c2.poner('$');
        c2.poner('P');
        c2.poner('A');
        c2.poner('$');

        Cola c1 = c2.clone();

        Cola retorno = generarOtraCola(c1);
        System.out.println(retorno);
        Lista resultado = generarSecuencia(c2, 3);
        System.out.println(resultado);



    }

    public static Cola generarOtraCola(Cola c1){
        Cola inversa = new Cola();
        Pila socorro = new Pila();

        while(!c1.esVacia()){
            if('$' != (char)c1.obtenerFrente()){
                inversa.poner(c1.obtenerFrente());
                socorro.apilar(c1.obtenerFrente());

            }else{ 
                while(!socorro.esVacia()){
                    inversa.poner(socorro.obtenerTope());   
                    socorro.desapilar();
                }
                inversa.poner(c1.obtenerFrente());
            }
            c1.sacar();
        }
        while(!socorro.esVacia()){
            inversa.poner(socorro.obtenerTope());   
            socorro.desapilar();
        }
        return inversa;
    }
    public static Lista generarSecuencia(Cola q, int t) {
    Lista l = new Lista();
    Cola qAux = q.clone();
    Pila pAux = new Pila();
    Cola cAux = new Cola();

    while (!qAux.esVacia()) {
        // Si el frente NO es el separador, procesamos el bloque de tamaño t
        if (!qAux.obtenerFrente().equals('$')) {
            int longitudBloque = 0;

            // Cortamos el bloque si llegamos a t o si topamos con un '$'
            while (!qAux.esVacia() && longitudBloque < t && !qAux.obtenerFrente().equals('$')) {
                Object item = qAux.obtenerFrente();
                qAux.sacar();

                pAux.apilar(item);
                cAux.poner(item);
                longitudBloque++;
            }

            // Descargamos la pila (secuencia invertida a_i')
            while (!pAux.esVacia()) {
                l.insertar(pAux.obtenerTope(), l.longitud() + 1);
                pAux.desapilar();
            }

            // Descargamos la cola (secuencia original a_i)
            while (!cAux.esVacia()) {
                l.insertar(cAux.obtenerFrente(), l.longitud() + 1);
                cAux.sacar();
            }

            // Si después de procesar el bloque todavía quedan elementos, 
            // ponemos el separador intermedio obligatoriamente
            if (!qAux.esVacia() && qAux.obtenerFrente().equals('$')) {
                l.insertar('$', l.longitud() + 1);
            }

        } else {
            // Si el frente ERA un '$', simplemente lo salteamos/desacolamos 
            // para avanzar al siguiente bloque sin duplicarlo
            qAux.sacar();
        }
    }

    return l;
}


}
