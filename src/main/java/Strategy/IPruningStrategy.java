package Strategy;

import java.time.LocalDate;

public interface IPruningStrategy {
    void prune(LocalDate today);
}
