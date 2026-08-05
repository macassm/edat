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
                    // Lógica para eliminar habitación
                    break;
                case 3:
                    // Lógica para agregar puerta
                    break;
                case 4:
                    // Lógica para agregar desafío
                    break;
                case 5:
                    // Lógica para eliminar desafío
                    break;
                case 6:
                    // Lógica para agregar equipo
                    break;
                case 7:
                    // Lógica para mostrar información de habitación
                    break;
                case 8:
                    // Lógica para mostrar información de desafío
                    break;
                case 9:
                    // Lógica para mostrar información de equipo
                    break;
                case 10:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 10);

    
        
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
}
