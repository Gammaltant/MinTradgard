package command;

import org.inlaming3.PlantManager;

import java.time.LocalDate;
import java.util.function.Supplier;

public class FertilizeCommand implements  ICommand{
    private PlantManager plantManager;
    private Supplier<LocalDate> dateSupplier;
    public FertilizeCommand(PlantManager plantManager, Supplier<LocalDate> currentDateSupplier){
        this.plantManager = plantManager;
        this.dateSupplier = currentDateSupplier;
    }

    @Override
    public void execute() {
        LocalDate today = dateSupplier.get();
        plantManager.fertilizePlants(today);
    }
}
