package bank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Operation> operations = new ArrayList<>();
    private Clock clock;

    // The clock is injected upon account creation
    public Account(Clock clock) {
        if (clock == null) {
            throw new IllegalArgumentException("Clock cannot be null");
        }
        this.clock = clock;
    }

    public synchronized void deposit(Amount amount) {
        this.balance = this.balance.add(amount.value());
        this.operations.add(new Operation(OperationType.DEPOSIT, clock.now(), amount.value(), this.balance));
    }

    public synchronized void withdraw(Amount amount) {
        // Business rule: no overdrafts allowed on this exercise
        if (amount.value().compareTo(this.balance) > 0) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal");
        }
        this.balance = this.balance.subtract(amount.value());
        this.operations.add(new Operation(OperationType.WITHDRAWAL, clock.now(), amount.value(), this.balance));
    }

    public synchronized BigDecimal getBalance() {
        return this.balance;
    }

    public synchronized List<Operation> getOperations() {
        // return an unmodifiable list to prevent alteration of the history
        return Collections.unmodifiableList(this.operations);
    }
}