package co.ledger.loan.enums;

import co.ledger.loan.service.operation.AddPaymentService;
import co.ledger.loan.service.operation.CreateNewLoanService;
import co.ledger.loan.service.operation.OperationService;
import co.ledger.loan.service.operation.ShowBalanceService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.function.Supplier;

/**
 * Operations supported by ledger
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum Operation {
    LOAN(5, CreateNewLoanService::getInstance),
    PAYMENT(4, AddPaymentService::getInstance),
    BALANCE(3,ShowBalanceService::getInstance);

    int numberOfArguments;
    Supplier<? extends OperationService> operationService;
}
