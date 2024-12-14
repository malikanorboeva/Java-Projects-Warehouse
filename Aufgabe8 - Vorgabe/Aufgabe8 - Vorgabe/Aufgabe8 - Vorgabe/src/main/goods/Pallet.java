package main.goods;

import java.util.Objects;

public abstract class Pallet {

    private String description = "";
    private int id;
    private int width;
    private int depth;
    protected int height;
    private boolean cooling;
    private long incoming;
    private long outgoing;
    private int price;

    private static int nextid = 1;

    /**
     * Creates a pallet from given values
     * @param description - describes the content
     * @param width - width of the pallet
     * @param depth - depth of the pallet
     * @param height - height of the pallet
     * @param cooling - true, if the pallet requires cooling
     * @param duration - estimated duration of stocking in days
     * @param price - price per liter or unit
     */
    protected Pallet(String description, int width, int depth, int height, boolean cooling, int duration, int price) {
        this.id = nextid++;
        this.description = description;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.cooling = cooling;
        this.incoming = System.currentTimeMillis();
        this.outgoing = this.incoming + (duration * 24 * 60 * 60 * 1000);
        this.price = price;
    }

    /**
     * Calculates the current weight of the pallet.
     * @return weight in gram
     */
    public abstract int getWeight();

    /**
     * Calculates the current total value of the pallet.
     * @return total value in Euro.
     */
    public abstract int getValue();

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public int getPrice() {
        return price;
    }

    /*
    public boolean equals(Object object) {
        if (Objects.equals(object, this)) {
            return true;
        }
        if (!(object instanceof Pallet)) {
            return false;
        }
        Pallet pallet = (Pallet) object;
        return Objects.equals(pallet.height, this.height)
                && Objects.equals(pallet.width, this.width)
                && Objects.equals(pallet.id, this.id)
                && Objects.equals(pallet.depth, this.depth)
                && Objects.equals(pallet.cooling, this.cooling)
                && Objects.equals(pallet.price, this.price)
                && Objects.equals(pallet.description, this.description);
    }

    public int hashCode(Object object) {
        if (object.hashCode()==this.hashCode()) {
            return Objects.hash(id, height, width, depth, cooling, price);
        }
        return 0;
    }

     */
}
