package TPFinal;
import grafos.*;
import Conjuntistas.*;
import java.util.HashMap;
import Lineales.dinamicas.Lista;

public class EscapeHouse {
    private Grafo planoCasa;
    private ArbolAVL tablaHabitaciones;
    private ArbolAVL tablaDesafios;
    private HashMap tablaEquipos;
    private HashMap desafiosResueltosPorEquipo;

    public EscapeHouse(){
        this.planoCasa = new Grafo();
        this.tablaHabitaciones = new ArbolAVL();
        this.tablaDesafios = new ArbolAVL();
        this.tablaEquipos = new HashMap<>();
        this.desafiosResueltosPorEquipo = new HashMap<>();
    }
     
    //Modificaciones
    public boolean insertarHabitacion(Habitacion h){
        boolean valido = false;
        return valido;
    }

    public boolean eliminarHabitacion(Habitacion h){
        boolean valido = false;
        return valido;
    }

    public boolean insertarPuerta(int habOrigen, int habDestino, int puntajeExigido){
        boolean valido = false;
        return valido;
    }

    public boolean modificarHabitacion(String nuevoNombre, int nuevaPlanta, double nuevoTamañoMtsCuadrados,){
        //mmm nose
    }

    public boolean agregarDesafio(Desafio d){
        boolean valido = false;
        return valido;
    }

    public boolean eliminarDesafio(Desafio d){
        boolean valido = false;
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
