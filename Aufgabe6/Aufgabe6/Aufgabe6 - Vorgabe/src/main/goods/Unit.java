package main.goods;

public class Unit extends Pallet {
    private final int hoeheEinesStudcks;
    private final int anzahlDerEbenen;
    private final int anzahlProEbene;
    private final int gewichtProStueck;
    private static final int STANDARD_DIMENSION = 90; // cm

    public Unit(String description, boolean cooling, int price, int duration,
                int hoeheEinesStudcks, int gewichtProStueck, int anzahlDerEbenen,
                int anzahlProEbene) {
        super(description, cooling, price, duration,
                STANDARD_DIMENSION, STANDARD_DIMENSION,
                hoeheEinesStudcks * anzahlDerEbenen);

        this.hoeheEinesStudcks = hoeheEinesStudcks;
        this.anzahlDerEbenen = anzahlDerEbenen;
        this.anzahlProEbene = anzahlProEbene;
        this.gewichtProStueck = gewichtProStueck;

        calculateWeightAndValue();
    }

    private void calculateWeightAndValue() {
        int totalPieces = anzahlDerEbenen * anzahlProEbene;

        // Weight in kg = total pieces * weight per piece / 1000 (g to kg)
        setWeight(totalPieces * gewichtProStueck / 1000.0);

        // Value = price per piece * total pieces
        setValue(getPrice() * totalPieces);
    }

    // Getters
    public int getHoeheEinesStudcks() { return hoeheEinesStudcks; }
    public int getAnzahlDerEbenen() { return anzahlDerEbenen; }
    public int getAnzahlProEbene() { return anzahlProEbene; }
    public int getGewichtProStueck() { return gewichtProStueck; }
    public int getTotalPieces() { return anzahlDerEbenen * anzahlProEbene; }
}

