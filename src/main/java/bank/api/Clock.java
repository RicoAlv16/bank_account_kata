package bank.api;

import java.time.LocalDateTime;

public interface Clock {
    LocalDateTime now();
}