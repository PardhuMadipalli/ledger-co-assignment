package co.ledger.loan.enums;

import co.ledger.loan.service.operation.CreateNewLoanService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest {
    @Test
    void operationServiceTest() {
        assertEquals(CreateNewLoanService.getInstance(), Operation.LOAN.getOperationService().get());
    }
}
