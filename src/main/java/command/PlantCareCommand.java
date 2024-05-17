package command;

import org.inlaming3.PlantManager;

import java.time.LocalDate;

public class PlantCareCommand implements ICommand{
    private PlantManager plantManager;
    private LocalDate simulatedToday;
    public PlantCareCommand(PlantManager plantManager){
        this.plantManager = plantManager;
        this.simulatedToday = LocalDate.now();
    }
    public void setSimulatedToday(LocalDate date){
        this.simulatedToday = date;
    }

    public void execute() {
        plantManager.checkPlantsForCareToday(simulatedToday);
    }


}
