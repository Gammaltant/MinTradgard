package org.inlaming3;

public class PlantFactory {
    public static PlantBase createPlant(String type, String name, int wateringInterval, int fertilizingInterval, int pruningInterval, String height,
                                        String attribute1, Object attribute2, boolean booleanAttribute, String pruneMonth, String harvestPeriod){
        if(!validateInputs(type,attribute2)){
            System.out.println("Felaktig input-parameter för typ " + type + " förväntad String, fick " + attribute2.getClass().getSimpleName());
            return null;
        }

        String normalizedType = type.toLowerCase();
        switch(normalizedType){
            case"flower":
                if (!(attribute2 instanceof String)) {
                    System.out.println("Felaktig parameter för Flower, floweringPeriod måste vara en String");
                    return null;
                }
                   return new Flower(name, wateringInterval, fertilizingInterval, pruningInterval, height, attribute1, (String)attribute2, booleanAttribute);
            case"buschesandtrees":
                if(pruneMonth == null || pruneMonth.trim().isEmpty()){
                    System.out.println("Beskärningsmånad krävs för buskar och träd.");
                    return null;

                }
                    return new BuschesAndTrees(name, wateringInterval, fertilizingInterval, pruningInterval, height, attribute1, (Boolean)attribute2, booleanAttribute,pruneMonth,harvestPeriod);
            case"vegetable":
                return new Vegetable(name, wateringInterval, fertilizingInterval, pruningInterval, height, attribute1);
            default:
                System.out.println("Okänd växttyp: " + type);
                return null;
        }
    }
    // Validation for specific parameters.
    private static boolean validateInputs(String type, Object attribute2) {
        switch(type.toLowerCase()){
            case"flower":
                return attribute2 instanceof String;  //Flower need a String for attribute2
            case"buschesandtrees":
                return attribute2 instanceof Boolean;  //Buschesandtrees need a boolean för attribute2
            case"vegetable":
                return true;
            default:
                return false;
        }
    }
}
