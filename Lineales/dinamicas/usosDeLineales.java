package Lineales.dinamicas;

public class usosDeLineales {
    public static void main(String[] args) {
        
        Lista l1 = new Lista();
        Lista l2 = new Lista();
        

        l1.insertar(1, 1);
        l1.insertar(10, 2);
        l1.insertar(100, 3);
        l1.insertar(1000, 4);
         
        l2.insertar(1, 1);
        l2.insertar(2, 1);
        l2.insertar(3, 1);
        l2.insertar(4, 1);

        Lista retornada = concatenar(l1,l2);

        System.out.println(retornada);
                                            


        System.out.println(l1);
        Lista l3 = invertir(l1);

        System.out.println(l3);
    }

    public static Lista concatenar(Lista l1, Lista l2){
        Lista nueva = l1.clone();
        int pos = nueva.longitud() + 1; 
        for(int i = 1; i <=  l2.longitud(); i++){
            nueva.insertar(l2.recuperar(i), pos);
            pos++;
        }
        return nueva;
    }

    public static boolean comprobar(Lista unaLista){
        boolean comprobado = true;
        Pila pilaAux = new Pila();
        Cola colaAux = new Cola();

        int i = 1;
        while(!unaLista.recuperar(i).equals(0)){
            pilaAux.apilar(unaLista.recuperar(i));
            colaAux.poner(unaLista.recuperar(i));
            i++;
        }

        //Verifica que se haya cargado algo antes del 0
        if(pilaAux.esVacia()){
            comprobado = false;
        }

        //Salta el primer cero.
        i++;


        //Segunda cadena.
        boolean bandera = true;
            while(bandera && !colaAux.esVacia()){
                if(unaLista.recuperar(i).equals(colaAux.obtenerFrente())){
                    i++;
                    colaAux.sacar();
                }else if(unaLista.recuperar(i).equals(0)){
                    //Sale y salta el segundo 0
                    bandera = false;
                    i++;
                }else{
                    comprobado = false;
                    bandera = false;
                }
            }

        if(!colaAux.esVacia()){
            comprobado = false;
        }
        
        while(comprobado && !pilaAux.esVacia()){

            if(unaLista.recuperar(i).equals(pilaAux.obtenerTope())){
                pilaAux.desapilar();
                i++;
            }else{
                comprobado = false;
            }
        }


        if(!pilaAux.esVacia()){
            comprobado = false;
        }

        return comprobado;
    }

    public static Lista invertir(Lista unaLista){
        Pila pilaAux = new Pila();

        //Recorro y obtengo cada elemento de la lista

        int i = 1;

        while(unaLista.recuperar(i) != null){
            pilaAux.apilar(unaLista.recuperar(i));
            i++;
        }

        //Ahora genero nueva lista y le inserto los elementos de la pila.
        i = 1;
        Lista retornoLista = new Lista();

        while(pilaAux.obtenerTope() != null){
            retornoLista.insertar(pilaAux.obtenerTope(), i);
            pilaAux.desapilar();
            i++;
        }

        return retornoLista;

    }
}
