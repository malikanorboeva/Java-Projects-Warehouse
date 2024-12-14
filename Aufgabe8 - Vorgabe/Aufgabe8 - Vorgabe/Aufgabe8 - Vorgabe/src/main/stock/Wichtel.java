package main.stock;

import main.goods.Pallet;
import main.manager.StockManager;

public class Wichtel implements IStockWorker {

    @Override
    public String computeNextOperation(int x, int y, Pallet[] registeredPallets, Stack[] stacks, Pallet inventory, Pallet requested) {
        // If we have a pallet, try to store it
        if (inventory != null) {
            // Move to stack area if not there
            if (x < StockManager.WIDTH) return "E";

            // Find first available stack
            for (int i = 0; i < stacks.length; i++) {
                if (stacks[i].isPlacingPossible(inventory)) {
                    if (y < i) return "S";
                    if (y > i) return "N";
                    return "D";
                }
            }
            // If no suitable stack found, continue searching
            if (y < stacks.length - 1) return "S";
            return "N";
        }

        // Look for pallets to pick up
        if (registeredPallets != null) {
            for (int i = 0; i < registeredPallets.length; i++) {
                if (registeredPallets[i] != null) {
                    // Move to conveyor position
                    if (x > 0) return "W";
                    if (y < i) return "S";
                    if (y > i) return "N";
                    return "P";
                }
            }
        }

        // If no pallets found, move to starting position
        if (y > 0) return "N";
        if (x > 0) return "W";

        return "";
    }
}
