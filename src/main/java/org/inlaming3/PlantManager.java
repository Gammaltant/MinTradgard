package org.inlaming3;

import Strategy.ISortStrategy;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;


public class PlantManager {
    private List<PlantBase> plants = new ArrayList<>();
    private ISortStrategy sortStrategy;
    private boolean isModified = false;

    public PlantManager(String filename) {
        initializePlantsFromFile(filename);
    }

    public void setSortStrategy(ISortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void sortPlants() {
        if (sortStrategy != null) {
            sortStrategy.sort(plants);
        }
    }

    public void savePlantsToFile(String filename) {
        if (!isModified) return;
        String filePath = Paths.get(filename).toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (PlantBase plant : plants) {
                bw.write(plant.toCsvString());
                bw.newLine();
            }
            isModified = false;
            System.out.println(".....och reglerats i fil.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void initializePlantsFromFile(String filename) {
        String filePath = Paths.get(filename).toString();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                PlantBase plant = parsePlantLine(line);
                if (plant != null) {
                    plants.add(plant);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private PlantBase parsePlantLine(String line) {

        String[] parts = line.split(",");
        if (parts.length < 6) {
            System.out.println("Fel: Raden innehåller inte tillräckligt många element" + line);
            return null;
        }

        try {
            String type = parts[0].trim();
            switch (type.toLowerCase()) {
                case "flower":
                    return createFlower(parts);
                case "buschesandtrees":
                    return createBuschesAndTrees(parts);
                case "vegetable":
                    return createVegetable(parts);
                default:
                    System.out.println("Okänd växttyp: " + type);
                    return null;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Fel vid tolkning av nummer i raden: " + line + " - " + ex.getMessage());
            return null;
        }
    }

    private Flower createFlower(String[] parts) {
        if (parts.length < 9) {
            System.out.println("Fel: Inte tillräckligt med info för en blomma - " + Arrays.toString(parts));
            return null;
        }
        String name = parts[1].trim();
        int wateringInterval = Integer.parseInt(parts[2].trim());
        int fertilizingInterval = Integer.parseInt(parts[3].trim());
        int pruningInterval = Integer.parseInt(parts[4].trim());
        String height = parts[5].trim();
        String flowerColor = parts[6].trim();
        String floweringPeriod = parts[7].trim();
        boolean perennial = Boolean.parseBoolean(parts[8].trim());
        return new Flower(name, wateringInterval, fertilizingInterval, pruningInterval, height, flowerColor, floweringPeriod, perennial);
    }

    private BuschesAndTrees createBuschesAndTrees(String[] parts) {
        if (parts.length < 11) {
            System.out.println("Fel: Inte tillräckligt med info för en buske/träd - " + Arrays.toString(parts));
            return null;
        }
        String name = parts[1].trim();
        int wateringInterval = Integer.parseInt(parts[2].trim());
        int fertilizingInterval = Integer.parseInt(parts[3].trim());
        int pruningInterval = Integer.parseInt(parts[4].trim());
        String height = parts[5].trim();
        String width = parts[6].trim();
        boolean hasFlowers = Boolean.parseBoolean(parts[7].trim());
        boolean hasFruits = Boolean.parseBoolean(parts[8].trim());
        String pruneMonth = parts[9].trim();
        String harvestPeriod = parts[10].trim();
        return new BuschesAndTrees(name, wateringInterval, fertilizingInterval, pruningInterval, height, width, hasFlowers, hasFruits, pruneMonth, harvestPeriod);
    }

    private Vegetable createVegetable(String[] parts) {
        if (parts.length < 7) {
            System.out.println("Fel: Inte tillräckligt med info för en grönsak - " + Arrays.toString(parts));
            return null;
        }
        String name = parts[1].trim();
        int wateringInterval = Integer.parseInt(parts[2].trim());
        int fertilizingInterval = Integer.parseInt(parts[3].trim());
        int pruningInterval = Integer.parseInt(parts[4].trim());
        String height = parts[5].trim();
        String harvestPeriod = parts[6].trim();
        return new Vegetable(name, wateringInterval, fertilizingInterval, pruningInterval, height, harvestPeriod);
    }


    public void listOfPlants() {
        System.out.println("Alla plantor i trädgården:");
        System.out.println("--------------------------\n");
        if (plants.isEmpty()) {
            System.out.println("Inga plantor finns i trädgården.");
        } else {
            for (PlantBase plant : plants) {
                System.out.println(plant.getName() + " - " + plant.getPlantType());
            }
        }
    }

    public void displayPlantsWithHarvestPeriod() {
        System.out.println("Alla plantor i trädgården sorterade efter skördetid:");
        System.out.println("---------------------------------------------------\n");
        if (plants.isEmpty()) {
            System.out.println("Inga plantor finns i trädgården.");
        } else {
            for (PlantBase plant : plants) {
                String harvestPeriod = "Ingen skördetid";
                if (plant instanceof Vegetable || plants instanceof BuschesAndTrees) {
                    harvestPeriod = ((Vegetable) plant).getHarvestPeriod();
                } else if (plant instanceof BuschesAndTrees) {
                    harvestPeriod = ((BuschesAndTrees) plant).getHarvestPeriod();
                }
                System.out.println(plant.getName() + " - " + plant.getPlantType() + " - skördetid: " + harvestPeriod);
            }
        }
    }

    public void addPlant(PlantBase plant) {
        plants.add(plant);
        System.out.println(plant.getName() + " har lagt till i trädgården.");
        isModified = true;
        savePlantsToFile("plants.csv");
    }

    public void removePlant(String name) {
        boolean isRemoved = plants.removeIf(plant -> plant.getName().equalsIgnoreCase(name));
        if (isRemoved) {
            System.out.println(name + " har tagits bort från trädgården.");
            isModified = true;
            savePlantsToFile("plants.csv");
        } else {
            System.out.println("Kunde inte hitta växten med det namnet " + name);
        }
    }

    public void resetPlantStatusForNewDay() {
        for (PlantBase plant : plants) {
            plant.setWateredToday(false);
            plant.setFertilizedToday(false);
            plant.setPrunedToday(false);
        }
    }

    public void checkPlantsForCareToday(LocalDate simulatedToday) {
        System.out.println("Kontrollerar plantor för omvårdnad: " + simulatedToday);
        resetPlantStatusForNewDay();
        boolean careNeeded = false;
        for (PlantBase plant : plants) {
            if (plant.needsWater(simulatedToday) && !plant.isWateredToday()) {
                System.out.println(plant.getName() + " behöver vattnas idag.");
                careNeeded = true;
            }
            if (plant.needsFertilizer(simulatedToday) && !plant.isFertilizedToday()) {
                System.out.println(plant.getName() + " behöver gödsel idag.");
                careNeeded = true;
            }
            if (plant.needsPruning(simulatedToday) && !plant.isPrunedToday()) {
                System.out.println(plant.getName() + " behöver beskäras idag.");
                careNeeded = true;
            }
        }
        if (!careNeeded) {
            System.out.println("\nInga plantor behöver omvårdnad idag. Bra dag för att rensa ogräs :).");
        }
    }

    public void waterPlants(LocalDate simulatedToday) {
        for (PlantBase plant : plants) {
            if (plant.needsWater(simulatedToday)) {
                plant.water(simulatedToday);
                System.out.println(simulatedToday);
                plant.reactionAfterWater();
                plant.setWateredToday(true);
            }
        }
    }

    public void fertilizePlants(LocalDate simulatedToday) {
        for (PlantBase plant : plants) {
            if (plant.needsFertilizer(simulatedToday)) {
                plant.fertilize(simulatedToday);
                System.out.println(simulatedToday);
                plant.reactionAfterFertilizer();
                plant.setFertilizedToday(true);
            }
        }
    }

    public void prunePlants(LocalDate simulatedToday) {
        for (PlantBase plant : plants) {
            if (plant.needsPruning(simulatedToday)) {
                plant.prune(simulatedToday);
                System.out.println(simulatedToday);
                plant.setPrunedToday(true);
            }
        }
    }
}