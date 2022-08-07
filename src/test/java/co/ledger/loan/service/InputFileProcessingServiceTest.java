package co.ledger.loan.service;

import co.ledger.loan.enums.Operation;
import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.InputCommand;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputFileProcessingServiceTest {
    private static final String INPUT_FILE = "input1.txt";

    @Test
    void newInputFileProcessingTest() throws InvalidInputException, IOException {
        File file = new File(getClass().getClassLoader().getResource(INPUT_FILE).getFile());
        InputFileProcessingService inputFileProcessingService = new InputFileProcessingService(file.getPath());
        InputCommand command = inputFileProcessingService.getNextCommand();
        assertEquals(Operation.LOAN, command.getOperation());
        assertNull(inputFileProcessingService.getNextCommand());
    }

    @Test
    void folderAsInputFilePath() {

        assertThrows(InvalidInputException.class, () ->
                new InputFileProcessingService(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath()));
    }

    @Test
    void callNextCommandForFileWithEmptyLines() throws InvalidInputException, IOException {
        File file = new File(getClass().getClassLoader().getResource("input2.txt").getFile());
        InputFileProcessingService inputFileProcessingService = new InputFileProcessingService(file.getPath());
        assertEquals(Operation.LOAN, inputFileProcessingService.getNextCommand().getOperation());
        assertEquals(Operation.PAYMENT, inputFileProcessingService.getNextCommand().getOperation());
    }

}
