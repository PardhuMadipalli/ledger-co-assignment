package co.ledger.loan.service.operation;

import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateNewLoanServiceTest {
    private static final String BANK = "BANK_NAME";
    private static final String CUST = "CUSTOMER_NAME";

    private Map<String, Map<String, LoanAccount>> ledgerData;

    @BeforeEach
    public void setup() {
        ledgerData = new HashMap<>();
    }

    @Test
    public void createNewLoanForNonExistingUser() throws InvalidInputException {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("1000");
        operations.add("2");
        operations.add("5");
        assertNull(CreateNewLoanService.getInstance().executeOperation(operations, ledgerData));
        assertEquals(24, ledgerData.get(BANK).get(CUST).getRemainingEmiCount(0));
    }

    @Test
    public void createNewLoanForExistingUser() throws InvalidInputException {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("1000");
        operations.add("2");
        operations.add("5");
        assertNull(CreateNewLoanService.getInstance().executeOperation(operations, ledgerData));
        assertThrows(InvalidInputException.class,
                () -> CreateNewLoanService.getInstance().executeOperation(operations, ledgerData));
    }
}
