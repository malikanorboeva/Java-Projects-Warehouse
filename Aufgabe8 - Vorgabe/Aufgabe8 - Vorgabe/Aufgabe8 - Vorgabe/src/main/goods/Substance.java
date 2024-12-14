package main.goods;

import java.util.Objects;
import java.util.SimpleTimeZone;

public abstract class Substance extends Pallet{

    private double density;

    /**
     * Creates a pallet from given values
     *
     * @param description - describes the content
     * @param width       - width of the pallet
     * @param depth       - depth of the pallet
     * @param height      - height of the pallet
     * @param cooling     - true, if the pallet requires cooling
     * @param duration    - estimated duration of stocking in days
     * @param density     - density in gram per liter
     */
    protected Substance(String description, int width, int depth, int height, boolean cooling, int duration, double density, int price) {
        super(description, width, depth, height, cooling, duration, price);
        this.density = density;
    }

    public double getDensity() {
        return density;
    }

    /*
    public boolean equals(Object object) {
        if (Objects.equals(object, this)) {
            return true;
        }
        if (!(object instanceof Substance)) {
            return false;
        }
        Substance substance = (Substance) object;
        return Objects.equals(substance.getDescription(), this.getDescription())
                && Objects.equals(substance.getValue(), this.getValue())
                && Objects.equals(substance.getDepth(), this.getDepth())
                && Objects.equals(substance.getHeight(), this.getHeight())
                && Objects.equals(substance.getDensity(), this.getDensity())
                && Objects.equals(substance.getWeight(), this.getWeight())
                && Objects.equals(substance.getId(), this.getId());
    }

    public int hashCode(Object object) {
        if (object.hashCode()==this.hashCode()) {
            return Objects.hash(getId(), getHeight(), getWidth(), getDensity(), getWeight(), getValue(), getPrice());
        }
        return 0;
    }

     */
}
