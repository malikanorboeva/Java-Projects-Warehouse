package main.gui;

import java.util.concurrent.CopyOnWriteArrayList;
import main.goods.Pallet;
import main.goods.Solid;
import main.manager.IncomingPalletManager;
import main.manager.StockManager;
import main.stock.Stack;
import main.stock.Wichtel;
import java.util.ArrayList;

public class WichtelController extends Helper {
	private final Wichtel wichtel = new Wichtel();
	private static final CopyOnWriteArrayList<WichtelController> wichtelList = new CopyOnWriteArrayList<>();

	public WichtelController(IncomingPalletManager incomingPalletManager, Stack[] stacks) {
		this.incomingPalletManager = incomingPalletManager;
		this.stacks = stacks;
		wichtelList.add(this);
	}

	public static void performWichtelOperationForEveryWichtel() {
		for (WichtelController wc : wichtelList) {
			wc.performWichtelOperation();
		}
	}

	private synchronized void generateAndRegisterRandomPallets() {
		while (getRegisteredPalletsNotNull().size() < StockManager.NUMBEROFSTACKS) {
			String description = "Pallet " + r.nextInt(3000);
			int size = ((r.nextInt(3) * 10) + 10) * 3;
			boolean cooling = r.nextBoolean();
			int duration = r.nextInt(13) + 1;
			int density = r.nextInt(1500);
			int price = r.nextInt(100);
			Solid solid = new Solid(description, size, cooling, duration, density, price);
			incomingPalletManager.registerPallets(solid);
		}
	}

	public void performWichtelOperation() {
		generateAndRegisterRandomPallets();
		Pallet[] registeredPallets = incomingPalletManager.getPallets();

		String cmd = wichtel.computeNextOperation(x, y, registeredPallets, stacks, inventory, requestedPallet);
		if (cmd == null || cmd.length() == 0) {
			return;
		}
		System.out.println("Wichtel Command: " + cmd);
		switch (cmd.charAt(0)) {
		case 'N':
			if (y > 0) {
				--y;
			}
			return;
		case 'S':
			if (y < stacks.length -1) {
				++y;
			}
			return;
		case 'E':
			if (x < StockManager.WIDTH) {
				++x;
			}
			return;
		case 'W':
			if (x > 0) {
				--x;
			}
			return;
		case 'P': //Pick
			if (y >= 0 && y < registeredPallets.length) {
				if (registeredPallets[y] != null) {
					inventory = incomingPalletManager.removePallet(registeredPallets[y]);
				}
			}
			return;
		case 'D': //Drop
			if (x == StockManager.WIDTH) {
				if (inventory != null){
					if (stacks[y].add(inventory)) {
						inventory = null;
					}
				}
			}
			return;
		default:
			return;
		}
	}
	

	public static CopyOnWriteArrayList<WichtelController> getWichtelList() {
		return wichtelList;
	}

	private ArrayList<Pallet> getRegisteredPalletsNotNull() {
		ArrayList<Pallet> registeredPallets = new ArrayList<>();
		for (Pallet p : incomingPalletManager.getPallets()) {
			if (p != null) {
				registeredPallets.add(p);
			}
		}
		return registeredPallets;
	}
}
