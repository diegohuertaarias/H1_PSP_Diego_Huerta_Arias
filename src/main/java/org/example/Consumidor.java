package org.example;

class Consumidor implements Runnable {
    private final ColaExamenes colaExamenes;

    public Consumidor(ColaExamenes colaExamenes) {
        this.colaExamenes = colaExamenes;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                colaExamenes.consumirExamen();
                Thread.sleep(2000); // Simulamos un retraso en el consumo de exámenes
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Interrumpimos el hilo de forma segura
            }
        }
    }
}
