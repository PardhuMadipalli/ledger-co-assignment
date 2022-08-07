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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowBalanceServiceTest {
    private static final String BANK = "BANK_NAME";
    private static final String CUST = "CUSTOMER_NAME";

    private Map<String, Map<String, LoanAccount>> ledgerData;

    @BeforeEach
    public void setup() {
        ledgerData = new HashMap<>();
        LoanAccount loanAccount = new LoanAccount(1000, 2, 5);
        Map<String, LoanAccount> custAccountMap = new HashMap<>();
        custAccountMap.put(CUST, loanAccount);
        ledgerData.put(BANK, custAccountMap);
    }

    @Test
    public void showBalanceTest() throws InvalidInputException {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("3");
        assertEquals("BANK_NAME CUSTOMER_NAME 138 21",
                ShowBalanceService.getInstance().executeOperation(operations, ledgerData));
    }

    @Test
    public void showBalanceNonExistingBankTest() {
        List<String> operations = new ArrayList<>();
        operations.add("BANK2");
        operations.add(CUST);
        operations.add("3");
        assertThrows(InvalidInputException.class, () ->
                ShowBalanceService.getInstance().executeOperation(operations, ledgerData));
    }

    @Test
    public void showBalanceNonExistingCustomerTest() {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add("CUSTOMER_NEW");
        operations.add("3");
        assertThrows(InvalidInputException.class, () ->
                ShowBalanceService.getInstance().executeOperation(operations, ledgerData));
    }
}
