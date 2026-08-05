package TPFinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class GestorArchivos {

    public static void cargarSistemaDesdeArchivo (String rutaArchivo, EscapeHouse escapeHouse) {
        System.out.println("Cargando datos del sistema desde: " + rutaArchivo);
        int habCargadas = 0, desCargados = 0, puertasCargadas = 0, eqCargados = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                // se omiten las líneas vacías
                if (linea.isEmpty() || linea.startsWith("//")) {
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length == 0) continue;

                String tipoRegistro = partes[0].trim().toUpperCase();

                switch (tipoRegistro) {
                    case "H":
                        if (partes.length >= 6) {
                            try {
                                int codigo = Integer.parseInt(partes[1].trim());
                                String nombre = partes[2].trim();
                                int planta = Integer.parseInt(partes[3].trim());
                                double metros = Double.parseDouble(partes[4].trim());
                                boolean tieneSalida = Boolean.parseBoolean(partes[5].trim());

                                Habitacion hab = new Habitacion(codigo, nombre, planta, metros, tieneSalida);
                                if (escapeHouse.insertarHabitacion(hab)) {
                                    habCargadas++;
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error de formato numérico en línea (Habitación): " + linea);
                            }
                        }
                        break;

                    case "D":
                        if (partes.length >= 5) {
                            try {
                                int puntaje = Integer.parseInt(partes[1].trim());
                                int codHab = Integer.parseInt(partes[2].trim());
                                String nombre = partes[3].trim();
                                String tipo = partes[4].trim();

                                Desafio des = new Desafio(puntaje, codHab, nombre, tipo);
                                if (escapeHouse.agregarDesafio(des)) {
                                    desCargados++;
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error de formato numérico en línea (Desafío): " + linea);
                            }
                        }
                        break;

                    case "P":
                        if (partes.length >= 4) {
                            try {
                                int habOrigen = Integer.parseInt(partes[1].trim());
                                int habDestino = Integer.parseInt(partes[2].trim());
                                int puntajeExigido = Integer.parseInt(partes[3].trim());

                                if (escapeHouse.insertarPuerta(habOrigen, habDestino, puntajeExigido)) {
                                    puertasCargadas++;
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error de formato numérico en línea (Puerta): " + linea);
                            }
                        }
                        break;

                    case "E":
                        if (partes.length >= 6) {
                            try {
                                String nombre = partes[1].trim();
                                int puntajeExigido = Integer.parseInt(partes[2].trim());
                                int puntajeTotal = Integer.parseInt(partes[3].trim());
                                int habActual = Integer.parseInt(partes[4].trim());
                                int puntajeHab = Integer.parseInt(partes[5].trim());

                                Equipo eq = new Equipo(nombre, puntajeExigido, puntajeTotal, habActual, puntajeHab);
                                if (escapeHouse.agregarEquipo(eq)) {
                                    eqCargados++;
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error de formato numérico en línea (Equipo): " + linea);
                            }
                        }
                        break;

                    default:
                        System.err.println("Tipo de registro no reconocido en línea: " + linea);
                        break;
                }
            }
            System.out.println("--- Carga finalizada con éxito ---");
            System.out.println("Habitaciones cargadas: " + habCargadas);
            System.out.println("Desafíos cargados: " + desCargados);
            System.out.println("Puertas cargadas: " + puertasCargadas);
            System.out.println("Equipos cargados: " + eqCargados);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de datos: " + e.getMessage());
        }
    }

    public static void registrarEnLog(String rutaLog, String mensaje) {
        try (FileWriter fw = new FileWriter(rutaLog, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(mensaje);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

}



    

