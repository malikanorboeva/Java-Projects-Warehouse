package main;

public class Slot {
    private Pallet pallet;
    private final int xpos;
    private final int ypos;
    private final boolean cooling;

    public Slot(int xpos, int ypos, boolean cooling) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.cooling = cooling;
        this.pallet = null;
    }

    public static Slot createSlotFrom(int xpos, int ypos, boolean cooling) {
        return new Slot(xpos, ypos, cooling);
    }

    public boolean setPallet(Pallet pallet) {
        // Can't place pallet if slot is occupied
        if (this.pallet != null) {
            return false;
        }

        // Can't place pallet that needs cooling in non-cooled slot
        if (pallet != null && pallet.needsCooling() && !this.cooling) {
            return false;
        }

        this.pallet = pallet;
        return true;
    }

    public void release() {
        this.pallet = null;
    }

    public String getText() {
        String status = (pallet == null) ? "free" : "occupied";
        String coolingStatus = cooling ? "cooled" : "";
        String palletInfo = (pallet == null) ? "" : "\n  Contains: " + pallet.getText();

        return String.format("Slot(%d,%d) %s %s%s",
                xpos, ypos, status, coolingStatus, palletInfo);
    }

    // Getters
    public Pallet getPallet() { return pallet; }
    public int getXpos() { return xpos; }
    public int getYpos() { return ypos; }
    public boolean isCooling() { return cooling; }

    // Method to swap contents with another slot
    public boolean swapWith(Slot other) {
        // Check cooling requirements
        if (this.pallet != null && this.pallet.needsCooling() && !other.cooling) {
            return false;
        }
        if (other.pallet != null && other.pallet.needsCooling() && !this.cooling) {
            return false;
        }

        // Perform swap
        Pallet temp = this.pallet;
        this.pallet = other.pallet;
        other.pallet = temp;
        return true;
    }
}
