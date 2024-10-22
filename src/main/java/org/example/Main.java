package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Verificamos si hay argumentos
        if (args.length > 0) {
            System.out.println("Argumento recibido: " + args[0]);

            // Lógica para manejar el argumento
            if ("TipoA".equals(args[0])) {
                System.out.println("Generando exámenes TipoA...");
                ejecutarGestionExamenes();
            } else if ("TipoB".equals(args[0])) {
                System.out.println("Generando exámenes TipoB...");
                ejecutarGestionExamenes();
            } else {
                System.out.println("Argumento no reconocido.");
            }
            return; // Termina aquí si hay argumentos para que no continúe con el menú principal.
        }

        // Menú principal si no hay argumentos (Actividad 1)
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Elija la actividad (1: multitarea, 2: multiproceso, 3: finalizar): ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue; // Si se presiona Enter, muestra las opciones nuevamente
            }

            int actividad;

            try {
                actividad = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida.");
                continue;
            }

            if (actividad == 1) {
                // Actividad 1: multitarea
                ejecutarGestionExamenes();
            } else if (actividad == 2) {
                // Lanza dos procesos separados (TipoA y TipoB)
                try {
                    ProcessBuilder proceso1 = new ProcessBuilder("java", "", "Roja", "Blanca", "Azul");
                    ProcessBuilder proceso2 = new ProcessBuilder("java", "Carrera", "Negra", "Amarilla");;

                    Process p1 = proceso1.inheritIO().start();
                    Process p2 = proceso2.inheritIO().start();

                    p1.waitFor();
                    p2.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (actividad == 3) {
                running = false;
                System.out.println("Programa finalizado.");
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    // Método que ejecuta la gestión de exámenes (multitarea)
    private static void ejecutarGestionExamenes() {
        ColaExamenes colaExamenes = new ColaExamenes();

        Thread generador1 = new Thread(new Generador(colaExamenes));
        Thread generador2 = new Thread(new Generador(colaExamenes));
        Thread consumidor1 = new Thread(new Consumidor(colaExamenes));
        Thread consumidor2 = new Thread(new Consumidor(colaExamenes));

        generador1.start();
        generador2.start();
        consumidor1.start();
        consumidor2.start();

        System.out.println("Presione Enter para detener y volver al menú principal...");
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            generador1.interrupt();
            generador2.interrupt();
            consumidor1.interrupt();
            consumidor2.interrupt();
        }).start();

        try {
            generador1.join();
            generador2.join();
            consumidor1.join();
            consumidor2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
