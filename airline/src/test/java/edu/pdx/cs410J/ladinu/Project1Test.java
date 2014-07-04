package edu.pdx.cs410J.ladinu;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

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
    return invokeMain( Project1.class, args );
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
    MainMethodResult result = invokeMain("-print", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    assertError(result, "Invalid number of arguments for -print option");
  }

  @Test
  public void printOptionShouldNotHaveLessThanEightArguments() {
    MainMethodResult result = invokeMain("-print", "1", "2", "3", "4", "5", "6", "7");
    assertError(result, "Invalid number of arguments for -print option");
  }

  @Ignore
  @Test
  public void makeSurePrintOptionCannotBeAddedAnyWhere() {
  }

  private void assertError(MainMethodResult result, String err) {
    assertTrue(result.getErr().contains(err));
    assertExitCodeIsOne(result);
  }

  private void assertExitCodeIsZero(MainMethodResult result) {
    assertEquals(new Integer(0), result.getExitCode());
  }

  private void assertExitCodeIsOne(MainMethodResult result) {
    assertEquals(new Integer(1), result.getExitCode());
  }
}