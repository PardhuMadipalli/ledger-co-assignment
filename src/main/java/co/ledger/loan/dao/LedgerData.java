package co.ledger.loan.dao;

import co.ledger.loan.model.LoanAccount;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LedgerData {

    @Getter private Map<String, Map<String, LoanAccount>> data;
    private static LedgerData instance;

    public static LedgerData getInstance() {
        if (instance == null) {
           instance = new LedgerData();
           instance.data = new HashMap<>();
        }
        return instance;
    }

    private LedgerData() {}

    public int getNumberOfBanks() {
        return this.data.keySet().size();
    }
}
