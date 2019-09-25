package Procesos;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumerExample {
 
    
    
    public static void main(String[] args) {
        
        PC pc = new PC();
        
        Thread producer = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerConsumerExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Thread consumer = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerConsumerExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        producer.start();
        consumer.start();
    }
    
    public static class PC {
        
        Logger logger = Logger.getLogger(PC.class.getName());
        
        private LinkedList<Integer> buffer = new LinkedList<>();
        int capacity = 4;
        
        public void produce() throws InterruptedException {
            
            int value = 0;

            while (true) {
                synchronized (this) {
                    
                    while(buffer.size()==capacity) wait();
                    
                    buffer.add(++value);
                    logger.info(Thread.currentThread().getName() + " -> Produce: "+value);
                    
                    notifyAll();
                    
                    Thread.sleep(1000);
                }
            }
                
        }
        
        public void consume() throws InterruptedException {
            
            while (true) {
                synchronized (this) {
                    
                    while(buffer.size()==0) wait();
                    
                    int product = buffer.removeFirst();
                    logger.info(Thread.currentThread().getName() + " -> Consume: "+product);
                    
                    notify();
                    
                    Thread.sleep(1000);
                }
            }
                
        }
        
    }
        
}
