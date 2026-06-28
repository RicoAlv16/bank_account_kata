package bank.api;

import bank.domain.Account;
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
        // Given: tree date of operations
        LocalDateTime date1 = LocalDateTime.of(2026, 6, 26, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2026, 6, 27, 11, 0);
        LocalDateTime date3 = LocalDateTime.of(2026, 6, 28, 12, 0);

        // Unique stub to control time on each call
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

        // Spy to capture what is sent to the printer
        List<Operation> capturedOperations = new ArrayList<>();
        StatementPrinter spyPrinter = capturedOperations::addAll;

        Account account = new Account();
        // Expected API of the exercise
        AccountService accountService = new AccountServiceImpl(account, controllableClock, spyPrinter);

        // when: deposit of 1000 and 2000 and withdrawal of 500 in tree dates
        accountService.deposit(new BigDecimal("1000.00")); // date1
        accountService.deposit(new BigDecimal("2000.00")); // date2
        accountService.withdraw(new BigDecimal("500.00"));  // date3
        accountService.printStatement();

        // Then: capture of 3 operations
        assertThat(capturedOperations).hasSize(3);

        // Expected reverse chronological order: newest to oldest
        assertThat(capturedOperations.get(0)).isEqualTo(new Operation(OperationType.WITHDRAWAL, date3, new BigDecimal("500.00"), new BigDecimal("2500.00")));
        assertThat(capturedOperations.get(1)).isEqualTo(new Operation(OperationType.DEPOSIT, date2, new BigDecimal("2000.00"), new BigDecimal("3000.00")));
        assertThat(capturedOperations.get(2)).isEqualTo(new Operation(OperationType.DEPOSIT, date1, new BigDecimal("1000.00"), new BigDecimal("1000.00")));
    }
}