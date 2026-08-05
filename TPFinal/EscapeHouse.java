package TPFinal;
import Conjuntistas.*;
import Lineales.dinamicas.Lista;
import grafos.*;
import java.util.HashMap;

public class EscapeHouse {
    private Grafo planoCasa;
    private ArbolAVL tablaHabitaciones;
    private ArbolAVL tablaDesafios;
    private HashMap<String, Equipo> tablaEquipos;
    private HashMap<String, Lista> desafiosResueltosPorEquipo;

    //variables que guardan cual es la entrada y salida de la casa.
    private int idHabitacionEntrada = -1;
    private int idHabitacionSalida = -1;

    public EscapeHouse(){
        this.planoCasa = new Grafo();
        this.tablaHabitaciones = new ArbolAVL(); //clave: int codigo
        this.tablaDesafios = new ArbolAVL(); //clave: int puntaje
        this.tablaEquipos = new HashMap<>(); //clave: String nombre
        this.desafiosResueltosPorEquipo = new HashMap<>();
    }
     
    //Modificaciones
    public boolean insertarHabitacion(Habitacion h){
        boolean valido = false;
        if (!tablaHabitaciones.pertenece(new Habitacion(h.getCodigo()))){
            //guardamos la habitacion en la tabla de habitaciones para guardar los datos
            tablaHabitaciones.insertar(h);
            //guardamos la clave de la habitacion en el plano de la casa
            planoCasa.insertarVertice(h.getCodigo());
            valido = true;
        }
        return valido;
    }
    public boolean eliminarEquipo(String nombreEquipo){
        boolean exito = false;
        if(tablaEquipos.containsKey(nombreEquipo)){
            Lista lista = tablaHabitaciones.listar();
            for(int i =1 ; i <= lista.longitud();i++){
                Habitacion hab = (Habitacion) lista.recuperar(i);
                hab.getPuntajeEquipos().remove(nombreEquipo);
            }
            tablaEquipos.remove(nombreEquipo);
            desafiosResueltosPorEquipo.remove(nombreEquipo);
            exito = true;
        }
        return exito;
    }
    public boolean eliminarHabitacion(int codigoHabitacion){
        boolean valido = false;
        if (codigoHabitacion != this.idHabitacionEntrada && codigoHabitacion != this.idHabitacionSalida && tablaHabitaciones.pertenece(new Habitacion(codigoHabitacion))){
            tablaHabitaciones.eliminar(new Habitacion(codigoHabitacion)); //se elimina la habitacion de la base de datos
            planoCasa.eliminarVertice(codigoHabitacion); //se elimina la habitacion de la casa con sus puertas
            valido = true;
        }
        return valido;
    }

    public boolean insertarPuerta(int habOrigen, int habDestino, int puntajeExigido){
        boolean valido = false;
        if (puntajeExigido > 0 && !planoCasa.existeArco(habOrigen, habDestino) && tablaHabitaciones.pertenece(new Habitacion(habOrigen)) && tablaHabitaciones.pertenece(new Habitacion (habDestino))){
            //se agrega en ambos sentidos la puerta
            planoCasa.insertarArco(habOrigen, habDestino, puntajeExigido);
            valido = true;
        }
        return valido;
    }

    public boolean eliminarPuerta(int habOrigen, int habDestino){
        // El Grafo valida si existe y nos devuelve true si logro borrarlo.
        return planoCasa.eliminarArco(habOrigen, habDestino);
    }
    public boolean modificarDesafio(String nuevoNombre, String nuevoTipo, int puntaje, int codigo){
        boolean exito = false;
        Habitacion hab = (Habitacion) tablaHabitaciones.obtener(new Habitacion(codigo));
        if(hab!=null){
            Desafio des = (Desafio)hab.getDesafios().obtener(new Desafio(puntaje));
            if(des!=null){
            des.setNombre(nuevoNombre);
            des.setTipo(nuevoTipo);
            exito = true;
        }
        }
        return exito;
    }
    public boolean modificarHabitacion(String nuevoNombre, int nuevaPlanta, double nuevoTamanioMtsCuadrados, int codigo){
       boolean exito = false;
       Habitacion aModificar = (Habitacion)tablaHabitaciones.obtener(new Habitacion(codigo));
       if(aModificar!=null){
        aModificar.setNombre(nuevoNombre);
        aModificar.setPlanta(nuevaPlanta);
        aModificar.setMetrosCuadrados(nuevoTamanioMtsCuadrados);
        exito = true;
       }
       return exito;
    }
    public boolean modificarEquipo(String nombreEquipo,int puntajeExigidoNuevo){
        boolean exito   = false;
        Equipo equipo = tablaEquipos.get(nombreEquipo);
        if(equipo!=null){
            equipo.setPuntajeExigido(puntajeExigidoNuevo);
            exito = true;
        }
        return exito;
    }
    public boolean agregarDesafio(Desafio d){
        boolean valido = false;
        if (d != null){
            //creamos un aux liviano para utilizar el arbolAVL
            Habitacion habAux = new Habitacion(d.getCodigoHabitacion());
            //revisamos que exista la habitacion donde el desafío debería estar
            if (tablaHabitaciones.pertenece(habAux)){
                //Verificamos si se pudo insertar, si ya estaba el desafío, devolvió falso.
                boolean desafioInsertado = tablaDesafios.insertar(d);
                if (desafioInsertado){
                    //creamos un puntero con la habitacion deseada y guardamos el desafio en el arbol interno de desafios
                    Habitacion hab = (Habitacion) tablaHabitaciones.obtener(habAux);
                    hab.getDesafios().insertar(d);
                    valido = true;
                }
            }
        }
        return valido;
    }

    public boolean eliminarDesafio(Desafio d){
        boolean valido = false;
        if (d != null){
            //guardamos si existía el desafío en la base de datos de Desafíos
            boolean desafioEliminado = tablaDesafios.eliminar(d);
            if (desafioEliminado){
                //creamos una habitacion liviana y la buscamos para poder borrar el desafio de su arbol interno
                Habitacion habAux = new Habitacion(d.getCodigoHabitacion());
                Habitacion hab = (Habitacion) tablaHabitaciones.obtener(habAux);
                //Si estaba la habitacion, eliminamos el desafío
                if (hab != null){
                    hab.getDesafios().eliminar(d);
                }
                //como se borró de la base de datos de la casa, fue válido el borrado
                valido = true;
            }
        }
        return valido;
    }

    public boolean agregarEquipo(Equipo equipo){
        boolean exito = false;
        if(!tablaEquipos.containsKey(equipo.getNombre())){
            tablaEquipos.put(equipo.getNombre(),equipo);
            desafiosResueltosPorEquipo.put(equipo.getNombre(), new Lista());
            exito = true;
        }
        return exito;
    }

    //Consultas
        //consultas de Habitaciones
    public String mostrarHabitacion(int codigo){
        String informacion = "La habitación " + codigo + " no existe en el sistema.";
        Habitacion señuelo = new Habitacion(codigo);
        
        Habitacion hab = (Habitacion) tablaHabitaciones.obtener(señuelo);
        if (hab != null) {
            informacion = hab.toString();
        }
        return informacion;
    }

    public String habitacionesContiguas(int codigo){
    String informacion = "La habitacion no existe";  // mensaje si la habitación no existe
    if(tablaHabitaciones.pertenece(new Habitacion(codigo))){
        informacion = "Habitaciones contiguas: ";
        Lista adyacentes = planoCasa.verticesAdyacentes(codigo);
        for(int i = 1; i <= adyacentes.longitud(); i++){
            int codigoVecino = (Integer) adyacentes.recuperar(i);
            Habitacion vecino = (Habitacion) tablaHabitaciones.obtener(new Habitacion(codigoVecino));
            int puntaje = planoCasa.obtenerEtiqueta(codigo, codigoVecino);
            informacion += "\n" + vecino.getNombre() + ": " + puntaje + " puntos";
        }
    }
    return informacion;
}

    //Creo que no hace falta que sean contiguas
    public boolean esPosibleLlegar(int codigo1, int codigo2, int puntos){
        return this.planoCasa.esPosibleLlegar(codigo1, codigo2, puntos);
    }

    public String minimoPuntaje(int cod1, int cod2){
        String informacion = "No se encontró un camino posible entre estas habitaciones";
        int[] puntajeMin = new int[1];
        Lista camino = planoCasa.minimoPuntaje(cod1, cod2, puntajeMin);
        if (!camino.esVacia()){
            informacion = "El mejor camino es " + camino.toString() + ". Requiere que el equipo tenga " + puntajeMin[0] + " puntos.";
        }
        return informacion;
    }

    public String sinPasarPor(int cod1, int cod2, int cod3, int puntajeMax){
        String informacion = "No hay caminos disponibles que cumplan con esas condiciones.";
        
        // Llamamos al grafo pasandole el origen, destino, la habitacion a evitar y los puntos maximos
        Lista caminos = planoCasa.caminosSinPasarPor(cod1, cod2, cod3, puntajeMax);
        
        if (!caminos.esVacia()) {
            informacion = "Caminos posibles:\n";
            // Recorremos la lista de caminos
            for (int i = 1; i <= caminos.longitud(); i++) {
                // Recuperamos cada camino individual (casteando a Lista)
                Lista caminoActual = (Lista) caminos.recuperar(i);
                informacion += "Camino " + i + ": " + caminoActual.toString() + "\n";
            }
        }
        
        return informacion;
    }

        //Consultas de Desafios

    public String mostrarDesafio(int puntaje, int codHabitacion){
        String info = "El desafio no existe en el sistema";
        Desafio desafioAux = new Desafio(puntaje, codHabitacion);
        Desafio desafio = (Desafio) tablaDesafios.obtener(desafioAux);
        if (desafio != null){
            info = desafio.toString();
        }
        return info;
    }

    public String mostrarDesafiosResueltos(String nomEq){
        String info = "El equipo no existe";
        if (desafiosResueltosPorEquipo.containsKey(nomEq)){
            Lista lista = desafiosResueltosPorEquipo.get(nomEq);
            if(lista.esVacia()){
                info = "El equipo " + nomEq + " no resolvió ningún desafío";
            }else{
                info = "el equipo " + nomEq + " resolvió:" + "\n"  + lista.toString();
            }
        }
        return info;
    }

    //quitamos el parámetro de habitación porque consideramos que no era necesario para hacer la consulta
    public boolean verificarDesafioResuelto(Equipo eq, Desafio de){
        boolean resuelto = false;
        if(eq != null && de != null && desafiosResueltosPorEquipo.containsKey(eq.getNombre())){
            Lista resueltos = desafiosResueltosPorEquipo.get(eq.getNombre());
            resuelto = (resueltos.localizar(de) > 0);
        }
        return resuelto;
    }

    public String mostrarDesafiosTipo(Habitacion h, int puntajeA, int puntajeB, String tipoX){
        String desafios = "No se encontraron desafios que coincidan con los parámetros";
        Lista lista = new Lista();
        int longLista = 0;
        if (h != null && puntajeA > 0 && puntajeB >= puntajeA && tipoX != null){
            tipoX = tipoX.toLowerCase();
            if (tipoX.equals("lógico") || tipoX.equals("matemático") || tipoX.equals("destreza") || tipoX.equals("letras") || tipoX.equals("búsqueda") || tipoX.equals("ingenio")) {
                ArbolAVL desafiosHabitacion = h.getDesafios();
                if (!desafiosHabitacion.vacio()){
                    Desafio minPuntaje = new Desafio(puntajeA, h.getCodigo());
                    Desafio maxPuntaje = new Desafio(puntajeB, h.getCodigo());

                    Lista desafiosEnRango = desafiosHabitacion.listarRango(minPuntaje, maxPuntaje);
                    if (!desafiosEnRango.esVacia()){
                        int longitud = desafiosEnRango.longitud(); 
                        
                        int i = 1;

                        while(i <= longitud){
                            Desafio def = (Desafio) desafiosEnRango.recuperar(i);
                            if (def.getTipo().equalsIgnoreCase(tipoX)){
                                longLista++;
                                lista.insertar(def, longLista);
                            }
                            i++;
                        }
                        if (!lista.esVacia()){
                            desafios = "Desafíos encontrados:\n" + lista.toString();
                        }
                    }
                }
            }

        }
        return desafios;
    }

        //consulta sobre equipos
    public String mostrarInfoEquipos(String nombreEq){
        String info = "No se encontró un equipo con el nombre " + nombreEq;
        if (nombreEq != null && tablaEquipos.containsKey(nombreEq)){
            info = tablaEquipos.get(nombreEq).toString() + "\nDesafios resueltos:\n" + desafiosResueltosPorEquipo.get(nombreEq).toString();
        }

        return info;
    }
    public boolean puedeSalir(String nombreEquipo){
        boolean puede = false;
        if(tablaEquipos.containsKey(nombreEquipo)){
            Equipo equipo = (Equipo) tablaEquipos.get(nombreEquipo);
            Habitacion habActual = (Habitacion) tablaHabitaciones.obtener(new Habitacion(equipo.getCodigoHabitacionActual()));
            if(habActual.isTieneSalida()){
                if(equipo.getPuntajeTotal()>=equipo.getPuntajeExigido()){
                    puede = true;
                }
            }
        }
        return puede;
    }
    
    public boolean jugarDesafio(String nombreEquipo, int codigoHabitacion, int puntajeDesafio){
        boolean exito = false;
        Equipo equipo = tablaEquipos.get(nombreEquipo);
        Habitacion hab = (Habitacion) tablaHabitaciones.obtener(new Habitacion(codigoHabitacion));
        if(equipo!=null && equipo.getCodigoHabitacionActual() == codigoHabitacion && hab != null){
            Desafio desafio = (Desafio)hab.getDesafios().obtener(new Desafio(puntajeDesafio));
            if(desafio!=null){
                if(!hab.getPuntajeEquipos().containsKey(nombreEquipo)){
                hab.getPuntajeEquipos().put(nombreEquipo, 0);
                }
                int puntajeActual = hab.getPuntajeEquipos().get(nombreEquipo);
                Lista lista = desafiosResueltosPorEquipo.get(nombreEquipo);
            if(!verificarDesafioResuelto(equipo, desafio)){
                lista.insertar(desafio,lista.longitud()+1 );
                equipo.setPuntajeActualEnHabitacion(puntajeActual+puntajeDesafio);
                hab.getPuntajeEquipos().put(nombreEquipo, puntajeActual + puntajeDesafio);
                equipo.setPuntajeTotal(equipo.getPuntajeTotal()+puntajeDesafio);
                exito = true;
            }
            }
        }
        return exito;
    }
    // public boolean yaResolvio(Lista lista, int puntajeDesafio, int codigoHabitacion){
    //     boolean encontrado = false;
    //     if(lista.localizar(new Desafio(puntajeDesafio,codigoHabitacion)) != -1){
    //         encontrado = true;
    //     }
    //     return encontrado;
    // }
    //Este metodo usa existeArco para verificar que haya una puerta directa entre la habitacion actual del equipo y la destino, y obtenerEtiqueta para saber cuanto puntaje exige esa puerta. Si el equipo tiene acumulado en su habitacion actual al menos ese puntaje, se lo deja pasar: se actualiza su habitacion actual y se le restaura el puntaje que ya tenia guardado en la habitacion destino (o 0 si nunca estuvo ahi).
    public boolean cambiarDeHabitacion(String nombreEquipo,int codigoDestino){
        boolean exito = false;
        Equipo equipo = tablaEquipos.get(nombreEquipo);
        if(equipo != null){
            Habitacion hab = (Habitacion)tablaHabitaciones.obtener(new Habitacion(codigoDestino));
            if(planoCasa.existeArco(equipo.getCodigoHabitacionActual(), codigoDestino)){
                int puntajeRequerido = planoCasa.obtenerEtiqueta(equipo.getCodigoHabitacionActual(), codigoDestino);
                if(puntajeRequerido != -1 && equipo.getPuntajeActualEnHabitacion() >= puntajeRequerido){
                    equipo.setCodigoHabitacionActual(codigoDestino);
                    equipo.setPuntajeActualEnHabitacion(hab.getPuntajeEquipos().getOrDefault(nombreEquipo, 0));
                    exito = true;
                }
            }
        }
        return exito;
    }
    public String posiblesDesafios(String nombreEquipo, int codHabitacionDestino){
    String info = "El equipo no existe";
    Equipo equipo = tablaEquipos.get(nombreEquipo);
    if(equipo != null){
        if(!planoCasa.existeArco(equipo.getCodigoHabitacionActual(), codHabitacionDestino)){
            info = "No existe puerta entre la habitacion en la que se encuentra el equipo y la de destino";  // mensaje aclaratorio: hab no es adyacente
        } else {
            int puntajeRequerido = planoCasa.obtenerEtiqueta(equipo.getCodigoHabitacionActual(), codHabitacionDestino);
            Habitacion habActual = (Habitacion) tablaHabitaciones.obtener(new Habitacion(equipo.getCodigoHabitacionActual()));
            Lista desafios = habActual.getDesafios().listar();
            info = "Desafios posibles: ";
            for(int i = 1; i <= desafios.longitud(); i++){
                Desafio d = (Desafio) desafios.recuperar(i);
                if(!verificarDesafioResuelto(equipo, d) && equipo.getPuntajeActualEnHabitacion()+d.getPuntaje() >= puntajeRequerido ){  // no resuelto por el equipo, Y que solo alcance
                    info += "\n "+ d.getNombre() + " Tipo: " + d.getTipo() + " Puntaje: "+d.getPuntaje();
                }
            }
        }
    }
    return info;
}
}
