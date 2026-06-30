package bank.domain;

import java.math.BigDecimal;

public record Amount(BigDecimal value) {
    // Minimum and maximum amount allowed for deposit and withdrawal
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("10.00");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000.00");

    // Compact constructor to validate the object upon creation
    public Amount {
        if (value == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be strictly positive");
        }
        if (value.compareTo(MIN_AMOUNT) < 0) {
            throw new IllegalArgumentException("Amount is below the minimum allowed (10.00)");
        }
        if (value.compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("Amount exceeds the maximum allowed");
        }
    }
}