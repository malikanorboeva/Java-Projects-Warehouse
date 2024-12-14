package main;

import Prog1Tools.IOTools;

public class Pallet {
    private String description;
    private final int id; // Final because ID shouldn't change after creation
    private static int nextId = 1;
    private int width;
    private int depth;
    private int height;
    private boolean cooling;
    private int duration;
    private final long incoming;
    private final long outgoing;

    // Private constructor to force use of factory methods
    private Pallet(String description, int width, int depth, int height, boolean cooling, int duration) {
        this.id = nextId++;
        this.description = description;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.cooling = cooling;
        this.duration = duration;
        this.incoming = System.currentTimeMillis();
        this.outgoing = this.incoming + (duration * 24L * 60L * 60L * 1000L); // Convert days to milliseconds
    }

    // Factory method
    public static Pallet createPalletFrom(String description, int width, int depth, int height, boolean cooling, int duration) {
        if (!checkParameters(width, depth, height, duration)) {
            throw new IllegalArgumentException("Invalid parameters: all dimensions and duration must be positive");
        }
        return new Pallet(description, width, depth, height, cooling, duration);
    }

    public static Pallet createPalletFromUserInput() {
        String description;
        int width, depth, height, duration;
        boolean cooling;

        do {
            System.out.print("Description: ");
            description = IOTools.readString();
            System.out.print("Width: ");
            width = IOTools.readInt();
            System.out.print("Depth: ");
            depth = IOTools.readInt();
            System.out.print("Height: ");
            height = IOTools.readInt();
            System.out.print("Cooling (true/false): ");
            cooling = IOTools.readBoolean();
            System.out.print("Duration (days): ");
            duration = IOTools.readInt();

            if (!checkParameters(width, depth, height, duration)) {
                System.out.println("Invalid input: all dimensions and duration must be positive. Please try again.");
            }
        } while (!checkParameters(width, depth, height, duration));

        return createPalletFrom(description, width, depth, height, cooling, duration);
    }

    private static boolean checkParameters(int width, int depth, int height, int duration) {
        return width > 0 && depth > 0 && height > 0 && duration > 0;
    }

    public String getText() {
        return String.format("[%d] %s (%dx%dx%d) %s",
                id, description, width, depth, height,
                cooling ? "COOLED" : "");
    }

    // Getters
    public String getDescription() { return description; }
    public int getId() { return id; }
    public int getWidth() { return width; }
    public int getDepth() { return depth; }
    public int getHeight() { return height; }
    public boolean needsCooling() { return cooling; }
    public int getDuration() { return duration; }
    public long getIncoming() { return incoming; }
    public long getOutgoing() { return outgoing; }

    @Override
    public String toString() {
        return getText();
    }
}
