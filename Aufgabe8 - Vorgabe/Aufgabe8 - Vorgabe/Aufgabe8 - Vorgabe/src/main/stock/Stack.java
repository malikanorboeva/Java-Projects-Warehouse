package main.stock;

import main.goods.Pallet;
import main.manager.StockManager;
import java.util.concurrent.locks.ReentrantLock;

public class Stack {
    private final Pallet[] stack = new Pallet[StockManager.STACKHEIGHT];
    private final ReentrantLock lock = new ReentrantLock();
    private volatile Accountant currentAccountant = null;

    // Legacy method for backward compatibility
    public boolean add(Pallet pallet) {
        if (!lock.tryLock()) {
            return false;
        }
        try {
            if (currentAccountant != null) {
                return false;
            }
            for (int i = 0; i < stack.length; i++) {
                if (stack[i] == null) {
                    if (i == 0 || canPlaceOnTop(pallet, stack[i-1])) {
                        stack[i] = pallet;
                        return true;
                    }
                    return false;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Legacy method for backward compatibility
    public Pallet remove() {
        if (!lock.tryLock()) {
            return null;
        }
        try {
            if (currentAccountant != null) {
                return null;
            }
            for (int i = stack.length - 1; i >= 0; i--) {
                if (stack[i] != null) {
                    Pallet removed = stack[i];
                    stack[i] = null;
                    return removed;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public boolean isPlacingPossible(Pallet pallet) {
        if (pallet == null) return false;

        lock.lock();
        try {
            if (isEmpty()) return true;

            for (int i = 0; i < stack.length - 1; i++) {
                if (stack[i] != null && stack[i+1] == null) {
                    return canPlaceOnTop(pallet, stack[i]);
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private boolean canPlaceOnTop(Pallet upper, Pallet lower) {
        return upper.getWidth() < lower.getWidth() &&
                upper.getDepth() < lower.getDepth();
    }

    private boolean isEmpty() {
        for (Pallet p : stack) {
            if (p != null) return false;
        }
        return true;
    }

    public Pallet get(int idx) {
        lock.lock();
        try {
            return (idx >= 0 && idx < stack.length) ? stack[idx] : null;
        } finally {
            lock.unlock();
        }
    }

    public boolean isLockedByAccountant(Accountant accountant) {
        return currentAccountant == accountant;
    }

    public void releaseAccountant(Accountant accountant) {
        if (currentAccountant == accountant) {
            currentAccountant = null;
        }
    }
}
