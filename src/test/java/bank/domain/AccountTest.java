package bank.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    // Unit test for deposit

    @Test
    void should_increase_balance_when_making_a_deposit() {
        // Given: an account with an initial balance of 0
        Account account = new Account();
        BigDecimal depositAmount = new BigDecimal("1000.00");

        // When: 1000 is deposited
        account.deposit(depositAmount, LocalDateTime.now());

        // Then: the balance must be 1000
        assertThat(account.getBalance()).isEqualByComparingTo("1000.00");
    }

    @Test
    void should_reject_deposit_when_amount_is_negative() {
        // Given: a deposit of negative amount
        Account account = new Account();
        BigDecimal negativeAmount = new BigDecimal("-50.00");

        // When: -50 is deposit and then: deposit is rejected
        assertThatThrownBy(() -> account.deposit(negativeAmount, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount must be positive and greater than zero");
    }

    @Test
    void should_reject_deposit_when_amount_is_zero() {
        // Given: a deposit of zero amount
        Account account = new Account();
        BigDecimal zeroAmount = BigDecimal.ZERO;

        // When: 0 is deposit and then: deposit is rejected
        assertThatThrownBy(() -> account.deposit(zeroAmount, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount must be positive and greater than zero");
    }

    @Test
    void should_reject_deposit_when_amount_is_null() {
        // Given: a deposit of "null" amount
        Account account = new Account();

        // When: null is deposit and then: deposit is rejected
        assertThatThrownBy(() -> account.deposit(null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount must be positive and greater than zero");
    }

    @Test
    void should_accumulate_balance_after_multiple_deposits() {
        // Given: a accumulate balance of multiple deposit
        Account account = new Account();

        // When: multiple deposit is done
        account.deposit(new BigDecimal("1000.00"), LocalDateTime.now());
        account.deposit(new BigDecimal("500.50"), LocalDateTime.now());

        //  Then: balance must be 1500.50
        assertThat(account.getBalance()).isEqualByComparingTo("1500.50");
    }

    // Unit test for withdrawal

    @Test
    void should_decrease_balance_when_making_a_withdrawal() {
        // Given: a decrease of the balance after withdrawal
        Account account = new Account();
        account.deposit(new BigDecimal("1000.00"), LocalDateTime.now()); // You need to have money before withdrawing.
        BigDecimal withdrawalAmount = new BigDecimal("400.00");

        // When: 400 is withdrawn
        account.withdraw(withdrawalAmount, LocalDateTime.now());

        //  Then: balance must be 600
        assertThat(account.getBalance()).isEqualByComparingTo("600.00");
    }

    @Test
    void should_reject_withdrawal_when_amount_is_negative() {
        // Given: a withdrawal on negative balance
        Account account = new Account();
        BigDecimal negativeAmount = new BigDecimal("-50.00");

        // When: -50 would be withdrawn and then: withdrawal is rejected
        assertThatThrownBy(() -> account.withdraw(negativeAmount, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Withdrawal amount must be positive");
    }

    @Test
    void should_reject_withdrawal_when_amount_is_zero() {
        // Given: a withdrawal of 0
        Account account = new Account();
        BigDecimal zeroAmount = BigDecimal.ZERO;

        // When: 0 would be withdrawn and then: withdrawal is rejected
        assertThatThrownBy(() -> account.withdraw(zeroAmount, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Withdrawal amount must be positive");
    }

    @Test
    void should_reject_withdrawal_when_amount_is_null() {
        // Given: a withdrawal of "null" amount
        Account account = new Account();

        // When: null would be withdrawal and then: withdrawal is rejected
        assertThatThrownBy(() -> account.withdraw(null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Withdrawal amount must be positive");
    }

    @Test
    void should_reject_withdrawal_when_balance_is_insufficient() {
        // Given: a withdrawal a highest amount for insufficient balance
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"), LocalDateTime.now());
        BigDecimal highAmount = new BigDecimal("150.00");

        // When: 150 would be withdrawn for 100 in the balance and then: withdrawal is rejected for insufficient balance
        assertThatThrownBy(() -> account.withdraw(highAmount, LocalDateTime.now()))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("Insufficient balance for this withdrawal");
    }
}