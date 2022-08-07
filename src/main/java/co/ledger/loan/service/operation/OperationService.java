package co.ledger.loan.service.operation;

import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;

import java.util.List;
import java.util.Map;

/**
 * Interface for operation service
 */
public interface OperationService {
    String executeOperation(List<String> arguments, Map<String, Map<String, LoanAccount>> ledgerData)
            throws InvalidInputException;

    default LoanAccount getLoanAccount(String bankName,
                                       String customerName,
                                       Map<String, Map<String, LoanAccount>> ledgerData)
            throws InvalidInputException {
        Map<String, LoanAccount> bankData = ledgerData.get(bankName);
        if (bankData == null) {
            throw  new InvalidInputException("Invalid bank specified: " + bankName);
        }

        LoanAccount account = bankData.get(customerName);
        if (account == null) {
            throw new InvalidInputException("Account does not exist for the customer: " + customerName);
        }
        return account;
    }
}
