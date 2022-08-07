package co.ledger.loan.model;

import co.ledger.loan.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanAccountTest {

    private static final int PRINCIPAL = 1000;
    private static final int NUMBER_OF_YEARS = 2;
    private static final int ROI = 10;

    @Test
    void loanAccountTest() {
        LoanAccount loanAccount = new LoanAccount(PRINCIPAL, NUMBER_OF_YEARS, ROI);
        int amount = (int) (PRINCIPAL*(1+((double)NUMBER_OF_YEARS*ROI/100)));
        assertEquals(amount, loanAccount.getInitialLoanAmount());

        int expectedEmi = (int) (amount/((double)NUMBER_OF_YEARS*12));
        assertEquals(expectedEmi, loanAccount.getInitialEmi());
        assertEquals(expectedEmi*2, loanAccount.getTotalAmountPaid(2));
    }

    @Test
    void loanAccountTestFloatingEmi(){
        LoanAccount loanAccount = new LoanAccount(PRINCIPAL, 1, ROI);
        int amount = (int) (PRINCIPAL*(1+((double)1*ROI/100)));
        assertEquals(amount, loanAccount.getInitialLoanAmount());

        int expectedEmi = (int) Math.ceil(amount/((double)12));
        assertEquals(expectedEmi, loanAccount.getInitialEmi());
        assertEquals(expectedEmi*2, loanAccount.getTotalAmountPaid(2));
    }

    @Test
    void loanAccountAddLumpsum() throws InvalidInputException {
        LoanAccount loanAccount = new LoanAccount(PRINCIPAL, NUMBER_OF_YEARS, ROI);
        assertEquals(0, loanAccount.getEmiNumLumpsumAmount().size());
        loanAccount.addLumpSumAmount(100, 2);
        assertEquals(1, loanAccount.getEmiNumLumpsumAmount().size());
        assertEquals((Integer) 100, loanAccount.getEmiNumLumpsumAmount().get(2));
    }

    @Test
    void loanAccountAddNegativeLumpsum() {
        LoanAccount loanAccount = new LoanAccount(PRINCIPAL, NUMBER_OF_YEARS, ROI);
        assertEquals(0, loanAccount.getEmiNumLumpsumAmount().size());
        assertThrows(InvalidInputException.class, ()->loanAccount.addLumpSumAmount(-100, 2));
    }

    @Test
    void loanAccountAddZeroLumpsum() throws InvalidInputException {
        LoanAccount loanAccount = new LoanAccount(PRINCIPAL, NUMBER_OF_YEARS, ROI);
        assertEquals(0, loanAccount.getEmiNumLumpsumAmount().size());
        loanAccount.addLumpSumAmount(0, 2);
        assertEquals(0, loanAccount.getEmiNumLumpsumAmount().size());
    }
}
