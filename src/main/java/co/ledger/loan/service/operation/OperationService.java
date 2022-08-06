package co.ledger.loan.service.operation;

import co.ledger.loan.dao.LedgerData;
import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;

import java.util.List;
import java.util.Map;

/**
 * Interface for operation service
 */
public interface OperationService {
    public String executeOperation(List<String> arguments, LedgerData ledgerData) throws InvalidInputException;

    default LoanAccount getLoanAccount(String bankName, String customerName, LedgerData ledgerData)
            throws InvalidInputException {
        Map<String, LoanAccount> bankData = ledgerData.getData().get(bankName);
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
