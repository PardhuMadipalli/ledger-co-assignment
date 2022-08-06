package co.ledger.loan.service;

import co.ledger.loan.exception.InvalidInputException;
import co.ledger.loan.model.InputCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Input file parser
 */
public class InputFileProcessingService {

    private final BufferedReader reader;

    public InputFileProcessingService(String filePath) throws FileNotFoundException, InvalidInputException {
        File inputFile = new File(filePath);
        if (!inputFile.isFile())
            throw new InvalidInputException("File path is invalid or it is not a valid file.");
        reader = new BufferedReader(new FileReader(inputFile));
    }

    public InputCommand getNextCommand() throws IOException, InvalidInputException {
        String inputLine = reader.readLine();
        if (inputLine == null) {
            reader.close();
            return null;
        }
        inputLine = inputLine.trim();
        if (inputLine.isEmpty()) {
            // Process the next line
            return getNextCommand();
        }
        return InputCommand.from(inputLine);
    }
}
