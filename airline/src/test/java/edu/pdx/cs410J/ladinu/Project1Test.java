package edu.pdx.cs410J.ladinu;

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
    assertExitCodeIsOne(result);
    assertTrue(result.getErr().contains( "Missing command line arguments" ));
  }

  @Test
  public void shouldPrintUsageWhenReadmeOptionIsGiven() {
    MainMethodResult result = invokeMain("-README");
    assertTrue(result.getOut().contains(Project1.USAGE));
    assertExitCodeIsZero(result);
  }

  private void assertExitCodeIsZero(MainMethodResult result) {
    assertEquals(new Integer(0), result.getExitCode());
  }

  private void assertExitCodeIsOne(MainMethodResult result) {
    assertEquals(new Integer(1), result.getExitCode());
  }
}