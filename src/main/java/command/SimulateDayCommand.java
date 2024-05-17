package command;

import org.inlaming3.Garden;

public class SimulateDayCommand implements ICommand{
    private Garden garden;
    public SimulateDayCommand(Garden garden){
        this.garden = garden;
    }

    @Override
    public void execute() {
        garden.simulateDay();
    }
}
