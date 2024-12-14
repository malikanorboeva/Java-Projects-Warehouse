package main;

import Prog1Tools.IOTools;

public class StockManager {
    private final Slot[][] storage;
    private static final int ROWS = 2;
    private static final int COLS = 3;

    public StockManager() {
        storage = new Slot[ROWS][COLS];
        // Initialize storage with slots
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                // Second row (y=1) is cooled
                storage[y][x] = new Slot(x, y, y == 1);
            }
        }
    }

    public void displayCurrentStock() {
        System.out.println("\nCurrent stock:");
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                System.out.println(storage[y][x].getText());
            }
        }
        System.out.println();
    }

    private Slot getSlot(int shelfIndex, int placeIndex) {
        if (shelfIndex < 0 || shelfIndex >= ROWS || placeIndex < 0 || placeIndex >= COLS) {
            return null;
        }
        return storage[shelfIndex][placeIndex];
    }

    public void run() {
        char choice;
        do {
            displayCurrentStock();
            System.out.print("What next? ('q'uit, 'i'nfo, 'c'reate, 'r'elease, 's'wap): ");
            choice = IOTools.readChar();

            switch (choice) {
                case 'i':
                    handleInfo();
                    break;
                case 'c':
                    handleCreate();
                    break;
                case 'r':
                    handleRelease();
                    break;
                case 's':
                    handleSwap();
                    break;
                case 'q':
                    System.out.println("Thanks for storing at our place!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 'q');
    }

    private void handleInfo() {
        System.out.println("Information about the storage place...");
        System.out.print("Shelf index (0/1): ");
        int shelfIndex = IOTools.readInt();
        System.out.print("Place index (0/1/2): ");
        int placeIndex = IOTools.readInt();

        Slot slot = getSlot(shelfIndex, placeIndex);
        if (slot == null) {
            System.out.println("Invalid slot location!");
            return;
        }

        System.out.println(slot.getText());
    }

    private void handleCreate() {
        System.out.println("Creating a new pallet...");
        Pallet pallet = Pallet.createPalletFromUserInput();

        System.out.print("Shelf index (0/1): ");
        int shelfIndex = IOTools.readInt();
        System.out.print("Place index (0/1/2): ");
        int placeIndex = IOTools.readInt();

        Slot slot = getSlot(shelfIndex, placeIndex);
        if (slot == null) {
            System.out.println("Invalid slot location!");
            return;
        }

        if (slot.setPallet(pallet)) {
            System.out.println("Pallet assigned successfully.");
        } else {
            System.out.println("Could not assign pallet. Check if slot is free and cooling requirements.");
        }
    }

    private void handleRelease() {
        System.out.println("Releasing pallet...");
        System.out.print("Shelf index (0/1): ");
        int shelfIndex = IOTools.readInt();
        System.out.print("Place index (0/1/2): ");
        int placeIndex = IOTools.readInt();

        Slot slot = getSlot(shelfIndex, placeIndex);
        if (slot == null) {
            System.out.println("Invalid slot location!");
            return;
        }

        if (slot.getPallet() == null) {
            System.out.println("Slot is already empty!");
            return;
        }

        slot.release();
        System.out.println("Pallet released successfully.");
    }

    private void handleSwap() {
        System.out.println("Swapping storage places...");

        System.out.println("Place 1:");
        System.out.print("Shelf index (0/1): ");
        int shelf1 = IOTools.readInt();
        System.out.print("Place index (0/1/2): ");
        int place1 = IOTools.readInt();

        System.out.println("Place 2:");
        System.out.print("Shelf index (0/1): ");
        int shelf2 = IOTools.readInt();
        System.out.print("Place index (0/1/2): ");
        int place2 = IOTools.readInt();

        Slot slot1 = getSlot(shelf1, place1);
        Slot slot2 = getSlot(shelf2, place2);

        if (slot1 == null || slot2 == null) {
            System.out.println("Invalid slot location(s)!");
            return;
        }

        if (slot1.swapWith(slot2)) {
            System.out.println("Slots swapped successfully.");
        } else {
            System.out.println("Could not swap slots. Check cooling requirements.");
        }
    }

    public static void main(String[] args) {
        StockManager manager = new StockManager();
        manager.run();
    }
}