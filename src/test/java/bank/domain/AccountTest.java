package bank.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @Test
    void should_increase_balance_when_making_a_deposit() {
        // Given: an account with an initial balance of 0
        Account account = new Account();
        BigDecimal depositAmount = new BigDecimal("1000.00");

        // When: 1000 is deposited
        account.deposit(depositAmount);

        // Then: the balance must be 1000
        assertThat(account.getBalance()).isEqualByComparingTo("1000.00");
    }

    @Test
    void should_reject_deposit_when_amount_is_negative() {
        // Given: a deposit of negative amount
        Account account = new Account();
        BigDecimal negativeAmount = new BigDecimal("-50.00");

        // When: -50 is deposit and then: deposit is rejected
        assertThatThrownBy(() -> account.deposit(negativeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount must be positive and greater than zero");
    }

    @Test
    void should_reject_deposit_when_amount_is_zero() {
        // Given: a deposit of zero amount
        Account account = new Account();
        BigDecimal zeroAmount = BigDecimal.ZERO;

        // When: 0 is deposit and then: deposit is rejected
        assertThatThrownBy(() -> account.deposit(zeroAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount must be positive and greater than zero");
    }

    @Test
    void should_reject_deposit_when_amount_is_null() {
        // Given: a deposit of "null" amount
        Account account = new Account();

        // When: null is deposit and then: deposit is rejected
        assertThatThrownBy(() -> account.deposit(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount must be positive and greater than zero");
    }

    @Test
    void should_accumulate_balance_after_multiple_deposits() {
        // Given: a accumulate balance of multiple deposit
        Account account = new Account();

        // When: multiple deposit is done
        account.deposit(new BigDecimal("1000.00"));
        account.deposit(new BigDecimal("500.50"));

        //  Then: deposit must be 1500.50
        assertThat(account.getBalance()).isEqualByComparingTo("1500.50");
    }
}