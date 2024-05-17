package org.inlaming3;

import Strategy.IPruningStrategy;
import Strategy.TreePruningStrategy;

import java.time.LocalDate;

public class BuschesAndTrees extends PlantBase {
    private String width;
    boolean flowersOrNot;
    boolean fruitsOrNot;
    private IPruningStrategy pruningStrategy;
    private String harvestPeriod;


    public BuschesAndTrees(String name, int wateringInterval, int fertilizingInterval, int pruningInterval, String height, String width, Object flowersOrNot, boolean fruitsOrNot, String pruneMonth, String harvestPeriod) {
        super(name, wateringInterval, fertilizingInterval, pruningInterval, height);
        this.width = width;
        this.flowersOrNot = (boolean) flowersOrNot;
        this.fruitsOrNot = fruitsOrNot;
        this.pruningStrategy = new TreePruningStrategy(pruneMonth);
        this.harvestPeriod = harvestPeriod;
    }

    @Override
    protected void reactionAfterFertilizer() {
        System.out.println("Har gett" + getName() + " kraftig tillväxt under säsongen.");
    }

    @Override
    protected void reactionAfterPruning() {
        System.out.println(getName() + " ger nya friska skott.");
    }

    @Override
    protected void reactionAfterWater() {
        System.out.println(getName() + " ser grön och frisk ut igen.");
    }

    @Override
    protected void prune(LocalDate today) {
        if (needsPruning(today)) {
            super.prune(today);
            pruningStrategy.prune(today);
            System.out.println("Beskurit träden inför nästa säsong " + getName());
        }
    }

    @Override
    protected String getPlantType() {
        return"Buske/Träd";
    }
    @Override
    public String getSortKey(){
        return harvestPeriod !=null ? harvestPeriod : "";
    }

    @Override
    protected void fertilize(LocalDate today) {
        if(needsFertilizer(today))
            super.fertilize(today);
        System.out.println(getName() + " har fått gödsel.");
    }

    @Override
    protected void water(LocalDate today) {
        if(needsWater(today))
            super.water(today);
        System.out.println(getName() + " har fått en rejäl rotblöta");
    }
     @Override
    public String toCsvString(){
        String safeHarvestPeriod = (harvestPeriod != null ? harvestPeriod : "Ingen skördetid");
        String strategyDescription = (pruningStrategy != null ? pruningStrategy.toString() : " Ingen Strategi");
        return String.join(",", "BuschesAndTrees", getName(),
                String.valueOf(getWateringInterval()),
                String.valueOf(getFertilizingInterval()),
                String.valueOf(getPruningInterval()),
                getHeight(),
                getWidth(),
                String.valueOf(isFlowersOrNot()),
                String.valueOf(isFruitsOrNot()),
                strategyDescription,
                safeHarvestPeriod);
    }

    // I will keep getters and setters for future use.
    public String getWidth() {
        return width;
    }

    public boolean isFlowersOrNot() {
        return flowersOrNot;
    }

    public boolean isFruitsOrNot() {
        return fruitsOrNot;
    }

    public IPruningStrategy getPruningStrategy() {
        return pruningStrategy;
    }
    public void setPruningStrategy(IPruningStrategy strategy) {
        this.pruningStrategy = strategy;
    }

    public void performPruning(LocalDate today){
        pruningStrategy.prune(today);
    }

    public String getHarvestPeriod() {
        return harvestPeriod;
    }
}

