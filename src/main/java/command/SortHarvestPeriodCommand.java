package command;

import Strategy.ISortStrategy;
import Strategy.MergeSortStrategy;
import org.inlaming3.PlantManager;

public class SortHarvestPeriodCommand implements ICommand{
    private PlantManager plantManager;
    public SortHarvestPeriodCommand(PlantManager plantManager){
        this.plantManager = plantManager;
    }

    @Override
    public void execute() {
        ISortStrategy sortStrategy = new MergeSortStrategy();
        plantManager.setSortStrategy(sortStrategy);
        plantManager.sortPlants();
        plantManager.displayPlantsWithHarvestPeriod();

    }
}
