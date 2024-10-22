package org.example;

class Generador implements Runnable {
    private final ColaExamenes colaExamenes;

    public Generador(ColaExamenes colaExamenes) {
        this.colaExamenes = colaExamenes;
    }

    @Override
    public void run() {
        int codigoExamen = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                colaExamenes.agregarExamen(++codigoExamen);
                Thread.sleep(1000); // Simulamos un retraso en la generación de exámenes
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Interrumpimos el hilo de forma segura
            }
        }
    }
}
