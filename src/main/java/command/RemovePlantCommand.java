package command;

import org.inlaming3.Garden;

public class RemovePlantCommand implements ICommand{
    private Garden garden;
    public RemovePlantCommand(Garden garden){
        this.garden = garden;
    }

    @Override
    public void execute() {
        garden.removePlant();
    }
}
