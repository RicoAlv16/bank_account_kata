package bank.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private final Clock fixedClock = () -> LocalDateTime.of(2026, 6, 30, 10, 0);
    private final Account account = new Account(fixedClock);

    @Test
    void should_increase_balance_when_making_a_deposit() {
        // Given: an account with an initial balance of 0
        Amount depositAmount = new Amount(new BigDecimal("1000.00"));

        // When: 1000.00 is deposited
        account.deposit(depositAmount);

        // Then: the balance must be 1000.00
        assertThat(account.getBalance()).isEqualByComparingTo("1000.00");
    }

    @Test
    void should_decrease_balance_when_making_a_withdrawal() {
        // Given: an account with an initial balance of 1000.00
        account.deposit(new Amount(new BigDecimal("1000.00")));
        Amount withdrawalAmount = new Amount(new BigDecimal("400.00"));

        // When: 400.00 is withdrawn
        account.withdraw(withdrawalAmount);

        // Then: the balance must be 600.00
        assertThat(account.getBalance()).isEqualByComparingTo("600.00");
    }

    @Test
    void should_reject_withdrawal_when_balance_is_insufficient() {
        // Given: an account with a balance of 100.00
        account.deposit(new Amount(new BigDecimal("100.00")));

        // When: trying to withdraw 150.00
        Amount highWithdrawalAmount = new Amount(new BigDecimal("150.00"));

        // Then: it must throw an InsufficientBalanceException without modifying the balance
        assertThatThrownBy(() -> account.withdraw(highWithdrawalAmount))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("Insufficient balance for this withdrawal");
    }
}