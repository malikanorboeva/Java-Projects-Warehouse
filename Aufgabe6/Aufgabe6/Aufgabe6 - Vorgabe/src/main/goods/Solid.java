package main.goods;

public class Solid extends Pallet {
    private final double dichte;
    private final int kemlerZahl;
    private final int unNummer;
    private final int kantenlaenge;

    public Solid(String description, boolean cooling, int price, int duration,
                 double dichte, int kemlerZahl, int unNummer, int kantenlaenge) {
        super(description, cooling, price, duration,
                kantenlaenge, kantenlaenge, kantenlaenge);

        this.dichte = dichte;
        this.kemlerZahl = kemlerZahl;
        this.unNummer = unNummer;
        this.kantenlaenge = kantenlaenge;

        calculateWeightAndValue();
    }

    private void calculateWeightAndValue() {
        // Volume in liters (kantenlaenge³ cm³ / 1000)
        double volumeInLiters = Math.pow(kantenlaenge, 3) / 1000;

        // Weight in kg = volume (L) * density (g/L) / 1000 (g to kg)
        setWeight(volumeInLiters * dichte / 1000);

        // Value = price per liter * volume
        setValue((int)(getPrice() * volumeInLiters));
    }

    // Getters
    public double getDichte() { return dichte; }
    public int getKemlerZahl() { return kemlerZahl; }
    public int getUnNummer() { return unNummer; }
    public int getKantenlaenge() { return kantenlaenge; }
}
