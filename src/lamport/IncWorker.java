package lamport;

public class IncWorker implements Runnable{

    private MyLock lock;

    public IncWorker(MyLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for(int i = 0; i < Main.ITERATIONS; i++){
            lock.lock();
            Main.x++;
            lock.unlock();
        }
    }
}
