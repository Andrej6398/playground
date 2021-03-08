package CLH;

public class IncWorker implements Runnable{

    private final Lock lock;

    public IncWorker(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for(int i = 0; i < CLHTest.ITERATIONS; i++){
            lock.lock();
            CLHTest.counter++;
            lock.unlock();
        }
    }
}
