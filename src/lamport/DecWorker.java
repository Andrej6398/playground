package lamport;

public class DecWorker implements Runnable{

    private MyLock lock;

    public DecWorker(MyLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for(int i = 0; i < Main.ITERATIONS; i++){
            lock.lock();
            Main.x--;
            lock.unlock();
        }
    }
}
