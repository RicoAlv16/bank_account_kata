package bank.domain;

import java.time.LocalDateTime;

public interface Clock {
    LocalDateTime now();
}