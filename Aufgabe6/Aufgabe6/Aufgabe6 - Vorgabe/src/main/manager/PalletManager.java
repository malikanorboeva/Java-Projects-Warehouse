package main.manager;

import main.goods.*;
import java.io.*;
import java.util.*;

public class PalletManager {
    private static Pallet[] pallets;

    public static void main(String[] args) {
        pallets = preloadInitialDataSet();
        if (pallets.length > 0) {
            System.out.println("Loaded " + pallets.length + " pallets:");
            for (Pallet pallet : pallets) {
                System.out.println(pallet.getText());
            }
        } else {
            System.out.println("No pallets loaded.");
        }
    }

    public static Pallet[] preloadInitialDataSet() {
        List<Pallet> palletList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("inputdata.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove BOM if present
                if (line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }

                String[] data = line.split(";");
                try {
                    Pallet pallet = createPalletFromData(data);
                    if (pallet != null) {
                        palletList.add(pallet);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file:");
            e.printStackTrace();
            return new Pallet[0];
        }

        return palletList.toArray(new Pallet[0]);
    }

    private static Pallet createPalletFromData(String[] data) {
        if (data == null || data.length < 5) return null;

        try {
            String type = data[0].trim();
            String description = data[1].trim();
            boolean cooling = Boolean.parseBoolean(data[2].trim());
            int price = Integer.parseInt(data[3].trim());
            int duration = Integer.parseInt(data[4].trim());

            switch (type) {
                case "L":
                    if (data.length < 8) return null;
                    double density = Double.parseDouble(data[5].trim());
                    int kemler = Integer.parseInt(data[6].trim());
                    int un = Integer.parseInt(data[7].trim());
                    return new Liquid(description, cooling, price, duration,
                            density, kemler, un);

                case "S":
                    if (data.length < 9) return null;
                    density = Double.parseDouble(data[5].trim());
                    kemler = Integer.parseInt(data[6].trim());
                    un = Integer.parseInt(data[7].trim());
                    int edge = Integer.parseInt(data[8].trim());
                    return new Solid(description, cooling, price, duration,
                            density, kemler, un, edge);

                case "U":
                    if (data.length < 9) return null;
                    int height = Integer.parseInt(data[5].trim());
                    int weight = Integer.parseInt(data[6].trim());
                    int layers = Integer.parseInt(data[7].trim());
                    int perLayer = Integer.parseInt(data[8].trim());
                    return new Unit(description, cooling, price, duration,
                            height, weight, layers, perLayer);

                default:
                    System.err.println("Unknown pallet type: " + type);
                    return null;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric value: " + e.getMessage());
            return null;
        }
    }
}
