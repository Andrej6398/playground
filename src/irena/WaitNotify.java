package irena;

public class WaitNotify {
    private int value = 0;

    public void producer() {
        synchronized (this){
            while (true){
                if(value == 5){
                    System.out.println("Producer waiting");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println(value);
                    value++;
                    notifyAll();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void consumer(){
        synchronized (this){

            while (true){
                if(value == 0){
                    System.out.println("Consumer waiting");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println(value);
                    value--;
                    notifyAll();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
