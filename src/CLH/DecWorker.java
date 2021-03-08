package CLH;

public class DecWorker implements Runnable{

    private final Lock lock;

    public DecWorker(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < CLHTest.ITERATIONS; i++){
            lock.lock();
            CLHTest.counter--;
            lock.unlock();
        }
    }
}
