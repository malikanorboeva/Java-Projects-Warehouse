package main.stock;

import main.goods.Pallet;

public interface IStockWorker {

    /**
     * Computes and returns next operation of worker based on a given situation.
     * @param x - position of worker
     * @param y - position of worker
     * @param registeredPallets - pallets on conveyor
     * @param stacks - current stacks
     * @param inventory - current inventory or null if empty
     * @param requested - requested pallet
     * @return commands: "N" (orth), "S" (outh), "E" (ast), "W" (est), "P" (ick), "D" (rop), "" (no action)
     */
    public default String computeNextOperation(int x, int y, Pallet[] registeredPallets, Stack[] stacks, Pallet inventory, Pallet requested) {
        for (int i = 0; i < stacks.length; i++) {
            if (y>i) {
                return "N";
            }
            if (y<i) {
                return "S";
            }
            if (x>i) {
                return "W";
            }
            if (x<i) {
                return "E";
            }
            if (inventory==null && x==i && y==i) {
                inventory = requested;
                return "P";
            }
            if (inventory!=null && x==10 && y==0) {
                if (stacks[i].isPlacingPossible(requested)) {
                    return "D";
                } else return "S";
            }
        }
        return null;
    }
}
