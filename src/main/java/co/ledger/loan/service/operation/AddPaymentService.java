package co.ledger.loan.service.operation;

import co.ledger.loan.dao.LedgerData;
import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;
import lombok.Getter;

import java.util.List;

/**
 * Add a new loan payment to the relevant account.
 */
public class AddPaymentService implements OperationService {
    @Getter private static final AddPaymentService instance = new AddPaymentService();

    private AddPaymentService() {}

    @Override
    public String executeOperation(List<String> arguments, LedgerData ledgerData) throws InvalidInputException {
        // BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO
        LoanAccount account = getLoanAccount(arguments.get(0), arguments.get(1), ledgerData);
        account.addLumpSumAmount(Integer.parseInt(arguments.get(2)), Integer.parseInt(arguments.get(3)));
        return null;
    }
}
