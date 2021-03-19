package irena;

import java.util.concurrent.Semaphore;

public class Printers implements Runnable{

    private final Semaphore semaphore;

    public Printers(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Trazim stampace..." + Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stampac pronadjen " + Thread.currentThread().getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        semaphore.release();
        System.out.println("Stampanje gotovo " + Thread.currentThread().getName());
    }
}
