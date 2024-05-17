package org.inlaming3;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public abstract class PlantBase  {
    private String name;
    private LocalDate lastWatered;
    private LocalDate lastFertilized;
    private LocalDate lastPruned;
    private int wateringInterval;
    private int fertilizingInterval;
    private int pruningInterval;
    private String height;
    private boolean isWateredToday = false;
    private boolean isFertilizedToday = false;
    private Boolean isPrunedToday = false;

    public PlantBase(String name,  int wateringInterval, int fertilizingInterval, int pruningInterval, String height) {
        this.name = name;
        this.wateringInterval = wateringInterval;
        this.fertilizingInterval = fertilizingInterval;
        this.pruningInterval = pruningInterval;
        this.height = height;
        this.lastWatered = LocalDate.now();
        this.lastFertilized = LocalDate.now();
        this.lastPruned = LocalDate.now();
    }




    protected void plantCare(LocalDate date){  //Template method
        if(needsWater(date)){
            water(date);
            reactionAfterWater();
        }
        if(needsFertilizer(date)){
            fertilize(date);
            reactionAfterFertilizer();
        }
        if(needsPruning(date)){
            prune(date);
            reactionAfterPruning();
        }
    }

    protected abstract void reactionAfterPruning();
    protected abstract void reactionAfterFertilizer();
    protected abstract void reactionAfterWater();
    protected void water(LocalDate today){
        if(needsWater(today)){
            lastWatered = today;
            isWateredToday = true;
        }
    }
    protected void fertilize(LocalDate today) {
        if (needsFertilizer(today)) {
            lastFertilized = today;
            isFertilizedToday = true;
        }
    }
    protected boolean needsWater(LocalDate today) {
        return ChronoUnit.DAYS.between(lastWatered, today) >= wateringInterval;
    }
    protected boolean needsFertilizer(LocalDate today){
        return ChronoUnit.DAYS.between(lastFertilized, today) >= fertilizingInterval;

    }
    protected  boolean needsPruning(LocalDate today){
        if(pruningInterval <= 0){
            return false;
        }
        return ChronoUnit.DAYS.between(lastPruned, today) >= pruningInterval;
    }
    protected void prune(LocalDate today){
        if(needsPruning(today)){
            lastPruned = today;
        }
    }
    protected abstract String getPlantType();
    public abstract String getSortKey();


    // I will keep getters and setters for future use.
    public String getName() {
        return name;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }
    public void setWateringInterval(int wateringInterval) {
        this.wateringInterval = wateringInterval;
    }
    public int getFertilizingInterval() {
        return fertilizingInterval;
    }
    public void setFertilizingInterval(int fertilizingInterval) {
        this.fertilizingInterval = fertilizingInterval;
    }

    public int getPruningInterval() {
        return pruningInterval;
    }
    public void setPruningInterval(int pruningInterval) {
        this.pruningInterval = pruningInterval;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public boolean isWateredToday() {
        return isWateredToday;
    }

    public void setWateredToday(boolean wateredToday) {
        isWateredToday = wateredToday;
    }

    public boolean isFertilizedToday() {
        return isFertilizedToday;
    }

    public void setFertilizedToday(boolean fertilizedToday) {
        isFertilizedToday = fertilizedToday;
    }

    public boolean isPrunedToday() {
        return isPrunedToday;
    }

    public void setPrunedToday(boolean prunedToday) {
        isPrunedToday = prunedToday;
    }

    public String toCsvString() {
        return "";
    }
}
