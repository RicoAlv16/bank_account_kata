package bank.domain;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance = BigDecimal.ZERO;

    public synchronized void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive and greater than zero");
        }
        this.balance = this.balance.add(amount);
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public synchronized void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        // Business rule: no overdrafts allowed on this exercise
        if (amount.compareTo(this.balance) > 0) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal");
        }

        this.balance = this.balance.subtract(amount);
    }
}