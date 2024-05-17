package org.inlaming3;

import java.time.LocalDate;


public class Flower extends PlantBase{
    String flowerColor;
    String floweringPeriod;
    boolean perennialOrNot;


    public Flower(String name, int wateringInterval, int fertilizingInterval, int pruningInterval, String height, String flowerColor, Object floweringPeriod, boolean perennialOrNot) {
        super(name, wateringInterval, fertilizingInterval, pruningInterval, height);
        this.flowerColor = flowerColor;
        this.floweringPeriod = floweringPeriod.toString();
        this.perennialOrNot = perennialOrNot;
    }

    @Override
    protected void reactionAfterPruning() {
        System.out.println(getName() + " gett plats åt nya blommor. ");
    }
    @Override
    protected void reactionAfterWater() {
        System.out.println(getName() + " börjar återhämta sig");
    }
    @Override
    protected void reactionAfterFertilizer() {
        System.out.println(getName() + " har fått ny fart i tillväxten.");
    }
    @Override
    protected String getPlantType() {
        return "Blomma";
    }
    @Override
    public String getSortKey(){
        return"";
    }

    @Override
    protected void fertilize(LocalDate today) {
        if(needsFertilizer(today))
            super.fertilize(today);
        System.out.println(getName() + " har fått kogödsel.");
    }

    @Override
    protected void water(LocalDate today) {
        if(needsWater(today))
            super.water(today);
        System.out.println(getName() + " har fått vatten 'manuellt'");
    }
    @Override
    protected void prune(LocalDate today){
        if(needsPruning(today))
            super.prune(today);
        System.out.println("Tagit bort döda delar på " + getName());
    }
    @Override
    public String toCsvString(){
        return String.join(",", "Flower", getName(), String.valueOf(getWateringInterval()),String.valueOf(getFertilizingInterval()),
        String.valueOf(getPruningInterval()), getHeight(), getFlowerColor(), getFloweringPeriod(), String.valueOf(isPerennialOrNot()));
    }


    //I will keep getters and setters for future use.
    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public String getFloweringPeriod() {
        return floweringPeriod;
    }

    public void setFloweringPeriod(String floweringPeriod) {
        this.floweringPeriod = floweringPeriod;
    }

    public boolean isPerennialOrNot() {
        return perennialOrNot;
    }

    public void setPerennialOrNot(boolean perennialOrNot) {
        this.perennialOrNot = perennialOrNot;
    }
}



