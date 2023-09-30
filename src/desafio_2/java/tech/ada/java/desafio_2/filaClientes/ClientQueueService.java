package tech.ada.java.desafio_2.filaClientes;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import tech.ada.java.desafio_2.filaClientes.ClientQueue;

@Service
public class ClientQueueService {
    private ClientQueue clientQueue;

    @PostConstruct
    public void init() {
<<<<<<< HEAD
        this.clientQueue = new ClientQueue(100); // Initial capacity
=======
        this.clientQueue = new ClientQueue(100);
>>>>>>> 31cc895 (quase la)
    }

    public void addToQueue(long clientId) {
        clientQueue.enqueue(clientId);
    }

    public long removeFromQueue() {
        return clientQueue.dequeue();
    }

    public boolean isQueueEmpty() {
        return clientQueue.isEmpty();
    }
}


