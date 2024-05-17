package command;


import Strategy.ISortStrategy;
import Strategy.QuickSortStrategy;
import org.inlaming3.PlantManager;


public class ListPlantsCommand implements ICommand{
    private PlantManager plantManager;
    public ListPlantsCommand(PlantManager plantManager){
        this.plantManager = plantManager;
    }

    @Override
    public void execute() {
        ISortStrategy sortStrategy = new QuickSortStrategy();
        plantManager.setSortStrategy(sortStrategy);
        plantManager.sortPlants();
        plantManager.listOfPlants();
    }
}
