package co.ledger.loan.model;

import co.ledger.loan.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.TreeMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanAccount {
    int initialLoanAmount;
    TreeMap<Integer, Integer> emiNumLumpsumAmount;
    int initialEmi;

    public LoanAccount(int principal, int periodInYears, int roi) {
        int totalInterest = (int) Math.ceil((double)principal * periodInYears * roi / 100);
        this.initialLoanAmount = principal + totalInterest;
        this.initialEmi = (int) Math.ceil((double)initialLoanAmount / (periodInYears * 12));
        emiNumLumpsumAmount = new TreeMap<>();
    }

    public int getRemainingEmiCount(int totalPaid) {
        int reminingAmount = initialLoanAmount - totalPaid;
        if (reminingAmount <= 0) {
            return 0;
        }
        return (int) Math.ceil((double)reminingAmount / initialEmi);
    }

    public int getTotalAmountPaid(int emiNum) {
        int amountPaidViaEmis = initialEmi * emiNum;
        int amountPaidByLumpSums = 0;
        for (Map.Entry<Integer, Integer> entry: emiNumLumpsumAmount.entrySet()) {
            if (entry.getKey() <= emiNum) {
                amountPaidByLumpSums += entry.getValue();
            } else {
                // Lumpsums are stored in order.
                break;
            }
        }
        int totalPaid = amountPaidByLumpSums + amountPaidViaEmis;
        return Math.min(totalPaid, initialLoanAmount);
    }

    public void addLumpSumAmount(int lumpsum, int emiNum) throws InvalidInputException {
        if (lumpsum > 0) {
            emiNumLumpsumAmount.put(emiNum, lumpsum);
        } else if (lumpsum < 0) {
            throw new InvalidInputException("Invalid lumpsum amount: " + lumpsum);
        }
    }
}
