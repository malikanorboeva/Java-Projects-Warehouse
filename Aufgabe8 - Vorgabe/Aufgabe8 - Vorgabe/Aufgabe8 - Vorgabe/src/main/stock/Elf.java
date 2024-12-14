package main.stock;

import main.goods.Pallet;
import main.manager.StockManager;

public class Elf implements IStockWorker {

    @Override
    public String computeNextOperation(int x, int y, Pallet[] registeredPallets, Stack[] stacks, Pallet inventory, Pallet requested) {
        // If we have no requested pallet, nothing to do
        if (requested == null) {
            return "";
        }

        // If we have the requested pallet, move to delivery zone
        if (inventory != null && inventory.equals(requested)) {
            if (x < 2 * StockManager.WIDTH) return "E";
            if (y > 0) return "N";
            return "D";
        }

        // If we have wrong pallet, find empty stack to place it
        if (inventory != null && !inventory.equals(requested)) {
            // First move to stack area if not there
            if (x < StockManager.WIDTH) return "E";
            if (x > StockManager.WIDTH) return "W";

            // Find first available stack
            for (int i = 0; i < stacks.length; i++) {
                if (stacks[i].isPlacingPossible(inventory)) {
                    if (y < i) return "S";
                    if (y > i) return "N";
                    return "D";
                }
            }
            if (y < stacks.length - 1) return "S";
            return "N";
        }

        // Look for requested pallet in stacks
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < StockManager.STACKHEIGHT; j++) {
                if (stacks[i].get(j) != null && stacks[i].get(j).equals(requested)) {
                    // Move to correct position
                    if (x > StockManager.WIDTH) return "W";
                    if (x < StockManager.WIDTH) return "E";
                    if (y < i) return "S";
                    if (y > i) return "N";
                    return "P";
                }
            }
        }

        return "";
    }
}