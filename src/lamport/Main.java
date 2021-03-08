package lamport;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static int x = 0;
    public static int THREADS_COUNT = 10;
    public static int ITERATIONS = 1000000;

    public static void main(String[] args) throws InterruptedException {
        MyLock lock = new Lamport(THREADS_COUNT);

        Thread[] threads = new Thread[THREADS_COUNT];

        for (int i = 0; i < THREADS_COUNT; i++){
            if( i % 2 == 0){
                threads[i] = new Thread(new IncWorker(lock));
            }else {
                threads[i] = new Thread(new DecWorker(lock));
            }
            threads[i].setName(Integer.toString(i));
        }
        AtomicBoolean locked = new AtomicBoolean(false);
        locked.compareAndSet(false, true);
        for (Thread t: threads) {
            t.start();
        }

        for (Thread t: threads) {
            t.join();
        }

        System.out.println("X: " + x);
    }
}
