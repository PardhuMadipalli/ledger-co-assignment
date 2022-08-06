package co.ledger.loan;

import co.ledger.loan.dao.LedgerData;
import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.InputCommand;
import co.ledger.loan.model.LoanAccount;
import co.ledger.loan.service.InputFileProcessingService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoanApplication {

    public static void main(String[] args) throws InvalidInputException, IOException {
        validateArguments(args);
        InputFileProcessingService fileProcessingService = new InputFileProcessingService(args[0]);
//        private final  Map<String, Map<String, LoanAccount>> data = new HashMap<>();
        InputCommand command;
        while ((command = fileProcessingService.getNextCommand()) != null) {
//            System.out.println("command is " + command);
            String output = command.getOperation().getOperationService().get()
                    .executeOperation(command.getOperands(), LedgerData.getInstance());
            if (output != null) {
                System.out.println(output);
            }
        }
    }

    private static void validateArguments(String[] args) throws InvalidInputException {
        if (args.length != 1) {
            throw new InvalidInputException("There should be one and only one argument: <path-to-input-file>");
        }
    }
}
