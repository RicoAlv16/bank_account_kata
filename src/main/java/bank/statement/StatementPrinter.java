package bank.statement;

import bank.domain.Operation;
import java.util.List;

public interface StatementPrinter {
    void print(List<Operation> operations);
}