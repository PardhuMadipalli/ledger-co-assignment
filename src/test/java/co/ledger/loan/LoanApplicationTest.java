package co.ledger.loan;

import co.ledger.loan.exception.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanApplicationTest {

    private final PrintStream STANDARD_OUT = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @ParameterizedTest
    @CsvSource({"input3.txt,output3.txt", "input4.txt,output4.txt"})
    void mainTest(String inputFile, String outputFile) throws InvalidInputException, IOException {
        String inputFilePath = new File(getClass().getClassLoader().getResource(inputFile).getFile()).getAbsolutePath();
        String[] mainArguments = new String[]{inputFilePath};
        LoanApplication.main(mainArguments);
        String outputFilePath = new File(getClass().getClassLoader().getResource(outputFile).getFile()).getAbsolutePath();
        assertEquals(new String(Files.readAllBytes(Paths.get(outputFilePath))), outputStreamCaptor.toString().trim());
    }

    @Test
    void mainMoreThanOneInputTest() {
        String[] mainArguments = new String[]{"arg1", "arg2"};
        assertThrows(InvalidInputException.class, ()->LoanApplication.main(mainArguments));
    }

    @Test
    void mainZeroInputTest() {
        String[] mainArguments = new String[]{};
        assertThrows(InvalidInputException.class, ()->LoanApplication.main(mainArguments));
    }

    @AfterEach
    void resetSystemOut() {
        System.setOut(STANDARD_OUT);
    }
}
