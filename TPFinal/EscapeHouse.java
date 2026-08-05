package TPFinal;
import grafos.*;
import Conjuntistas.*;
import java.util.HashMap;
import Lineales.dinamicas.Lista;

public class EscapeHouse {
    private Grafo planoCasa;
    private ArbolAVL tablaHabitaciones;
    private ArbolAVL tablaDesafios;
    private HashMap<String, Equipo> tablaEquipos;
    private HashMap<String, Lista> desafiosResueltosPorEquipo;

    //variables que guardan cuál es la entrada y salida de la casa.
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
            exito = true;
        }
        return exito;
    }

    //Consultas
        //consultas de Habitaciones
    public String mostrarHabitacion(int codigo){
        String informacion = "";
        return informacion;
    }

    public String habitacionesContiguas(int codigo){
        String informacion = "";
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
        
        // Llamamos al grafo pasándole el origen, destino, la habitación a evitar y los puntos máximos
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
        String info = "";
        return info;
    }

    public String mostrarDesafiosResueltos(Equipo eq){
        String info = "";
        return info;
    }

    public boolean verificarDesafioResuelto(Equipo eq, Desafio de, Habitacion ha){
        boolean resuelto = false;
        return resuelto;
    }

    public Lista mostrarDesafiosTipo(Habitacion h, int puntajeA, int puntajeB, String tipoX){
        Lista lista = new Lista();
        return lista;
    }

        //consulta sobre equipos
    public String mostrarInfoEquipos(String nombreEq){
        String info = "";
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
            Lista lista = desafiosResueltosPorEquipo.get(nombreEquipo);
            if(!yaResolvio(lista, puntajeDesafio, codigoHabitacion)){
                lista.insertar(desafio,lista.longitud()+1 );
                equipo.setPuntajeActualEnHabitacion(equipo.getPuntajeActualEnHabitacion()+puntajeDesafio);
                equipo.setPuntajeTotal(equipo.getPuntajeTotal()+puntajeDesafio);
                exito = true;
            }
            }
        }
        return exito;
    }
    private boolean yaResolvio(Lista lista, int puntajeDesafio, int codigoHabitacion){
        boolean encontrado = false;
        if(lista.localizar(new Desafio(puntajeDesafio,codigoHabitacion)) != -1){
            encontrado = true;
        }
        return encontrado;
    }
}
