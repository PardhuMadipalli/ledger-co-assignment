package co.ledger.loan.model;

import co.ledger.loan.enums.Operation;
import co.ledger.loan.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputCommandTest {

    @Test
    void fromValidTest() throws InvalidInputException {
        InputCommand inputCommand = InputCommand.from("LOAN BANK NAME 5000 10 5");
        assertEquals(Operation.LOAN, inputCommand.getOperation());

        List<String> arguments = new ArrayList<>();
        arguments.add("BANK");
        arguments.add("NAME");
        arguments.add("5000");
        arguments.add("10");
        arguments.add("5");
        assertEquals(arguments, inputCommand.getOperands());
    }

    @Test
    void fromInvalidValidTest() {
        assertThrows(InvalidInputException.class, ()->InputCommand.from("LOAN BANK NAME 10 5"));
    }

    @Test
    void fromInvalidOperationTest() {
        assertThrows(IllegalArgumentException.class, ()->InputCommand.from("INVALID BANK NAME 10 5"));
    }
}
