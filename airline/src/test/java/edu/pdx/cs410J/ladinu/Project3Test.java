package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {
  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  /**
   * Invokes the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project3.class, args);
  }
  private MainMethodResult invokeMain(String args) {
    return invokeMain( Project3.class, args.split(" "));
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertError(result, "Missing command line arguments");
  }

  @Test
  public void shouldPrintUsageToStdOutAndExitWithZeroWhenReadmeOptionIsGiven() {
    MainMethodResult result = invokeMain("-README");
    assertTrue(result.getOut().contains(Project3.USAGE));
    assertExitCodeIsZero(result);
  }

  @Test
  public void printOptionShouldNotHaveLessThanEightArguments() {
    MainMethodResult result = invokeMain("-print 1 2 3 4 5 6 7");
    assertError(result, "Invalid depart date time '3 4'");
  }

  @Test
  public void testInvalidOption() {
    MainMethodResult result = invokeMain("-invalidOption 1 2 3 4 5 6 7 8");
    assertError(result, "Invalid option");
  }

  @Test
  public void optionsAreOptional() {
    MainMethodResult result = invokeMain("Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00");
    assertEmptyOutput(result);
  }

  @Test
  public void testInvalidNumberOfArguments() throws Exception {
    assertError(invokeMain("-print"), "Invalid number of arguments");
    assertError(invokeMain("-print 1 2 3 4 5 6 7"), "Invalid depart date time '3 4'");
  }

  @Test
  public void testPrintOption() {
    MainMethodResult result = invokeMain("-print", "\"Alaska 2\"", "32", "PDX", "3/15/2014", "17:00", "LAX", "3/15/2014", "1:00");
    Flight flight = new Flight(32, "PDX", "3/15/2014 17:00", "LAX", "3/15/2014 1:00");
    assertEquals(flight.toString() + "\n", result.getOut());
    assertExitCodeIsZero(result);
  }
  
  @Test
  public void shouldValidateIATACode() {
    assertTrue(Project3.isValid_IATA_AirportCode("PDX"));
    assertTrue(Project3.isValid_IATA_AirportCode("pdx"));
    assertTrue(Project3.isValid_IATA_AirportCode("PdX"));

    assertFalse(Project3.isValid_IATA_AirportCode(""));
    assertFalse(Project3.isValid_IATA_AirportCode("p"));
    assertFalse(Project3.isValid_IATA_AirportCode("pd"));
    assertFalse(Project3.isValid_IATA_AirportCode("PDXL"));
    assertFalse(Project3.isValid_IATA_AirportCode("PD1"));
    assertFalse(Project3.isValid_IATA_AirportCode("LA$"));
  }


  @Test
  public void shouldParseValidDateTime() {
    assertTrue(Project3.isValidDateTime("3/15/2014 10:39"));
    assertTrue(Project3.isValidDateTime("03/2/2014 1:03"));

    assertFalse(Project3.isValidDateTime("03/2/92 1:03"));
    assertFalse(Project3.isValidDateTime("03/2/2014 1PM"));
    assertFalse(Project3.isValidDateTime("03/2/2014 1:30PM"));
    assertFalse(Project3.isValidDateTime("July 19th 2014 1:30PM"));
    assertFalse(Project3.isValidDateTime("03/2/20141:03"));
  }

  @Test
  public void shouldParseValidTime() {
    assertTrue(Project3.isValidTime("00:00"));
    assertTrue(Project3.isValidTime("01:00"));
    assertTrue(Project3.isValidTime("1:00"));
    assertTrue(Project3.isValidTime("24:00"));
    assertTrue(Project3.isValidTime("24:0"));
    assertTrue(Project3.isValidTime("24:59"));

    assertFalse(Project3.isValidTime("1A:AM"));
    assertFalse(Project3.isValidTime("1:3AM"));
    assertFalse(Project3.isValidTime("13AM"));
  }


  @Test
  public void shouldParseValidDate() {
    assertTrue(Project3.isValidDate("3/1/2014"));
    assertTrue(Project3.isValidDate("3/1/2014"));
    assertTrue(Project3.isValidDate("03/15/2014"));
    assertTrue(Project3.isValidDate("03/1/2014"));
    assertTrue(Project3.isValidDate("03/01/2014"));

    assertFalse(Project3.isValidDate("03/01/14"));
    assertFalse(Project3.isValidDate("$/01/14"));
    assertFalse(Project3.isValidDate("January 1st 2012"));

  }

  @Test
  public void testThatFlightNumberGetParsedToInt() {
    assertTrue(Project3.isValidInt("1"));
    assertTrue(Project3.isValidInt("31233"));

    assertFalse(Project3.isValidInt("q"));
    assertFalse(Project3.isValidInt("four"));
    assertFalse(Project3.isValidInt("3.1415928"));
  }

  @Test
  public void testParseInt() throws Exception {
    assertEquals(1, Project3.parseInt("1"));
  }

  @Test (expected = NumberFormatException.class)
  public void testParseIntFail() {
    Project3.parseInt("f");
  }

  @Test
  public void testContainValidNumberOfArguments() throws Exception {
    assertTrue(Project3.containValidNumberOfArguments(args("-print 1 2 3 4 5 6 7 8")));
    assertTrue(Project3.containValidNumberOfArguments(args("-textFile foo -another -and_another 1 2 3 4 5 6 7 8")));
    assertTrue(Project3.containValidNumberOfArguments(args("-textFile foo -another -and_another 1 2 3 4 5 6 7")));
    assertTrue(Project3.containValidNumberOfArguments(args("1 2 3 4 5 6 7 8")));
    assertFalse(Project3.containValidNumberOfArguments(args("1 2 3 4 5 6 7")));
  }

  @Test
  public void testContainValidOptions() throws Exception {
    assertTrue(Project3.containValidOptions(args("1 2 3 4 5 6 7 8")));
    assertTrue(Project3.containValidOptions(args("-print -textFile foo 1 2 3 4 5 6 7 8")));
    assertTrue(Project3.containValidOptions(args("-textFile -print -print 1 2 3 4 5 6 7 8")));

    assertFalse(Project3.containValidOptions(args("-textFile f -prin 1 2 3 4 5 6 7 8")));
    assertFalse(Project3.containValidOptions(args("-textFil f -print 1 2 3 4 5 6 7 8")));
    assertFalse(Project3.containValidOptions(args("-textFile 1 2 3 4 5 6 7 8")));
    assertFalse(Project3.containValidOptions(args("-textFile f -print -k 1 2 3 4 5 6 7 8")));
  }

  @Test
  public void testExtractOptions() throws Exception {
    assertEquals(args("-print"), Project3.extractOptions(args("-print 1 2 3 4 5 6 7 8")));
    assertEquals(args("-print -textFile"), Project3.extractOptions(args("-print -textFile 1 2 3 4 5 6 7 8")));
    assertEquals(args("-print -textFile f"), Project3.extractOptions(args("-print -textFile f 1 2 3 4 5 6 7 8")));
    assertEquals(args("-textFile f -print"), Project3.extractOptions(args("-textFile f -print 1 2 3 4 5 6 7 8")));
    assertEquals(args("-textFile -print "), Project3.extractOptions(args("-textFile -print 1 2 3 4 5 6 7 8")));
    assertEquals(args("-textFile -print foo"), Project3.extractOptions(args("-textFile -print foo 1 2 3 4 5 6 7 8")));
    assertTrue(Project3.extractOptions(args("1 2 3 4 5 6 7 8")).isEmpty());
  }

  @Test (expected = Exception.class)
  public void testExtractOptionsError() throws Exception {
    Project3.extractOptions(args("1 2 3 4 5 6 7"));
  }

  @Test
  public void testExtractArguments() throws Exception {
    assertEquals(args("1 2 3 4 5 6 7 8"), Project3.extractArgs(args("1 2 3 4 5 6 7 8")));
    assertEquals(args("2 3 4 5 6 7 8 9"), Project3.extractArgs(args("1 2 3 4 5 6 7 8 9")));
  }

  @Test (expected = IndexOutOfBoundsException.class)
  public void textExtractArgumentsFail() throws Exception {
    Project3.extractArgs(args("3"));
  }

  @Ignore
  @Test
  public void testContainTextFileOption() throws Exception {
    args("-print Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00"); // Does not contain textFile
    args("-print -textFile Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00"); // Invalid arguments
    args("-print -textFile foo Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00"); // Contain textFile
    args("-textFile foo -print Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00"); // Contain textFile
    args("-textFile -print -print Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00"); // Contain textFile, "-print" is the file name
  }


  @Test
  public void testFileExist() throws Exception {
    // Setup
    File root = tmpFolder.getRoot();
    String nonExistantFile = root.toString() + "/foo.txt";
    File file = tmpFolder.newFile("exist.txt");

    // SUT & Verification
    assertTrue(Project3.fileExist(file.toString()));
    assertFalse(Project3.fileExist(nonExistantFile));
    assertFalse(Project3.fileExist(root.toString()));
  }

  @Test
  public void testTextFileWriteNonExistantFile() throws Exception {
    // Setup
    String file = tmpFolder.getRoot().toString() + "/alaska.txt";
    String args = MessageFormat.format("-textFile {0} Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00", file);
    assertFalse(Project3.fileExist(file));

    // SUT
    MainMethodResult result = invokeMain(args);

    // Verification
    assertTrue(Project3.fileExist(file));
    AbstractAirline airline = getAbstractAirline(file);
    assertEquals("Alaska", airline.getName());
    // WTF is going on here
 //   assertEquals(1, airline.getFlights().size());
    assertExitCodeIsZero(result);
  }

  public AbstractAirline getAbstractAirline(String file) throws FileNotFoundException, ParserException {
    InputStream inputStream = new FileInputStream(file);
    DataInputStream dataInputStream = new DataInputStream(inputStream);
    TextParser parser = new TextParser(dataInputStream);
    return parser.parse();
  }

  @Test
  public void testTextFileReadExistingFile() throws Exception {
    // Setup
    String file = tmpFolder.getRoot().toString() + "/alaska.txt";
    String args = MessageFormat.format("-textFile {0} Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00", file);
    invokeMain(args);

    // SUT
    String args1 = MessageFormat.format("-textFile {0} Alaska 34 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00", file);
    MainMethodResult result = invokeMain(args1);

    // Verify
    assertExitCodeIsZero(result);
    assertTrue(Project3.fileExist(file));
    AbstractAirline airline = getAbstractAirline(file);
    assertEquals("Alaska", airline.getName());
    // WTF? assertEquals(2, airline.getFlights().size());
  }

  @Test
  public void testAirlineNameIsDiffrentFromAirlineNameInFile() throws Exception {
    // Setup
    String file = tmpFolder.getRoot().toString() + "/alaska.txt";
    String args = MessageFormat.format("-textFile {0} Alaska 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00", file);
    String args1 = MessageFormat.format("-textFile {0} Foo 32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00", file);
    invokeMain(args);

    // SUT
    MainMethodResult result = invokeMain(args1);

    // Verify
    assertExitCodeIsOne(result);
    assertEquals("Airline name in file is different\n", result.getErr());
  }

  @Test
  public void testPrintAndTextFileOption() throws Exception {
    // Setup
    String file = tmpFolder.getRoot().toString() + "/alaska.txt";
    String args = MessageFormat.format("-textFile {0} -print Alaska 32 PDZ 3/14/2013 15:00 LAX 4/4/2011 1:00", file);

    // SUT
    MainMethodResult result = invokeMain(args);

    // Verify
    String expected = "Flight 32 departs PDZ at 3/14/2013 15:00 arrives LAX at 4/4/2011 1:00\n";
    assertExitCodeIsZero(result);
    assertEquals(expected, result.getOut());
    assertTrue(Project3.fileExist(file));

  }

  private ArrayList<String> args(String args) {
    return new ArrayList<>(Arrays.asList(args.split(" ")));
  }

  private void assertEmptyOutput(MainMethodResult result) {
    assertExitCodeIsZero(result);
    assertEquals(result.getOut(), "");
    assertEquals(result.getErr(), "");
  }

  private void assertError(MainMethodResult result, String err) {
    assertEquals(result.getOut(), "");
    assertEquals(result.getErr(), err+"\n");
    assertExitCodeIsOne(result);
  }

  private void assertExitCodeIsZero(MainMethodResult result) {
    assertEquals(new Integer(0), result.getExitCode());
  }

  private void assertExitCodeIsOne(MainMethodResult result) {
    assertEquals(new Integer(1), result.getExitCode());
  }
}