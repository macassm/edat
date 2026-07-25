package Lineales.dinamicas;

public class testCadenaSIM2019 {
    public static void main(String[] args) {
        Cola miCola = new Cola();

        miCola.poner('a');
        miCola.poner('b');
        miCola.poner('c');
        miCola.poner('#');
        miCola.poner('z');
        miCola.poner('x');
        miCola.poner('#');
        miCola.poner('a');
        miCola.poner('a');

        Cola otraCola = generarCola(miCola);

        System.out.println(otraCola);



    }

    public static Cola generarCola(Cola c3){
        
        Cola unaCola = c3.clone();
        Cola cola1 = new Cola();
        Cola cola2 = new Cola();
        Pila auxPi = new Pila();
        Cola resultado = new Cola();

        while(!unaCola.esVacia()){
            char actual = (char) unaCola.obtenerFrente();
            unaCola.sacar();

            if(actual != '#'){
                cola1.poner(actual);
                cola2.poner(actual);
                auxPi.apilar(actual);
            }
            if(actual == '#' || unaCola.esVacia()){

                while(!cola1.esVacia()){
                    resultado.poner(cola1.obtenerFrente());
                    cola1.sacar();
                }

                while(!auxPi.esVacia()){
                    resultado.poner(auxPi.obtenerTope());
                    auxPi.desapilar();
                }

                while(!cola2.esVacia()){
                    resultado.poner(cola2.obtenerFrente());
                    cola2.sacar();
                }

                if(!unaCola.esVacia()){
                    resultado.poner('#');
                }
            }
        }
        return resultado;
    }
    
}