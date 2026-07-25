package Lineales.dinamicas;

public class testLinealesPlucas {
    public static void main(String[] args) {
        
        Cola miCola = new Cola();

        miCola.poner('A');
        miCola.poner('B');
        miCola.poner('C');
        miCola.poner('#');
        miCola.poner('A');
        miCola.poner('C');
        miCola.poner('E');
        miCola.poner('#');
        miCola.poner('Q');
        miCola.poner('L');
        miCola.poner('R');
        miCola.poner('T');

        System.out.println(miCola);

        Lista l2 = invertirVocalesDuplicarSinVocales(miCola);
        System.out.println(l2);
    }

public static Lista invertirVocalesDuplicarSinVocales(Cola q){
    Cola clon = q.clone();
    Pila aux = new Pila();   // vocales
    Cola c1 = new Cola();    // no vocales (para duplicar)
    Cola c2 = new Cola();    // copia para duplicar
    Lista retorna = new Lista();
    int i = 1;

    while(!clon.esVacia()){
        char c = (char) clon.obtenerFrente();
        clon.sacar();

        if(c == '#'){
            // procesar bloque
            if(!aux.esVacia()){
                // invertir vocales
                while(!aux.esVacia()){
                    retorna.insertar(aux.obtenerTope(), i++);
                    aux.desapilar();
                }
                c1.vaciar();
                c2.vaciar();
            } else {
                // duplicar no vocales
                while(!c1.esVacia()){
                    retorna.insertar(c1.obtenerFrente(), i++);
                    c1.sacar();
                }
                while(!c2.esVacia()){
                    retorna.insertar(c2.obtenerFrente(), i++);
                    c2.sacar();
                }
            }
            retorna.insertar('#', i++);
        } 
        else if(esVocal(c)){
            aux.apilar(c);
        } 
        else{
            c1.poner(c);
            c2.poner(c);
        }
    }

    // 🔴 procesar último bloque (si no termina en '#')
    if(!aux.esVacia() || !c1.esVacia()){
        if(!aux.esVacia()){
            while(!aux.esVacia()){
                retorna.insertar(aux.obtenerTope(), i++);
                aux.desapilar();
            }
        } else {
            while(!c1.esVacia()){
                retorna.insertar(c1.obtenerFrente(), i++);
                c1.sacar();
            }
            while(!c2.esVacia()){
                retorna.insertar(c2.obtenerFrente(), i++);
                c2.sacar();
            }
        }
    }

    return retorna;
}


    private static boolean esVocal(char c){
        boolean verificado = false;

        if(c == 'A'||c == 'E'||c == 'I'||c == 'O'||c == 'U'){
            verificado = true;
        }
        return verificado;
    }

}