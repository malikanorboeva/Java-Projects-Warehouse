package main.gui;

import main.goods.Pallet;
import main.manager.IncomingPalletManager;
import main.stock.Stack;

import java.util.Random;

public class Helper {

    /**
     * Position
     */
    protected int x = 0;
    protected int y = 0;

    /**
     * Stacks
     */
    protected  Stack[] stacks;

    /**
     * Connection to manager
     */
    protected IncomingPalletManager incomingPalletManager;

    /**
     * Inventory of Wichtel/Elf
     */
    protected  Pallet inventory = null;

    /**
     * Requested pallet
     */
    protected static Pallet requestedPallet;

    /**
     * Some random generator
     */
    protected  Random r = new Random();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pallet getInventory() {
        return inventory;
    }

    public static Pallet getRequestedPallet() {
        return requestedPallet;
    }

}
