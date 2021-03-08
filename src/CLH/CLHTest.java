package CLH;

public class CLHTest {

    public static final int THREAD_COUNT = 10;
    public static final int ITERATIONS = 1000000;
    public static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new CLHLock();
        Thread[] threads = new Thread[THREAD_COUNT];

        for(int i = 0; i < THREAD_COUNT; i++){
            if(i % 2 == 0){
                threads[i] = new Thread(new IncWorker(lock));
            }else {
                threads[i] = new Thread(new DecWorker(lock));
            }
        }

        long timeStart = System.currentTimeMillis();

        for (Thread t: threads) {
            t.start();
        }

        for (Thread t: threads) {
            t.join();
        }

        long totalTime = System.currentTimeMillis() - timeStart;

        System.out.println("Counter = " + counter + " Time in ms: " + totalTime);
    }
}
