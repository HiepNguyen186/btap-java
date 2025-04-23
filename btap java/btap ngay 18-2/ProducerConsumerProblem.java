package a;

import java.lang.Thread;
import java.lang.Object;

class Store {
    private int product = 0;
    private final int CAPACITY = 50; 

       public synchronized int get() {
        while (product == 0) {
            try {
                System.out.println("Consumer is waiting for new product...");
                wait();             } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product--; 
        System.out.println(" Consumer bought 1 product. Remaining: " + product);
        notify(); 
        return product;
    }

 
    public synchronized void put() {
        while (product >= CAPACITY) {
            try {
                System.out.println("Producer is waiting, store is full...");
                wait(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product++;
        System.out.println("Producer added 1 product. Stock: " + product);
        notify(); 
    }
}

class Producer extends Thread {
    private Store store;

    public Producer(Store store) { 
        this.store = store;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); 
                store.put(); 
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1500); 
                store.get(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProducerConsumerProblem {
    public static void main(String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        producer.start();
        consumer.start();
    }
}
