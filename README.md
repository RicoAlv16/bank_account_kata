# Bank Account Kata

A Java implementation of the **Bank Account Kata**, created as part of a technical assessment. The goal: to demonstrate a TDD-oriented approach, clean code, and a suitable architecture—without over-engineering.

## Exercise Context

> *Think of your personal bank account experience. When in doubt, go for the simplest solution.*

The kata requires providing **a service API and its underlying implementation**, without a user interface or persistence. It addresses three functional requirements outlined in three user stories:

| # | User Story | Requirement |
|---|---|---|
| US1 | As a customer, I want to make a **deposit** into my account | To save money |
| US2 | As a customer, I want to make a **withdrawal** from my account | To access some or all of my savings |
| US3 | As a customer, I want to view my transaction **history** (transaction type, date, amount, balance) | To check my transactions |


## Specifications
In accordance with the requirements, this project focuses exclusively on the **Core Domain**:
- **No frameworks** (Pure Java 17+)
- **No persistence** (In-memory management)
- **No GUI / Web interface**

---

## Architectural

### 1. Domain-Driven Model & Clean Code
To demonstrate engineering rigor without over-engineering, the project is organized into three clear packages that respect the Single Responsibility Principle (SRP):
- `bank.domain`: Contains core business rules (`Account`, immutable `Operation` objects using Java `Records`, and business exceptions).
- `bank.api`: Defines entry points and orchestration (`AccountService`, `Clock`).
- `bank.statement`: Manages output and display logic (`StatementPrinter`).

### 2. Financial Precision
All amounts and balances are handled using the **`BigDecimal`** type (verified via `isEqualByComparingTo` in tests). Floating-point types (`double`, `float`) were avoided to prevent rounding errors, which are unacceptable in the banking domain.

### 3. Concurrency & Data Consistency (*Safety by Design*)
Since the account lifecycle runs entirely in-memory without database locks, thread safety is guaranteed directly at the JVM level. The state-mutating methods of the `Account` class are **`synchronized`**, protecting the balance against race conditions during simultaneous multi-threaded access.

### 4. Time Control (Testability)
To reliably validate the reverse-chronological sorting of the bank statement, a `Clock` interface is injected; this allows time to be frozen and controlled during unit and integration tests, thereby avoiding tight coupling with `LocalDateTime.now()`.

---

## Testing Strategy (TDD)

The project was developed using a strict **TDD (Test-Driven Development)** approach (*Red -> Green -> Refactor*).

- **Domain Tests (`AccountTest`)** : Exhaustive validation of nominal and edge cases (rejection of negative or zero amounts, `null` values, or insufficient funds).
- **Integration Tests (`AccountServiceIntegrationTest`)** : Use of a *Stub* for the clock and a *Spy* to capture the data flow, validating orchestration behavior and reverse-chronological sorting without dependency on the machine's locale.
- **The Live Demo (`ConsoleStatementPrinterTest`)**: A demonstration method has been included to visually validate the bank statement rendering in the console.

---

## Architectural Evolution & Refactoring (V2 – DDD Alignment)

Previously designed with distributed validation and external temporal orchestration, the codebase underwent a major architectural overhaul to meet the standards of a highly secure banking application:

1. **Value Object Pattern (`Amount`)** : Centralization of initial checks for financial flows. The `Account` entity no longer validates the format or raw consistency of the amount; instead, it receives an `Amount` object whose validity is contractually guaranteed by Java's type system.
2. **Immutability and Encapsulation** : Definition of strict financial thresholds managed directly within the domain (deposit/withdrawal floor of 10.00; ceiling of 1000,000.00).
3. **Resilience Against Temporal Vulnerabilities** : Removal of `LocalDateTime` parameters from execution signatures. The domain entity encapsulates its own `Clock`, preventing any attempts at backdating or tampering with transaction logs from outside the application.
4. **Clean Architectural Testing (Given-When-Then)** : Clarification of the unit test suite, restructured according to the components' actual responsibilities. Eliminating redundant validation logic ensures optimal long-term code maintainability.

## Installation and Execution

### Prerequisites
- **Java 21**
- **Maven 3.6+**

### Running Tests
To execute the entire unit and integration test suite:
```bash
mvn test
```
<br><br><br><br>

<div align="right" style="color: gray;">Produced by D.A</div>   
 