package edu.pdx.cs410J.ladinu;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;
import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args);
  }
  private MainMethodResult invokeMain(String args) {
    return invokeMain( Project1.class, args.split(" "));
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
    assertTrue(result.getOut().contains(Project1.USAGE));
    assertExitCodeIsZero(result);
  }

  @Test
  public void printOptionShouldNotHaveMoreThanEightArguments() {
    MainMethodResult result = invokeMain("-print 1 2 3 4 5 6 7 8 9");
    assertError(result, "Invalid number of arguments for -print option");
  }

  @Test
  public void printOptionShouldNotHaveLessThanEightArguments() {
    MainMethodResult result = invokeMain("-print 1 2 3 4 5 6 7");
    assertError(result, "Invalid number of arguments for -print option");
  }

  @Test
  public void testInvalidOption() {
    MainMethodResult result = invokeMain("-invalidOption");
    assertError(result, "Invalid option");
  }

  @Test
  public void optionsAreOptional() {
    MainMethodResult result = invokeMain("Alaska A32 PDX 3/15/2014 17:00 LAX 3/15/2014 1:00");
    assertEmptyOutput(result);
  }
  
  @Test
  public void shouldValidateIATACode() {
    assertTrue(Project1.valid_IATA_AirportCode("PDX"));
    assertTrue(Project1.valid_IATA_AirportCode("pdx"));
    assertTrue(Project1.valid_IATA_AirportCode("PdX"));

    assertFalse(Project1.valid_IATA_AirportCode(""));
    assertFalse(Project1.valid_IATA_AirportCode("p"));
    assertFalse(Project1.valid_IATA_AirportCode("pd"));
    assertFalse(Project1.valid_IATA_AirportCode("PDXL"));
    assertFalse(Project1.valid_IATA_AirportCode("PD1"));
    assertFalse(Project1.valid_IATA_AirportCode("LA$"));
  }


  @Test
  public void shouldParseValidDateTime() {
    assertTrue(Project1.isValidDateTime("3/15/2014 10:39"));
    assertTrue(Project1.isValidDateTime("03/2/2014 1:03"));

    assertFalse(Project1.isValidDateTime("03/2/92 1:03"));
    assertFalse(Project1.isValidDateTime("03/2/2014 1PM"));
    assertFalse(Project1.isValidDateTime("03/2/2014 1:30PM"));
    assertFalse(Project1.isValidDateTime("July 19th 2014 1:30PM"));
    assertFalse(Project1.isValidDateTime("03/2/20141:03"));
  }

  @Test
  public void shouldParseValidTime() {
    assertTrue(Project1.isValidTime("00:00"));
    assertTrue(Project1.isValidTime("01:00"));
    assertTrue(Project1.isValidTime("1:00"));
    assertTrue(Project1.isValidTime("24:00"));
    assertTrue(Project1.isValidTime("24:0"));
    assertTrue(Project1.isValidTime("24:59"));

    assertFalse(Project1.isValidTime("1A:AM"));
    assertFalse(Project1.isValidTime("1:3AM"));
    assertFalse(Project1.isValidTime("13AM"));
  }


  @Test
  public void shouldParseValidDate() {
    assertTrue(Project1.isValidDate("3/1/2014"));
    assertTrue(Project1.isValidDate("3/1/2014"));
    assertTrue(Project1.isValidDate("03/15/2014"));
    assertTrue(Project1.isValidDate("03/1/2014"));
    assertTrue(Project1.isValidDate("03/01/2014"));

    assertFalse(Project1.isValidDate("03/01/14"));
    assertFalse(Project1.isValidDate("$/01/14"));
    assertFalse(Project1.isValidDate("January 1st 2012"));

  }

  @Ignore
  @Test
  public void containVlidNumberOfArgumentsPrintMessagesRegardingPrint() {
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