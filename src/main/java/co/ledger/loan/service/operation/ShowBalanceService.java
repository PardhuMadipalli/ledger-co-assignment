package co.ledger.loan.service.operation;

import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.LoanAccount;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Show total paid amount and remaining EMIs details
 */
public class ShowBalanceService implements OperationService {

    @Getter private static final ShowBalanceService instance = new ShowBalanceService();

    private ShowBalanceService() {}

    @Override
    public String executeOperation(List<String> arguments, Map<String, Map<String, LoanAccount>> ledgerData) throws InvalidInputException {
        // BANK_NAME BORROWER_NAME EMI_NO
        LoanAccount account = getLoanAccount(arguments.get(0), arguments.get(1), ledgerData);
        int emiNum = Integer.parseInt(arguments.get(2));
        int totalPaid = account.getTotalAmountPaid(emiNum);
        int remainingEmis = account.getRemainingEmiCount(totalPaid);
        return String.format("%s %s %d %d", arguments.get(0), arguments.get(1), totalPaid, remainingEmis);
    }
}
