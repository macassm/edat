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
    private static final String RUTA_DATOS = "datos_iniciales.txt";

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
                    // Lógica para consultas sobre habitaciones 
                    break;
                case 4:
                    // Lógica para consultas sobre desafíos
                    break;
                case 5:
                    // Lógica para acciones y consultas sobre equipos
                    break;
                case 6:
                    // mostrar sistema
                    break;
                case 0:
                    System.out.println("Saliendo del programa");
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
        System.out.println("2. Cargar, modificar o eliminar (Habitaciones, Desafíos, Puertas y Equipos)");
        System.out.println("3. Consultas sobre habitaciones");
        System.out.println("4. Consultas sobre desafíos");
        System.out.println("5. Acciones y Consultas sobre equipos");
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
            System.out.println("8. Modificar una puerta");
            System.out.println("9. Eliminar una puerta");
            System.out.println("10. Agregar un equipo");
            System.out.println("11. Modificar un equipo");
            System.out.println("12. Eliminar un equipo");
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

                String[] p = linea.split(";");
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
                    System.out.print("Código habitación: ");
                    int codH = scanner.nextInt();
                    System.out.print("Nombre: ");
                    String nomH = scanner.nextLine();
                    System.out.print("Planta (número): ");
                    int planta = scanner.nextInt();
                    System.out.print("Superficie (m2): ");
                    double mts = scanner.nextDouble();
                    System.out.print("¿Tiene salida al exterior? (true/false): ");
                    boolean tieneSalida = scanner.nextBoolean();
                    scanner.nextLine(); 

                    Habitacion nuevaHab = new Habitacion(codH, nomH, planta, mts, tieneSalida);

                    if (escapeHouse.insertarHabitacion(nuevaHab)){
                        System.out.println("Se agregó la habitación correctamente.");
                        registrarEnLog("Se agregó la habitación: " + codH + ", " + nomH);
                    } else {
                        System.out.println("Error al agregar la habitación. Verifique los datos.");
                        registrarEnLog("Error al agregar la habitación: " + codH + ", " + nomH);

                    }

                    break;
                case 2:
                    System.out.print("Código de habitación a modificar: ");
                    int codModH = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nuevo Nombre: ");
                    String nNombre = scanner.nextLine();
                    System.out.print("Nueva Planta: ");
                    int nPlanta = scanner.nextInt();
                    System.out.print("Nuevos Metros Cuadrados: ");
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
                    System.out.print("Código de habitación a eliminar: ");
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
                    System.out.print("Puntaje del desafío: ");
                    int pjeD = scanner.nextInt();
                System.out.print("Código de habitación asignada: ");
                int habD = scanner.nextInt();
                System.out.print("Nombre del desafío: ");
                String nomD = scanner.nextLine();
                System.out.print("Tipo del desafío (lógico, matemático, búsqueda, destreza, ingenio): ");
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
                    // Lógica para modificar desafío
                    break;
                case 6:
                    System.out.print("Puntaje del desafío a eliminar: ");
                    int ptsDel = scanner.nextInt();
                    System.out.print("Código de habitación donde está: ");
                    int habDel = scanner.nextInt();


                    break;
                case 7:
                    // Lógica para agregar puerta
                    break;
                case 8:
                    // Lógica para modificar puerta
                    break;
                case 9:
                    // Lógica para eliminar puerta
                    break;
                case 10:
                    // Lógica para agregar equipo
                    break;
                case 11:
                    // Lógica para modificar equipo
                    break;
                case 12:
                    // Lógica para eliminar equipo
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }
}
