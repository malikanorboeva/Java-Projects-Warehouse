package main.goods;

public abstract class Pallet {
    private final int id;
    private final String description;
    private final int width;
    private final int depth;
    protected int height;
    private final boolean cooling;
    private final long incoming;
    private final long outgoing;
    private final int price;
    private double weight;
    private int value;

    private static int nextId = 1;

    protected Pallet(String description, boolean cooling, int price, int duration,
                     int width, int depth, int height) {
        this.id = nextId++;
        this.description = description;
        this.cooling = cooling;
        this.price = price;
        this.incoming = System.currentTimeMillis();
        this.outgoing = this.incoming + (duration * 24L * 60L * 60L * 1000L);
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    public String getText() {
        return String.format("[%d] %s (%dx%dx%d) %.2fkg %d€%s",
                id, description, width, depth, height,
                weight, value, cooling ? " cooling" : "");
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public int getWidth() { return width; }
    public int getDepth() { return depth; }
    public int getHeight() { return height; }
    public boolean isCooling() { return cooling; }
    public long getIncoming() { return incoming; }
    public long getOutgoing() { return outgoing; }
    public int getPrice() { return price; }
    public double getWeight() { return weight; }
    public int getValue() { return value; }

    // Setters for calculated values
    protected void setWeight(double weight) { this.weight = weight; }
    protected void setValue(int value) { this.value = value; }

    @Override
    public String toString() {
        return getText();
    }
}