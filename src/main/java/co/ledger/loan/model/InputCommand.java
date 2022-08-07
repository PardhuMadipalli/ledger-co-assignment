package co.ledger.loan.model;

import co.ledger.loan.enums.Operation;
import co.ledger.loan.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Model class for input command passed by the user
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class InputCommand {
    Operation operation;
    List<String> operands;

    public static InputCommand from(String command) throws InvalidInputException {
        List<String> commandSplit = new ArrayList<>(Arrays.asList(command.split("\\s+")));
        Operation operation = Operation.valueOf(commandSplit.remove(0));
        InputCommand inputCommand = new InputCommand(operation, commandSplit);
        validateCommand(inputCommand);
        return inputCommand;
    }

    private static void validateCommand(InputCommand inputCommand) throws InvalidInputException {
        if (inputCommand.operands.size() != inputCommand.operation.getNumberOfArguments()) {
            throw new InvalidInputException("Invalid number of arguments for the command.");
        }
    }
}
