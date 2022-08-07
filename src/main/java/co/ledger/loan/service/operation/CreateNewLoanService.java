package co.ledger.loan.service.operation;

import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create a new loan account
 */
public class CreateNewLoanService implements OperationService {

    @Getter private static final CreateNewLoanService instance = new CreateNewLoanService();

    private CreateNewLoanService() {}

    @Override
    public String executeOperation(List<String> arguments, Map<String, Map<String, LoanAccount>> ledgerData) throws InvalidInputException {
        // BANK_NAME BORROWER_NAME PRINCIPAL NO_OF_YEARS RATE_OF_INTEREST
        String bankName = arguments.get(0);
        Map<String, LoanAccount> bankLoanAccountsMap =
                ledgerData.computeIfAbsent(bankName, k -> new HashMap<>());
        String customerName = arguments.get(1);
        if(bankLoanAccountsMap.containsKey(customerName)) {
            throw new InvalidInputException("Customer already availed a loan from this bank.");
        }
        LoanAccount loanAccount = new LoanAccount(
                Integer.parseInt(arguments.get(2)),
                Integer.parseInt(arguments.get(3)),
                Integer.parseInt(arguments.get(4)));
        bankLoanAccountsMap.put(customerName, loanAccount);
        return null;
    }
}
