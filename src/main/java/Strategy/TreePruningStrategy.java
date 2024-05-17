package Strategy;

import java.time.LocalDate;
import java.time.Month;

public class TreePruningStrategy implements IPruningStrategy {
    private String pruneMonth;

    public TreePruningStrategy(String pruneMonth){
        this.pruneMonth = pruneMonth;
    }

    @Override
    public void prune(LocalDate today) {
        if (pruneMonth != null && !pruneMonth.isEmpty()) {
            Month currentMonth = today.getMonth();
            Month pruneMonthEnum = Month.valueOf(pruneMonth.toUpperCase());
            if (currentMonth == pruneMonthEnum) {
                System.out.println("Har beskurit träden inför nästa säsong.");
            }
        }

    }
    @Override
    public String toString(){
        return pruneMonth;

    }
}
