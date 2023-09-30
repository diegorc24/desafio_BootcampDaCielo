package tech.ada.java.desafio_2.filaClientes;

public class ClientQueue {
    private long[] queue;
    private int front;
    private int rear;
    private int size;

    public ClientQueue(int capacity) {
        this.queue = new long[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(long clientId) {
        if (isFull()) {
            // Double the size
            int newCapacity = 2 * queue.length;
            long[] newQueue = new long[newCapacity];
            for (int i = 0; i < queue.length; i++) {
                newQueue[i] = queue[(front + i) % queue.length];
            }
            front = 0;
            rear = size - 1;
            queue = newQueue;
        }

        rear = (rear + 1) % queue.length;
        queue[rear] = clientId;
        size++;
    }

    public long dequeue() {
        if (isEmpty()) {
            return -1;
        }
        long clientId = queue[front];
        front = (front + 1) % queue.length;
        size--;
        return clientId;
    }

    public long front() {
        if (isEmpty()) {
            return -1;
        }
        return queue[front];
    }

    public long rear() {
        if (isEmpty()) {
            return -1;
        }
        return queue[rear];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }
}


