package TPFinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static final String RUTA_LOG = "log_escape_house.txt";
    private static final String RUTA_DATOS = "TPFinal/datos_iniciales.txt";

    public static void main(String[] args) {
        EscapeHouse escapeHouse = new EscapeHouse();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 
            switch (opcion) {
                case 1:
                    cargarSistemaDesdeArchivo(escapeHouse, RUTA_DATOS);
                    registrarEnLog("--- CARGA INICIAL FINALIZADA ---");
                    registrarEnLog(escapeHouse.mostrarSistema()); 
                    break;
                case 2:
                    menuModificaciones(escapeHouse, scanner);
                    break;
                case 3:
                    menuConsultasHabitaciones(escapeHouse, scanner);
                    break;
                case 4:
                    menuConsultasDesafios(escapeHouse, scanner);
                    break;
                case 5:
                    menuEquipos(escapeHouse, scanner);
                    break;
                case 6:
                    System.out.println(escapeHouse.mostrarSistema());
                    break;
                case 0:
                    System.out.println("Saliendo del programa");
                    registrarEnLog("--- ESTADO DEL SISTEMA AL FINALIZAR ---");
                    registrarEnLog(escapeHouse.mostrarSistema());
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);

    }

    private static void mostrarMenu() {
        System.out.println("==================================================");
        System.out.println("                  ESCAPE HOUSE MENÚ               ");
        System.out.println("==================================================");
        System.out.println("1. Carga inicial del sistema");
        System.out.println("2. Cargar, modificar o eliminar (habitaciones, desafíos, puertas o equipos)");
        System.out.println("3. Consultas sobre habitaciones");
        System.out.println("4. Consultas sobre desafíos");
        System.out.println("5. Acciones y consultas sobre equipos");
        System.out.println("6. Mostrar sistema completo");
        System.out.println("0. Salir");
        System.out.println("==================================================");
    }

    private static void menuModificaciones(){
        System.out.println("=== Menú de Modificaciones ===");
            System.out.println("1. Agregar una habitación");
            System.out.println("2. Modificar una habitación");
            System.out.println("3. Eliminar una habitación");
            System.out.println("4. Agregar un desafío");
            System.out.println("5. Modificar un desafío");
            System.out.println("6. Eliminar un desafío");
            System.out.println("7. Agregar una puerta");
            System.out.println("8. Eliminar una puerta");
            System.out.println("9. Agregar un equipo");
            System.out.println("10. Modificar un equipo");
            System.out.println("11. Eliminar un equipo");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");
    }

    private static void cargarSistemaDesdeArchivo(EscapeHouse juego, String ruta) {
        System.out.println("Cargando datos desde " + ruta + "...");
        int hC = 0, dC = 0, pC = 0, eC = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("//")) continue;

                String[] p = linea.split("[;:]");
                if (p.length == 0) continue;

                String tipo = p[0].trim().toUpperCase();
                switch (tipo) {
                    case "H": // Habitacion: H;codigo;nombre;planta;superficie;tieneSalida
                        if (p.length >= 6) {
                            Habitacion hab = new Habitacion(
                                    Integer.parseInt(p[1].trim()),
                                    p[2].trim(),
                                    Integer.parseInt(p[3].trim()),
                                    Double.parseDouble(p[4].trim()),
                                    Boolean.parseBoolean(p[5].trim())
                            );
                            if (juego.insertarHabitacion(hab)) {
                                hC++;
                            }
                        }
                        break;

                    case "D": // Desafío: D;puntaje;codigoHabitacion;nombre;tipo
                        if (p.length >= 5) {
                            Desafio des = new Desafio(
                                    Integer.parseInt(p[1].trim()),
                                    Integer.parseInt(p[2].trim()),
                                    p[3].trim(),
                                    p[4].trim()
                            );
                            if (juego.agregarDesafio(des)) {
                                dC++;
                            }
                        }
                        break;

                    case "P": // Puerta (Arco): P;origen;destino;puntajeExigido
                        if (p.length >= 4) {
                            if (juego.insertarPuerta(
                                    Integer.parseInt(p[1].trim()),
                                    Integer.parseInt(p[2].trim()),
                                    Integer.parseInt(p[3].trim())
                            )) pC++;
                        }
                        break;

                    case "E": // Equipo: E;nombre;ptsExigidos;ptsTotal;habActual;ptsActualHab
                        if (p.length >= 6) {
                            Equipo eq = new Equipo(
                                    p[1].trim(),
                                    Integer.parseInt(p[2].trim()),
                                    Integer.parseInt(p[3].trim()),
                                    Integer.parseInt(p[4].trim()),
                                    Integer.parseInt(p[5].trim())
                            );
                            if (juego.agregarEquipo(eq)) eC++;
                        }
                        break;
                }
            }
            System.out.println("Carga completada: " + hC + " habs, " + dC + " desafíos, " + pC + " puertas, " + eC + " equipos.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static void registrarEnLog(String mensaje) {
        try (FileWriter fw = new FileWriter(RUTA_LOG, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(mensaje);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

    private static void menuModificaciones(EscapeHouse escapeHouse, Scanner scanner) {
        int opcion;
        do {
            menuModificaciones();
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el código habitación: ");
                    int codH = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nomH = scanner.nextLine();
                    System.out.print("Ingrese la planta (número): ");
                    int planta = scanner.nextInt();
                    System.out.print("Ingrese la superficie (m2): ");
                    double mts = scanner.nextDouble();
                    scanner.nextLine(); 

                    Habitacion nuevaHab = new Habitacion(codH, nomH, planta, mts, false);

                    if (escapeHouse.insertarHabitacion(nuevaHab)){
                        System.out.println("Se agregó la habitación correctamente.");
                        registrarEnLog("Se agregó la habitación: " + codH + ", " + nomH);
                    } else {
                        System.out.println("Error al agregar la habitación. Verifique los datos.");
                        registrarEnLog("Error al agregar la habitación: " + codH + ", " + nomH);

                    }

                    break;
                case 2:
                    System.out.print("Ingrese el código de habitación a modificar: ");
                    int codModH = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nuevo nombre: ");
                    String nNombre = scanner.nextLine();
                    System.out.print("Ingrese la nueva planta: ");
                    int nPlanta = scanner.nextInt();
                    System.out.print("Ingrese los nuevos metros cuadrados: ");
                    double nMts = scanner.nextDouble();

                    if (escapeHouse.modificarHabitacion(nNombre, nPlanta, nMts, codModH)) {
                        System.out.println("Habitación modificada correctamente.");
                        registrarEnLog("Habitación modificada: " + codModH + ", " + nNombre);
                    } else {
                        System.out.println("Error al modificar la habitación. Verifique los datos.");
                        registrarEnLog("Error al modificar la habitación: " + codModH + ", " + nNombre);
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el código de habitación a eliminar: ");
                    int codElimH = scanner.nextInt();
                    if (escapeHouse.eliminarHabitacion(codElimH)){
                        System.out.println("Habitación eliminada correctamente.");
                        registrarEnLog("Habitación eliminada: " + codElimH);
                    } else {
                        System.out.println("Error al eliminar la habitación. Verifique los datos.");
                        registrarEnLog("Error al eliminar la habitación: " + codElimH);
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el puntaje del desafío: ");
                    int pjeD = scanner.nextInt();
                    System.out.print("Ingrese el código de habitación asignada: ");
                    int habD = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nombre del desafío: ");
                    String nomD = scanner.nextLine();
                    System.out.print("Ingrese el tipo del desafío (lógico, matemático, búsqueda, destreza, ingenio): ");
                    String tipoD = scanner.nextLine();

                    Desafio nuevoD = new Desafio(pjeD, habD, nomD, tipoD);

                    if (escapeHouse.agregarDesafio(nuevoD)){
                        System.out.println("Desafío agregado correctamente.");
                        registrarEnLog("Desafío agregado: " + nomD);
                    } else {
                        System.out.println("Error al agregar el desafío. Verifique los datos.");
                        registrarEnLog("Error al agregar el desafío: " + nomD);
                    }

                    break;

                case 5:
                    System.out.print("Ingrese el puntaje del desafío a modificar: ");
                    int pjeModD = scanner.nextInt();
                    System.out.print("Ingrese el código de habitación donde está: ");
                    int habModD = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nuevo nombre del desafío: ");
                    String nuevoNomD = scanner.nextLine();
                    System.out.print("Ingrese el nuevo tipo del desafío: ");
                    String nuevoTipoD = scanner.nextLine();
                    if (escapeHouse.modificarDesafio(nuevoNomD, nuevoTipoD, pjeModD, habModD)) {
                        System.out.println("Desafío modificado correctamente.");
                        registrarEnLog("Desafío modificado: " + pjeModD + ", " + habModD);
                    } else {
                        System.out.println("Error al modificar el desafío. Verifique los datos.");
                        registrarEnLog("Error al modificar el desafío: " + pjeModD + ", " + habModD);
                    }

                    break;
                case 6:
                    System.out.print("Ingrese el puntaje del desafío a eliminar: ");
                    int ptsDel = scanner.nextInt();
                    System.out.print("Ingrese el código de habitación donde está: ");
                    int habDel = scanner.nextInt();

                    if (escapeHouse.eliminarDesafio(ptsDel, habDel)) {
                        System.out.println("Desafío eliminado correctamente.");
                        registrarEnLog("Desafío eliminado: " + ptsDel + ", " + habDel);
                    } else {
                        System.out.println("Error al eliminar el desafío. Verifique los datos.");
                        registrarEnLog("Error al eliminar el desafío: " + ptsDel + ", " + habDel);
                    }
                    break;
                case 7:
                    System.out.print("Ingrese el código Habitación Origen: ");
                    int hOrigen = scanner.nextInt();
                    System.out.print("Ingrese el código Habitación Destino: ");
                    int hDestino = scanner.nextInt();
                    System.out.print("Ingrese el puntaje exigido para pasar: ");
                    int ptsPuerta = scanner.nextInt();

                    if (escapeHouse.insertarPuerta(hOrigen, hDestino, ptsPuerta)) {
                        System.out.println("Puerta agregada correctamente.");
                        registrarEnLog("Puerta agregada: " + hOrigen + " -> " + hDestino);
                    } else {
                        System.out.println("Error al agregar la puerta. Verifique los datos.");
                        registrarEnLog("Error al agregar la puerta: " + hOrigen + " -> " + hDestino);
                    }

                    break;

                case 8:
                    System.out.print("Ingrese el código Habitación Origen: ");
                    hOrigen = scanner.nextInt();
                    System.out.print("Ingrese el código Habitación Destino: ");
                    hDestino = scanner.nextInt();

                    if(escapeHouse.eliminarPuerta(hOrigen, hDestino)) {
                        System.out.println("Puerta eliminada correctamente.");
                        registrarEnLog("Puerta eliminada: " + hOrigen + " -> " + hDestino);
                    } else {
                        System.out.println("Error al eliminar la puerta. Verifique los datos.");
                        registrarEnLog("Error al eliminar la puerta: " + hOrigen + " -> " + hDestino);
                    }

                    break;
                case 9:
                    System.out.print("Ingrese el nombre del equipo: ");
                    String nomEq = scanner.nextLine();
                    System.out.print("Ingrese el puntaje exigido para salir: ");
                    int ptsExigidos = scanner.nextInt();

                    Equipo nuevoEq = new Equipo(nomEq, ptsExigidos, 0, -1, 0);

                    if (escapeHouse.agregarEquipo(nuevoEq)) {
                        System.out.println("Equipo agregado correctamente.");
                        registrarEnLog("Equipo agregado: " + nomEq);
                    } else {
                        System.out.println("Error al agregar el equipo. Verifique los datos.");
                        registrarEnLog("Error al agregar el equipo: " + nomEq);
                    }
                    
                    break;
                case 10:
                    System.out.print("Ingrese el nombre del equipo a modificar: ");
                    String nomEqMod = scanner.nextLine();
                    System.out.print("Ingrese el nuevo puntaje exigido para salir: ");
                    int nuevoPtsExigidos = scanner.nextInt();
                    if(escapeHouse.modificarEquipo(nomEqMod, nuevoPtsExigidos)) {
                        System.out.println("Equipo modificado correctamente.");
                        registrarEnLog("Equipo modificado: " + nomEqMod);
                    } else {
                        System.out.println("Error al modificar el equipo. Verifique los datos.");
                        registrarEnLog("Error al modificar el equipo: " + nomEqMod);
                    }
                    break;
                case 11:
                    System.out.print("Ingrese el nombre del equipo a eliminar: ");
                    String nomEqElim = scanner.nextLine();
                    if (escapeHouse.eliminarEquipo(nomEqElim)) {
                        System.out.println("Equipo eliminado correctamente.");
                        registrarEnLog("Equipo eliminado: " + nomEqElim);
                    } else {
                        System.out.println("Error al eliminar el equipo. Verifique los datos.");
                        registrarEnLog("Error al eliminar el equipo: " + nomEqElim);
                    }
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion < 0 || opcion > 11);
    }

    private static void menuConsultasHabitaciones(EscapeHouse escapeHouse, Scanner scanner) {
        System.out.println("=== Menú de Consultas sobre Habitaciones ===");
        System.out.println("1. Mostrar habitación");
        System.out.println("2. Ver habitaciones contiguas");
        System.out.println("3. ¿Es posible llegar desde una habitación a otra con X puntos?");
        System.out.println("4. Mínimo puntaje entre dos habitaciones");
        System.out.println("5. Camino entre 2 habitaciones sin pasar por X, con un máximo de P puntos");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        int op;
        do {
            op = scanner.nextInt();
            scanner.nextLine();
        
            switch (op) {
                case 1:
                    System.out.print("Ingrese el código de la habitación: ");
                    int c1 = scanner.nextInt();
                    System.out.println(escapeHouse.mostrarHabitacion(c1));
                    break;
                case 2:
                    System.out.print("Ingrese el código de la habitación: ");
                    int c2 = scanner.nextInt();
                    System.out.println(escapeHouse.habitacionesContiguas(c2));
                    break;
                case 3:
                    System.out.print("Ingrese el código de la habitación de origen: ");
                    int hO = scanner.nextInt();
                    System.out.print("Ingrese el código de la habitación de destino: ");
                    int hD = scanner.nextInt();
                    System.out.print("Ingrese la cantidad de puntos: ");
                    int k = scanner.nextInt();
                    boolean sePuede = escapeHouse.esPosibleLlegar(hO, hD, k);
                    if (sePuede) {
                        System.out.println("Es posible llegar de la habitación " + hO + " a la habitación " + hD + " con " + k + " puntos.");
                    } else {
                        System.out.println("No es posible llegar de la habitación " + hO + " a la habitación " + hD + " con " + k + " puntos.");
                    }
                    break;

                case 4:

                    System.out.print("Ingrese el código de la habitación origen: ");
                    int hO2 = scanner.nextInt();
                    System.out.print("Ingrese el código de la habitación destino: ");
                    int hD2 = scanner.nextInt();
                    System.out.println((String) escapeHouse.minimoPuntaje(hO2, hD2));
                    break;

                case 5:
                    System.out.print("Ingrese el código de la habitación origen: ");
                    int hO3 = scanner.nextInt();
                    System.out.print("Ingrese el código de la habitación destino: ");
                    int hD3 = scanner.nextInt();
                    System.out.print("Ingrese el código de la habitación a evitar: ");
                    int hEvitar = scanner.nextInt();
                    System.out.println("Ingrese la cantidad máxima de puntos: ");
                    int p = scanner.nextInt();
                    System.out.println((String) escapeHouse.sinPasarPor(hO3, hD3, hEvitar, p));
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (op < 0 || op > 5);
    
    }

    private static void menuConsultasDesafios(EscapeHouse escapeHouse, Scanner scanner) {
        System.out.println("=== Menú de consultas sobre desafíos ===");
        System.out.println("1. Mostrar desafío");
        System.out.println("2. Ver los desafíos resueltos por un equipo");
        System.out.println("3. Verificar si un equipo ha resuelto un desafío");
        System.out.println("4. Mostrar todos los desafíos de un tipo con un puntaje en un rango [min,max]");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        int op;
        do {
            op = scanner.nextInt();
            scanner.nextLine();
        
            switch (op) {
                case 1:
                    System.out.println("Ingrese el puntaje del desafío: ");
                    int pjeD = scanner.nextInt();
                    System.out.print("Ingrese el código de la habitación del desafío: ");
                    int codD = scanner.nextInt();
                    System.out.println((String)escapeHouse.mostrarDesafio(pjeD, codD));
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del equipo: ");
                    String nomEq = scanner.nextLine();
                    System.out.println((String)escapeHouse.mostrarDesafiosResueltos(nomEq));
                    break;
                case 3:
                    System.out.println("Ingrese el nombre del equipo: ");
                    String nomEq3 = scanner.nextLine();
                    System.out.println("Ingrese el código de la habitación del desafío: ");
                    int codD3 = scanner.nextInt();
                    System.out.println("Ingrese el puntaje del desafío: ");
                    int pjeD3 = scanner.nextInt();

                    boolean resuelto = escapeHouse.verificarDesafioResuelto(nomEq3, pjeD3, codD3);
                    if (resuelto) {
                        System.out.println("El equipo ha resuelto el desafío");
                    } else {
                        System.out.println("El equipo no ha resuelto el desafío");
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el código de la habitación: ");
                    int codH4 = scanner.nextInt();
                    System.out.println("Ingrese el puntaje mínimo: ");
                    int pjeMin = scanner.nextInt();
                    System.out.println("Ingrese el puntaje máximo: ");
                    int pjeMax = scanner.nextInt();
                    System.out.println("Ingrese el tipo de desafío: ");
                    String tipoD4 = scanner.nextLine();
                    System.out.println(escapeHouse.mostrarDesafiosTipo(codH4, pjeMin, pjeMax, tipoD4));

                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (op < 0 || op >4);
        
    }

    private static void menuEquipos(EscapeHouse escapeHouse, Scanner scanner) {

        System.out.println("=== Menú de consultas sobre equipos ===");
        System.out.println("1. Mostrar equipo");
        System.out.println("2. Mostrar posibles desafíos que un equipo puede resolver para pasar a otra habitación");
        System.out.println("3. Marcar como ganado un desafío para un equipo");
        System.out.println("4. Cambiar de habitación a un equipo");
        System.out.println("5. Verificar si un equipo puede salir del escape house");
        System.out.println("6. Hacer salir un equipo de la casa");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");
        int op;
        do {
            op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
            case 1:
                System.out.println("Ingrese el nombre del equipo: ");
                String nombreEquipo = scanner.nextLine();
                System.out.println(escapeHouse.mostrarInfoEquipos(nombreEquipo));
                break;

            case 2:
                System.out.println("Ingrese el nombre del equipo: ");
                String nombreEquipo2 = scanner.nextLine();
                System.out.println("Ingrese el código de la habitación destino: ");
                int codigoHabitacionDestino = scanner.nextInt();
                System.out.println(escapeHouse.posiblesDesafios(nombreEquipo2, codigoHabitacionDestino));
                break;

            case 3:
                System.out.println("Ingrese el nombre del equipo: ");
                String nombreEquipo3 = scanner.nextLine();
                System.out.println("Ingrese el puntaje del desafío: ");
                int puntajeDesafio = scanner.nextInt();
                boolean seResolvio = escapeHouse.jugarDesafio(nombreEquipo3, puntajeDesafio);
                if(seResolvio){
                    System.out.println("El desafio se resolvio correctamente");
                    registrarEnLog("El equipo "+ nombreEquipo3 + " resolvió el desafío con puntaje " + puntajeDesafio + " de la habitación");
                }else{
                    System.out.println("El desafio no se resolvio");
                    registrarEnLog("El equipo "+ nombreEquipo3 + " no resolvió el desafío con puntaje " + puntajeDesafio + " de la habitación");
                }
                break;

            case 4:
                System.out.println("Ingrese el nombre del equipo: ");
                String nombreEquipo4 = scanner.nextLine();
                System.out.println("Ingrese el código de la habitación destino: ");
                int codHabDestino = scanner.nextInt();
                boolean seCambio = escapeHouse.cambiarDeHabitacion(nombreEquipo4, codHabDestino);
                if(seCambio){
                    System.out.println("El equipo se cambio de habitacion correctamente");
                    registrarEnLog("El equipo " + nombreEquipo4 + " se cambió de habitación a " + codHabDestino);
                }else{
                    System.out.println("El equipo no se pudo cambiar de habitacion");
                    registrarEnLog("El equipo " + nombreEquipo4 + " no se cambió de habitación a " + codHabDestino);
                }
                break;
            case 5:
                System.out.println("Ingrese el nombre del equipo: ");
                String nombreEquipo5 = scanner.nextLine();
                boolean puede = escapeHouse.puedeSalir(nombreEquipo5);
                if (puede){
                    System.out.println("El equipo "+ nombreEquipo5 + " puede salir");
                }else{
                    System.out.println("El equipo "+ nombreEquipo5 +" no puede salir");
                }
                break;
            case 6:
                System.out.println("ingrese el nombre del equipo");
                String nombreEquipo6 = scanner.nextLine();
                boolean puedeSalir = escapeHouse.puedeSalir(nombreEquipo6);
                if(puedeSalir){
                    escapeHouse.eliminarEquipo(nombreEquipo6);
                    System.out.println("El equipo "+ nombreEquipo6 + " salió de la casa");
                    registrarEnLog("El equipo "+ nombreEquipo6 + " salió de la casa");
                }else{
                    System.out.println("El equipo " + nombreEquipo6 + " no puede salir de la casa aún");
                }
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (op < 0 || op > 5);
    }
}