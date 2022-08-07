package co.ledger.loan.service.operation;

import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;
import co.ledger.loan.service.operation.AddPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddPaymentServiceTest {

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
    public void addNewPaymentLumpSum() throws InvalidInputException {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("100");
        operations.add("2");
        // No output should be returned
        assertNull(AddPaymentService.getInstance().executeOperation(operations, ledgerData));
        assertEquals(ledgerData.get(BANK).get(CUST).getTotalAmountPaid(2), 192);
        assertEquals(ledgerData.get(BANK).get(CUST).getTotalAmountPaid(3), 238);
    }

    @Test
    public void addNewPaymentTwoLumpSumsCheckInBetween() throws InvalidInputException {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("100");
        operations.add("2");
        // No output should be returned
        assertNull(AddPaymentService.getInstance().executeOperation(operations, ledgerData));
        assertEquals(ledgerData.get(BANK).get(CUST).getTotalAmountPaid(2), 192);
        assertEquals(ledgerData.get(BANK).get(CUST).getTotalAmountPaid(3), 238);

        operations.clear();

        operations.add(BANK);
        operations.add(CUST);
        operations.add("200");
        operations.add("5");
        assertNull(AddPaymentService.getInstance().executeOperation(operations, ledgerData));
        assertEquals(ledgerData.get(BANK).get(CUST).getTotalAmountPaid(3), 238);

    }

    @Test
    public void addNewPaymentVeryHighLumpSum() throws InvalidInputException {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("10000");
        operations.add("2");
        // No output should be returned
        assertNull(AddPaymentService.getInstance().executeOperation(operations, ledgerData));

        // maximum amount paid will be only the initial amount
        assertEquals(1100, ledgerData.get(BANK).get(CUST).getTotalAmountPaid(2));

        // number of EMIs paid will be 0, if more than initial amount is paid
        assertEquals(0, ledgerData.get(BANK).get(CUST).getRemainingEmiCount(10000));
    }

    @Test
    public void addNewPaymentNegativeLumpSum() {
        List<String> operations = new ArrayList<>();
        operations.add(BANK);
        operations.add(CUST);
        operations.add("-10000");
        operations.add("2");
        assertThrows(InvalidInputException.class,
                () -> AddPaymentService.getInstance().executeOperation(operations, ledgerData));
    }
}
