package command;

import org.inlaming3.Garden;


public class AddPlantCommand implements ICommand{
    private Garden garden;
    public AddPlantCommand(Garden garden){
        this.garden = garden;
    }

    @Override
    public void execute() {
        garden.addNewPlant();

    }
}
