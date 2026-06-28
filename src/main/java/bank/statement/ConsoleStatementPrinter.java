package bank.statement;

import bank.domain.Operation;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleStatementPrinter implements StatementPrinter {

    // Standardized date format (e.g., 28/06/2026)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Header line expected on a bank statement
    private static final String HEADER = "Date       | Amount     | Balance";

    @Override
    public void print(List<Operation> operations) {
        // 1. The header is printed.
        System.out.println(HEADER);

        // 2. Each formatted line is printed.
        for (Operation op : operations) {
            String dateStr = op.date().format(DATE_FORMATTER);

            // The amount is formatted: positive for a deposit, negative for a withdrawal.
            String amountStr = formatAmount(op);
            String balanceStr = String.format("%.2f", op.balanceAfterOperation());

            // Column-aligned display
            System.out.printf("%s | %-10s | %s%n", dateStr, amountStr, balanceStr);
        }
    }

    private String formatAmount(Operation operation) {
        String sign = (operation.type() == bank.domain.OperationType.DEPOSIT) ? "+" : "-";
        return sign + String.format("%.2f", operation.amount());
    }
}