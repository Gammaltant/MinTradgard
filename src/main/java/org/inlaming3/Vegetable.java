package org.inlaming3;

import java.time.LocalDate;

public class Vegetable extends PlantBase{
     private String harvestPeriod;


     public Vegetable(String name, int wateringInterval, int fertilizingInterval, int pruningInterval, String height, String harvestPeriod) {
          super(name, wateringInterval, fertilizingInterval, pruningInterval, height);
          this.harvestPeriod = harvestPeriod;
     }
     @Override
     protected void reactionAfterFertilizer() {
          System.out.println(getName() + " kommer snart att ge skörd.");
     }
     @Override
     protected void reactionAfterPruning() {
          System.out.println("Har rensat torra, vissna delar på " + getName() + "plantan.");
     }
     @Override
     protected void reactionAfterWater() {
          System.out.println(getName() + " återhämtar sig.");
     }
     @Override
     protected void prune(LocalDate today) {
          if(needsPruning(today))
              super.prune(today);
          System.out.println("Rensat bort döda delar och gallrat " + getName());
     }
     @Override
     protected void fertilize(LocalDate today) {
          if(needsFertilizer(today))
               super.fertilize(today);
          System.out.println(getName() + " har fått hönsgödsel.");
     }
     @Override
     protected void water(LocalDate today) {
          if(needsWater(today))
               super.water(today);
          System.out.println(getName() + " har droppbevattnats hela dagen.");
     }
     @Override
     public String toCsvString(){
          return String.join(",", "Vegetable", getName(), String.valueOf(getWateringInterval()),String.valueOf(getFertilizingInterval()),
                  String.valueOf(getPruningInterval()), getHeight(), getHarvestPeriod());
     }
     @Override
     protected String getPlantType() {
          return "Grönsak";
     }

     public String getHarvestPeriod() {
          return harvestPeriod;
     }

     // For future use.
     public void setHarvestPeriod(String harvestPeriod) {
          this.harvestPeriod = harvestPeriod;
     }
     public String getSortKey(){
          return harvestPeriod !=null ? harvestPeriod : "";
     }



}
