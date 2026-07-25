package Lineales.dinamicas;

public class testLinealesPnico {
    public static void main(String[] args) {
        Cola prueba = new Cola(); 

        prueba.poner('A');
        prueba.poner('B');
        prueba.poner('#');
        prueba.poner('C');
        prueba.poner('D');
        prueba.poner('#');
        prueba.poner('X');
        prueba.poner('Y');
        prueba.poner('Z');


        System.out.println(prueba);

        Lista retornada = generarLista(prueba);

        System.out.println(retornada);



    }
    public static Lista generarLista(Cola q1){
        Cola clon = q1.clone();
        Lista l1 = new Lista();
        Pila p1 = new Pila();
        Cola c1 = new Cola();
        int i = 0;
        int pos = 1;

        while(!clon.esVacia()){
            char elem = (char)clon.obtenerFrente();
            clon.sacar();

            if(elem != '#'){

                if(i % 2 == 0){
                    p1.apilar(elem);
                }else{
                    c1.poner(elem);
                }
            }else{
                if(i % 2 == 0){
                    while(!p1.esVacia()){
                        l1.insertar(p1.obtenerTope(), pos);
                        p1.desapilar();
                        pos++;
                    }
                }else{
                    while(!c1.esVacia()){
                        l1.insertar(c1.obtenerFrente(), pos);
                        c1.sacar();
                        pos++;
                    }
                }
                l1.insertar(elem,pos++);
                i++;
            }
        }

        while(!p1.esVacia()){
            l1.insertar(p1.obtenerTope(), pos);
            p1.desapilar();
            pos++;
        }

        while(!c1.esVacia()){
            l1.insertar(c1.obtenerFrente(), pos);
            c1.sacar();
            pos++;
        }

        return l1;
    }
}
