package bank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Operation> operations = new ArrayList<>();

    public synchronized void deposit(BigDecimal amount, LocalDateTime date) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive and greater than zero");
        }
        this.balance = this.balance.add(amount);
        this.operations.add(new Operation(OperationType.DEPOSIT, date, amount, this.balance));
    }

    public synchronized void withdraw(BigDecimal amount, LocalDateTime date) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        // Business rule: no overdrafts allowed on this exercise
        if (amount.compareTo(this.balance) > 0) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal");
        }
        this.balance = this.balance.subtract(amount);
        this.operations.add(new Operation(OperationType.WITHDRAWAL, date, amount, this.balance));
    }

    public synchronized BigDecimal getBalance() {
        return this.balance;
    }

    public synchronized List<Operation> getOperations() {
        // return an unmodifiable list to prevent alteration of the history
        return Collections.unmodifiableList(this.operations);
    }
}