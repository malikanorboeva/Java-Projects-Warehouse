package main.goods;

public class Liquid extends Pallet {
    private double fuellstand;
    private final double dichte;
    private final int kemlerZahl;
    private final int unNummer;
    private static final int STANDARD_DIMENSION = 90; // cm

    public Liquid(String description, boolean cooling, int price, int duration,
                  double dichte, int kemlerZahl, int unNummer) {
        super(description, cooling, price, duration,
                STANDARD_DIMENSION, STANDARD_DIMENSION, STANDARD_DIMENSION);

        this.dichte = dichte;
        this.kemlerZahl = kemlerZahl;
        this.unNummer = unNummer;
        this.fuellstand = 1.0; // 100%

        calculateWeightAndValue();
    }

    private void calculateWeightAndValue() {
        // Volume in liters (90x90x90 cm³ = 729000 cm³ = 729 L)
        double volumeInLiters = Math.pow(STANDARD_DIMENSION, 3) / 1000;

        // Weight in kg = volume (L) * density (g/L) * fillLevel / 1000 (g to kg)
        setWeight(volumeInLiters * dichte * fuellstand / 1000);

        // Value = price per liter * volume * fillLevel
        setValue((int)(getPrice() * volumeInLiters * fuellstand));
    }

    public void removeLiquid(double amount) {
        if (amount > 0 && amount <= fuellstand) {
            fuellstand -= amount;
            calculateWeightAndValue();
        }
    }

    // Getters
    public double getFuellstand() { return fuellstand; }
    public double getDichte() { return dichte; }
    public int getKemlerZahl() { return kemlerZahl; }
    public int getUnNummer() { return unNummer; }
}

