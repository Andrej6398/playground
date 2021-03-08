package lamport;

public class Lamport implements MyLock{

    private final int n;
    private volatile boolean[] flags;
    private volatile int[] tickets;

    public Lamport(int n) {
        flags = new boolean[n];
        tickets = new int[n];
        this.n = n;
    }

    @Override
    public void lock() {
        int id = Integer.parseInt(Thread.currentThread().getName());

        flags[id] = true;
        flags = flags;

        int max = -1;
        for(int i = 0; i < n; i++){
            if(tickets[i] > max){
                max = tickets[i];
            }
        }
        tickets[id] = max + 1;
        tickets = tickets;

        boolean spin = true;

        while (spin){
            spin = false;
            for (int j = 0; j < n; j++){
                if(j != id){
                    if (flags[j]) {
                        if (tickets[j] < tickets[id]) {
                            spin = true;
                            break;
                        }
                        if (tickets[j] == tickets[id]){
                            if(j < id){
                                spin = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void unlock() {
        int id = Integer.parseInt(Thread.currentThread().getName());
        flags[id] = false;
    }
}
