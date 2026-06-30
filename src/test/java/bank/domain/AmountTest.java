package bank.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    @Test
    void should_reject_amount_when_it_is_null() {
        // Given: a null reference for the amount value
        // When: trying to create the Amount object
        // Then: it must throw an IllegalArgumentException
        assertThatThrownBy(() -> new Amount(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount cannot be null");
    }

    @Test
    void should_reject_amount_when_it_is_negative() {
        // Given: a negative amount value
        BigDecimal negativeValue = new BigDecimal("-50.00");

        // When: trying to create the Amount object
        // Then: it must throw an IllegalArgumentException
        assertThatThrownBy(() -> new Amount(negativeValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_reject_amount_when_it_is_zero() {
        // Given: an amount value of zero
        BigDecimal zeroValue = BigDecimal.ZERO;

        // When: trying to create the Amount object
        // Then: it must throw an IllegalArgumentException
        assertThatThrownBy(() -> new Amount(zeroValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_reject_amount_when_below_minimum_allowed() {
        // Given: an amount value lower than the 10.00 minimum limit
        BigDecimal belowMinValue = new BigDecimal("0.50");

        // When: trying to create the Amount object
        // Then: it must throw an IllegalArgumentException
        assertThatThrownBy(() -> new Amount(belowMinValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount is below the minimum allowed (10.00)");
    }

    @Test
    void should_reject_amount_when_above_maximum_allowed() {
        // Given: an amount value higher than the 100000.00 maximum limit
        BigDecimal aboveMaxValue = new BigDecimal("1000.01");

        // When: trying to create the Amount object
        // Then: it must throw an IllegalArgumentException
        assertThatThrownBy(() -> new Amount(aboveMaxValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount exceeds the maximum allowed");
    }
}