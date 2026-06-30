package bank.api;

import bank.api.AccountService;
import bank.api.AccountServiceImpl;
import bank.domain.Account;
import bank.domain.Clock;
import bank.domain.Operation;
import bank.domain.OperationType;
import bank.statement.StatementPrinter;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceIntegrationTest {

    @Test
    void should_record_transactions_and_print_statement_in_reverse_chronological_order() {
        // Given: three different dates to simulate sequential operations
        LocalDateTime date1 = LocalDateTime.of(2026, 6, 26, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2026, 6, 27, 11, 0);
        LocalDateTime date3 = LocalDateTime.of(2026, 6, 28, 12, 0);

        // Given: a controllable Clock stub to simulate time passing at each call
        Clock controllableClock = new Clock() {
            private int callCount = 0;
            @Override
            public LocalDateTime now() {
                callCount++;
                if (callCount == 1) return date1;
                if (callCount == 2) return date2;
                return date3;
            }
        };

        // Given: a Spy printer to capture the operations sent by the service
        List<Operation> capturedOperations = new ArrayList<>();
        StatementPrinter spyPrinter = (operations) -> capturedOperations.addAll(operations);

        // Given: the account wired with the clock, and the service wired with the account and printer
        Account account = new Account(controllableClock);
        AccountService accountService = new AccountServiceImpl(account, spyPrinter);

        // When: sequential actions are performed and the statement printing is requested
        accountService.deposit(new BigDecimal("1000.00")); // will capture date1
        accountService.deposit(new BigDecimal("300.00")); // will capture date2
        accountService.withdraw(new BigDecimal("500.00"));  // will capture date3
        accountService.printStatement();

        // Then: the captured list must contain exactly 3 operations
        assertThat(capturedOperations).hasSize(3);

        // Then: the operations must be ordered from the most recent to the oldest (reverse chronological)
        assertThat(capturedOperations.get(0)).isEqualTo(new Operation(OperationType.WITHDRAWAL, date3, new BigDecimal("500.00"), new BigDecimal("800.00")));
        assertThat(capturedOperations.get(1)).isEqualTo(new Operation(OperationType.DEPOSIT, date2, new BigDecimal("300.00"), new BigDecimal("1300.00")));
        assertThat(capturedOperations.get(2)).isEqualTo(new Operation(OperationType.DEPOSIT, date1, new BigDecimal("1000.00"), new BigDecimal("1000.00")));
    }

}