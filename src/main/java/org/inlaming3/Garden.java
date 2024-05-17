package org.inlaming3;

import Strategy.TreePruningStrategy;
import command.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Garden {
    Scanner scanner = new Scanner(System.in);
    private LocalDate simulatedToday = LocalDate.now();
    private PlantManager plantManager;
    private Map<String, ICommand> commands = new HashMap<>();

    public Garden() {
        this.plantManager = new PlantManager("plants.csv");
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put("1", new ListPlantsCommand(plantManager));
        commands.put("2", new SortHarvestPeriodCommand(plantManager));
        commands.put("3", new PlantCareCommand(plantManager));
        commands.put("4", new AddPlantCommand(this));
        commands.put("5", new RemovePlantCommand(this));
        commands.put("6", new WaterCommand(plantManager,this::getSimulatedToday));
        commands.put("7", new FertilizeCommand(plantManager, this::getSimulatedToday));
        commands.put("8", new PruneCommand(plantManager,this::getSimulatedToday));
        commands.put("9", new SimulateDayCommand(this));
    }

    public void menu() {
        System.out.println("\nVad vill du göra idag Lotta?");
        System.out.println();
        boolean gardening = true;
        while (gardening) {
            printMenu();
            String choice = scanner.nextLine();
            ICommand command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice.equals("10")) {
                gardening = false;
                System.out.println("Tack för idag....vi ses imorgon igen:)");
            } else {
                System.out.println("Felaktigt val, försök igen");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("------------------------------------------------");
        System.out.println("\n1.Alla växter i trädgården.");  // Plant list
        System.out.println("2.Överblick över grönsakernas skördetid.");
        System.out.println("3.Kolla vad som behövs göras i trädgården idag.");  //check plants
        System.out.println("4.Plantera ny växt");  //add plant
        System.out.println("5.Gräva upp växt");  // remove plant
        System.out.println("6.Vattna");
        System.out.println("7.Gödsla");
        System.out.println("8.Beskära/Gallra/rensa");
        System.out.println("9.Simulera en dag.");
        System.out.println("10.Avsluta\n");
        System.out.println("Gör ditt val i menyn:");
    }
    public void addNewPlant() {
        System.out.println("Vilken typ av växt vill du plantera? (1.Blomma, 2.Buske/Träd, 3.Grönsak");
        String type = scanner.nextLine();

        switch (type.toLowerCase()) {
            case "1":
                addFlower();
                break;
            case "2":
                addBushOrTree();
                break;
            case "3":
                addVegetable();
                break;
        }
    }
    private void addFlower() {
        String name = promptForInput("Ange namnet på växten:");
        int wateringInterval = promptForInteger("Ange bevattningsintervall i dagar:");
        int fertilizingInterval = promptForInteger("Ange gödslingsintervall i dagar:");
        int pruningInterval = promptForInteger("Ange beskärningsintervall i dagar:");
        String height = promptForInput("Ange höjden:");
        String flowerColor = promptForInput("Ange blomfärg");
        String floweringPeriod = promptForInput("Ange blommningsperiod:");
        boolean perennial = promptForBoolean("Är det en perenn? (ja/nej):");

        PlantBase plant = PlantFactory.createPlant("flower", name, wateringInterval, fertilizingInterval, pruningInterval, height, flowerColor, floweringPeriod, perennial, null, null);
        plantManager.addPlant(plant);
        System.out.println("Ny blomma tillagd:" + plant.getName());

    }

    private void addBushOrTree() {
        String name = promptForInput("Ange namnet på växten:");
        int wateringInterval = promptForInteger("Ange bevattningsintervall i dagar:");
        int fertilizingInterval = promptForInteger("Ange gödslingsintervall i dagar:");
        int pruningInterval = -1;
        String height = promptForInput("Ange höjden:");
        String width = promptForInput("Ange bredden på växten:");
        boolean flowerOrNot = promptForBoolean("Har växten blommor? (ja/nej):");
        boolean fruitOrNot = promptForBoolean("Har växten frukt? (ja/nej):");
        String pruneMonth = promptForInput("Ange beskärningsmånad");
        String harvestPeriod = promptForInput("Ange skördetid");

        TreePruningStrategy strategy = new TreePruningStrategy(pruneMonth);

        PlantBase plant = PlantFactory.createPlant("buschesandtrees", name, wateringInterval, fertilizingInterval, pruningInterval, height, width, flowerOrNot, fruitOrNot, pruneMonth, harvestPeriod);
        plantManager.addPlant(plant);
        System.out.println("Ny växt tillagd: " + plant.getName());
    }

    private void addVegetable() {
        String name = promptForInput("Vad är namnet på grönsaken?:");
        int wateringInterval = promptForInteger("Hur ofta behövs det vattnas?(ange intervall i dagar):");
        int fertilizingInterval = promptForInteger("Hur ofta behövs gödning?(ange intervall i dagar:");
        int pruningInterval = promptForInteger("Hur ofta behövs det gallras och rensas?(ange i dagar):");
        String height = promptForInput("Hur hög blir den?:");
        String harvestPeriod = promptForInput("När skördas den:");

        PlantBase plant = PlantFactory.createPlant("vegetable", name, wateringInterval, fertilizingInterval, pruningInterval, height, harvestPeriod, false, false, null,null);
        plantManager.addPlant(plant);
        System.out.println("Ny grönsak tillagd: " + plant.getName());
    }

    private String promptForInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    private int promptForInteger(String message) {
        while (true) {
            try {
                System.out.println(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Felaktig input, var god försök igen.");
            }
        }
    }
    private boolean promptForBoolean(String message) {
        System.out.println(message + " skriv ja eller nej");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("ja")) {
                return true;
            } else if (input.equals("nej")) {
                return false;
            } else {
                System.out.println("Felaktig input, ange ja eller nej.");
            }
        }
    }

    public void removePlant() {
        System.out.println("Ange namnet på den växt du vill ta bort.");
        String nameToRemove = scanner.nextLine().trim();
        plantManager.removePlant(nameToRemove);
    }
    public void simulateDay() {
        simulatedToday = simulatedToday.plusDays(1);
        ICommand careCommmand = commands.get("3");
        if(careCommmand instanceof PlantCareCommand){
            ((PlantCareCommand) careCommmand).setSimulatedToday(simulatedToday);
            careCommmand.execute();
        }
    }
    public LocalDate getSimulatedToday(){
        return simulatedToday;
    }
}





