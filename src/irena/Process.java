package irena;

import java.util.concurrent.locks.Lock;

public class Process {
    private int counter = 0;
    private int counter2 = 0;

    private final Lock lock;

    public Process(Lock lock){
        this.lock = lock;
    }

    public void increment(){
        lock.lock();
        counter++;
        lock.unlock();
    }

    public void decrement(){
        lock.lock();
        counter--;
        lock.unlock();
    }

    public int getCounter(){
        return counter;
    }

    public int getCounter2(){
        return counter2;
    }
}
