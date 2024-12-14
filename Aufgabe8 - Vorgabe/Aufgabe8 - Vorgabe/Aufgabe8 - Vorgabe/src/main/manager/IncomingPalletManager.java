package main.manager;

import main.goods.Pallet;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Arrays;

public class IncomingPalletManager {

    private volatile Pallet[] pallets;
    private final ReentrantLock lock = new ReentrantLock();

    public IncomingPalletManager() {
        pallets = new Pallet[0];
    }

    public synchronized Pallet[] getPallets() {
        return Arrays.copyOf(pallets, pallets.length);
    }

    public void registerPallets(Pallet... newPallets) {
        if (newPallets == null || newPallets.length == 0) {
            return;
        }

        lock.lock();
        try {
            // Remove leading nulls first
            int firstNonNull = 0;
            while (firstNonNull < pallets.length && pallets[firstNonNull] == null) {
                firstNonNull++;
            }

            Pallet[] cleanedPallets;
            if (firstNonNull > 0) {
                cleanedPallets = Arrays.copyOfRange(pallets, firstNonNull, pallets.length);
            } else {
                cleanedPallets = pallets;
            }

            // Create new array with additional capacity
            Pallet[] newArray = Arrays.copyOf(cleanedPallets, cleanedPallets.length + newPallets.length);
            System.arraycopy(newPallets, 0, newArray, cleanedPallets.length, newPallets.length);

            this.pallets = newArray;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a given Pallet from registered pallets
     * @param pallet - pallet to remove
     * @return removed pallet or null (if given pallet wasn't in array)
     */
    public Pallet removePallet(Pallet pallet) {
        if (pallet == null) return null;

        lock.lock();
        try {
            for (int i = 0; i < pallets.length; i++) {
                if (pallets[i] != null && pallets[i].equals(pallet)) {
                    Pallet removed = pallets[i];
                    pallets[i] = null;
                    return removed;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }
}