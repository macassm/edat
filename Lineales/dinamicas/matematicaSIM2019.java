package Lineales.dinamicas;

public class matematicaSIM2019 {
    public static void main(String[] args) {
        
    }

    public static boolean verificarBalanceo(Cola c1){
        boolean verifica = true;
        Pila aux = new Pila();
        Cola miCola = c1.clone();

        while(!miCola.esVacia()){    
            char c = (char) miCola.obtenerFrente();
            miCola.sacar();

            if(c == '{' || c == '[' || c == '('){
                aux.apilar(c);
            }else if(c == '}' || c == ']' || c == ')'){
                if(aux.esVacia()){
                    verifica = false;
                }else{
                    char a = (char)aux.obtenerTope();
                    aux.desapilar();
                    if(!((a == '{' && c == '}') || (a == '[' && c == ']') || (a == '(' && c == ')'))){
                        verifica = false;
                    }
                }
            }
        }
        if(!aux.esVacia()){
            verifica = false;
        }
        return verifica;
    }
       
        
    }