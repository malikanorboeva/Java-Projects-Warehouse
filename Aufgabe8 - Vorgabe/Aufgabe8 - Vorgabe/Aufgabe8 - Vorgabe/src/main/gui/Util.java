package main.gui;

public class Util {
	
	public static void sleep(int sleepInMs) {
		try {
			Thread.sleep(sleepInMs);
		} catch (InterruptedException e) {
		}
	}

}
