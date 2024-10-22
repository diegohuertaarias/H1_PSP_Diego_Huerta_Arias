package org.example;

import java.util.LinkedList;
import java.util.Queue;

class ColaExamenes {
    private final Queue<Integer> cola = new LinkedList<>();
    private final int LIMITE = 5; // Limite de exámenes en la cola

    // Método sincronizado para añadir exámenes a la cola
    public synchronized void agregarExamen(int examen) throws InterruptedException {
        while (cola.size() == LIMITE) {
            wait(); // Si la cola está llena, el generador espera
        }
        cola.add(examen);
        System.out.println("Examen generado: " + examen);
        notifyAll(); // Notificamos a los consumidores que hay un examen disponible
    }

    // Método sincronizado para consumir exámenes de la cola
    public synchronized int consumirExamen() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); // Si la cola está vacía, el consumidor espera
        }
        int examen = cola.poll();
        System.out.println("Examen consumido: " + examen);
        notifyAll(); // Notificamos a los generadores que hay espacio en la cola
        return examen;
    }
}
