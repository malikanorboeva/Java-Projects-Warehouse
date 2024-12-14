package main.gui;

import main.goods.Pallet;
import main.manager.IncomingPalletManager;
import main.manager.StockManager;
import main.stock.Elf;
import main.stock.Stack;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ElfController extends Helper {

    /**
     * Elf
     */
    private final Elf elf = new Elf();

    /**
     * List of all ElfControllers
     */
    private static final CopyOnWriteArrayList<ElfController> elfList = new CopyOnWriteArrayList<>();

    /**
     * score
     */
    private static final AtomicInteger score = new AtomicInteger(0);

    public ElfController(IncomingPalletManager incomingPalletManager, Stack[] stacks) {
        x = StockManager.WIDTH * 2;
        this.incomingPalletManager = incomingPalletManager;
        this.stacks = stacks;
        elfList.add(this);
    }

    public void performElfOperation() {
        Pallet[] registeredPallets = incomingPalletManager.getPallets();
        assignRequestedPallet();

        String cmd = elf.computeNextOperation(x, y, registeredPallets, stacks, inventory, requestedPallet);
        if (cmd == null || cmd.length() == 0) {
            return;
        }
        System.out.println("Elf Command: " + cmd);
        switch (cmd.charAt(0)) {
            case 'N':
                if (y > 0) {
                    --y;
                }
                return;
            case 'S':
                if (y < stacks.length - 1) {
                    ++y;
                }
                return;
            case 'E':
                if (x < 2 * StockManager.WIDTH) {
                    ++x;
                }
                return;
            case 'W':
                if (x > StockManager.WIDTH) {
                    --x;
                }
                return;
            case 'P': //Pick
                if (x == StockManager.WIDTH && y >= 0 && y < stacks.length) {
                    if (stacks[y].get(0) != null) {
                        inventory = stacks[y].remove();
                    }
                }
                return;
            case 'D': //Drop
                if (x == 2 * StockManager.WIDTH && y == 0) {
                    if (inventory != null && inventory.equals(requestedPallet)) {
                        inventory = null;
                        requestedPallet = null;
                        score.incrementAndGet();
                        assignRequestedPallet();
                    }
                }
                if (x == StockManager.WIDTH) {
                    if (stacks[y].add(inventory)) {
                        inventory = null;
                    }
                }
                return;
            default:
                return;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void performElfOperationForEveryElf() {
        for (ElfController ec : elfList) {
            ec.performElfOperation();
        }
    }

    public static CopyOnWriteArrayList<ElfController> getElfList() {
        return elfList;
    }

    public static int getScore() {
        return score.get();
    }

    public Pallet getInventory() {
        return inventory;
    }

    private void assignRequestedPallet() {
        if (requestedPallet != null || stacks == null) {
            return;
        }
        ArrayList<Pallet> available = new ArrayList<>();
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < StockManager.STACKHEIGHT; j++) {
                if (stacks[i].get(j) != null) {
                    available.add(stacks[i].get(j));
                }
            }
        }

        if (available.isEmpty()) {
            Pallet[] registered = incomingPalletManager.getPallets();
            int length = Math.min(registered.length, 20);
            for (int i = 0; i < length; i++) {
                if (registered[i] != null) {
                    available.add(registered[i]);
                }
            }
        }

        if (!available.isEmpty()) {
            int idx = r.nextInt(available.size());
            requestedPallet = available.get(idx);
        }
    }
}