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
        if (!tablaHabitaciones.pertenece(h.getCodigo())){
            //guardamos la habitación en la tabla de habitaciones para guardar los datos
            tablaHabitaciones.insertar(h);
            //guardamos la clave de la habitación en el plano de la casa
            planoCasa.insertarVertice(h.getCodigo());
            valido = true;
        }
        return valido;
    }

    public boolean eliminarHabitacion(int codigoHabitacion){
        boolean valido = false;
        if (codigoHabitacion != this.idHabitacionEntrada && codigoHabitacion != this.idHabitacionSalida && tablaHabitaciones.pertenece(codigoHabitacion)){
            tablaHabitaciones.eliminar(codigoHabitacion); //se elimina la habitación de la base de datos
            planoCasa.eliminarVertice(codigoHabitacion); //se elimina la habitacion de la casa con sus puertas
            valido = true;
        }
        return valido;
    }

    public boolean insertarPuerta(int habOrigen, int habDestino, int puntajeExigido){
        boolean valido = false;
        if (puntajeExigido > 0 && !planoCasa.existeArco(habOrigen, habDestino) && tablaHabitaciones.pertenece(habOrigen) && tablaHabitaciones.pertenece(habDestino)){
            //se agrega en ambos sentidos la puerta
            planoCasa.insertarArco(habOrigen, habDestino, puntajeExigido);
            planoCasa.insertarArco(habDestino, habOrigen, puntajeExigido);
            valido = true;
        }
        return valido;
    }

    public boolean eliminarPuerta(int habOrigen, int habDestino){
        boolean exito = false;
        if(planoCasa.existeArco(habOrigen, habDestino)){
            planoCasa.eliminarArco(habOrigen, habDestino);
            planoCasa.eliminarArco(habDestino, habOrigen);
            exito = true;
        }
        return exito;
    }

    public boolean modificarHabitacion(String nuevoNombre, int nuevaPlanta, double nuevoTamañoMtsCuadrados,){
        //mmm nose
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

    public boolean agregarEquipo(Equipo e){
        boolean valido = false;
        return valido;
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
        boolean esPosible = false;
        return esPosible;
    }

    public String minimoPuntaje(int cod1, int cod2){
        String informacion = "";
        return informacion;
    }

    public String sinPasarPor(int cod1, int cod2, int cod3, int puntajeMax){
        String informacion = "";
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




}
