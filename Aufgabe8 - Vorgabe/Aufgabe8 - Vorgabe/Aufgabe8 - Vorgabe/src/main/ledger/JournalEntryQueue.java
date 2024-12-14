package main.ledger;

import java.util.concurrent.ArrayBlockingQueue;
import main.goods.Pallet;

public class JournalEntryQueue {
    private final ArrayBlockingQueue<Pallet> queue;

    public JournalEntryQueue(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public boolean addEntry(Pallet entry) {
        return queue.offer(entry);
    }

    public Pallet removeFirst() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }
}
