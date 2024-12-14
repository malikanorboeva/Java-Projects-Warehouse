package main.goods;

import java.util.Objects;

public class Solid extends Substance{
    /**
     * Creates a pallet from given values
     *
     * @param description - describes the content
     * @param size        - edge length of the pallet in cm
     * @param cooling     - true, if the pallet requires cooling
     * @param duration    - estimated duration of stocking in days
     * @param density     - density in gram per liter
     * @param price       - price per liter
     */
    public Solid(String description, int size, boolean cooling, int duration, double density, int price) {
        super(description, size, size, size, cooling, duration, density, price);
    }

    @Override
    public int getWeight() {
        return (int) (((getWidth() * getHeight() * getDepth()) / 1000) * getDensity());
    }

    @Override
    public int getValue() {
        return ((getWidth() * getHeight() * getDepth()) / 1000) * getPrice();
    }

    /*
    public boolean equals(Object object) {
        if (Objects.equals(object, this)) {
            return true;
        }
        if (!(object instanceof Solid)) {
            return false;
        }
        Solid solid = (Solid) object;
        return Objects.equals(solid.getDescription(), this.getDescription())
                && Objects.equals(solid.getValue(), this.getValue())
                && Objects.equals(solid.getDepth(), this.getDepth())
                && Objects.equals(solid.getHeight(), this.getHeight())
                && Objects.equals(solid.getDensity(), this.getDensity())
                && Objects.equals(solid.getWeight(), this.getWeight())
                && Objects.equals(solid.getId(), this.getId());
    }

    public int hashCode(Object object) {
        if (object.hashCode()==this.hashCode()) {
            return Objects.hash(getId(), getHeight(), getWidth(), getDensity(), getWeight(), getValue(), getPrice());
        }
        return 0;
    }

     */
}
