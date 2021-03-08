package CLH;

public class Ticket {
    private volatile boolean locked;

    public Ticket() {
        locked = false;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }
}
