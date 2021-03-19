package irena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        WaitNotify waitNotify = new WaitNotify();
        Thread[] threads = new Thread[THREAD_COUNT];
        Semaphore semaphore = new Semaphore(3);

        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT, () -> {
            System.out.println("Irena zna kids najbolje na svetu");
        });

        for(int i = 0; i < THREAD_COUNT; i++){
            threads[i] = new Thread(new Barrier(cyclicBarrier));
        }

        for (int i = 0; i < THREAD_COUNT; i++){
            threads[i].start();
        }

        List<String> list = Collections.synchronizedList(new ArrayList<>());
    }
}
