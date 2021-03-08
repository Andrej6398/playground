package CLH;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock{

    AtomicReference<Ticket> tail;
    ThreadLocal<Ticket> me;
    ThreadLocal<Ticket> prev;

    public CLHLock() {
        tail = new AtomicReference<>(new Ticket());
        me = ThreadLocal.withInitial(Ticket::new);
        prev = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        me.get().setLocked(true);
        prev.set(tail.getAndSet(me.get()));

        while (prev.get().isLocked()){ }
    }

    @Override
    public void unlock() {
        me.get().setLocked(false);
        me.set(prev.get());
    }
}
