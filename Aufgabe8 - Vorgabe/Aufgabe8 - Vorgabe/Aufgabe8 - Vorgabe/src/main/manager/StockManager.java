package main.manager;

import main.gui.ElfController;
import main.gui.WichtelController;
import main.gui.StockSimulator;
import main.gui.Util;
import main.stock.Stack;
import main.ledger.JournalEntryQueue;
import java.util.concurrent.*;

public class StockManager {
	public static final int NUMBEROFWICHTEL = 1;
	public static final int NUMBEROFELF = 1;
	public static final int NUMBEROFSTACKS = 20;
	public static final int STACKHEIGHT = 3;
	public static final int WIDTH = 10;
	private static volatile boolean running = true;
	private static final ExecutorService workerPool =
			Executors.newFixedThreadPool(NUMBEROFWICHTEL + NUMBEROFELF);
	private static final JournalEntryQueue entryQueue = new JournalEntryQueue(1000);

	public static void main(String[] args) {
		Stack[] stacks = new Stack[NUMBEROFSTACKS];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = new Stack();
		}

		IncomingPalletManager incomingPalletManager = new IncomingPalletManager();

		// Create and submit worker tasks
		CountDownLatch startLatch = new CountDownLatch(1);
		for (int i = 0; i < NUMBEROFWICHTEL; i++) {
			workerPool.submit(() -> {
				try {
					startLatch.await();
					WichtelController controller = new WichtelController(incomingPalletManager, stacks);
					while (running) {
						controller.performWichtelOperation();
						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});
		}

		for (int i = 0; i < NUMBEROFELF; i++) {
			workerPool.submit(() -> {
				try {
					startLatch.await();
					ElfController controller = new ElfController(incomingPalletManager, stacks);
					while (running) {
						controller.performElfOperation();
						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});
		}

		StockSimulator sim = new StockSimulator(incomingPalletManager, stacks);
		startLatch.countDown();

		try {
			while (running) {
				sim.UpdateGUI();
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			stop();
		}
	}

	public static void stop() {
		running = false;
		workerPool.shutdown();
		try {
			if (!workerPool.awaitTermination(1, TimeUnit.SECONDS)) {
				workerPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			workerPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	public static JournalEntryQueue getEntryQueue() {
		return entryQueue;
	}
}
