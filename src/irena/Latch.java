package irena;

import java.util.concurrent.CountDownLatch;

public class Latch implements Runnable{

    private final CountDownLatch latch;

    public Latch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown();

        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
