package edu.pdx.cs410J.ladinu;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.StandardErrorStreamLog;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ArgParserTest {

  public static final int ERR_CODE = 1;
  public static final int CLEAN_EXIT = 0;

  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Rule
  public final StandardErrorStreamLog stderr = new StandardErrorStreamLog();

  @Rule
  public final StandardOutputStreamLog stdout = new StandardOutputStreamLog();

  @Test
  public void shouldPrintMissingCommandlineArgs() throws Exception {
    assertError("Missing command line arguments");
    ArgParser.parse(new ArrayList());
  }

  @Test
  public void shouldExitIfArgsAreLessThanMinimum() throws Exception {
    assertError("Invalid number of arguments");
    ArgParser.parse(toList("-host localhost -port"));
  }

  @Test
  public void shouldAlwaysHaveHostAndPortOption() throws Exception {
    assertError("Options -host and -port required");
    ArgParser.parse(toList("-search Alaska PDX LAX"));
  }

  @Test
  public void shouldExitIfArgumentCountIsInvalidForSearchOption() throws Exception {
    assertError("Invalid number of arguments for -search option");
    ArgParser.parse(toList("-host i -port 80 -search Alaska PDX"));

    assertError("Invalid number of arguments for -search option");
    ArgParser.parse(toList("-host i -port 80 -search Alaska PDX LAX foo"));
  }

  @Test
  public void shoudlExitIfInvalidSrcForSearchOption() throws Exception {
    assertError("Invalid src airport code");
    ArgParser.parse(toList("-host i -port 80 -search Alaska foo PDX"));
  }

  @Test
  public void shoudlExitIfInvalidDestForSearchOption() throws Exception {
    assertError("Invalid dest airport code");
    ArgParser.parse(toList("-host i -port 80 -search Alaska PDX FOO"));
  }

  @Ignore
  @Test
  public void caseShoudlNotMatterForDestAndSrcAirportCodesForSearchOption() throws Exception {
    String arguments = "-host i -port 80 -search Alaska pDX lAx";
    List<String> keys  =  toList("host port search name src dest");
    List<String> values = toList("i 80 true Alaska pDX lAx");
    assertEqual(getMap(keys, values), ArgParser.parse(toList(arguments)));
  }

  @Ignore
  @Test
  public void shouldReturnMapWithSearchOption() throws Exception {
    String arguments = "-host i -port 80 -search Alaska PDX LAX";
    List<String> keys  =  toList("host port search name src dest");
    List<String> values = toList("i 80 true Alaska PDX LAX");
    assertEqual(getMap(keys, values), ArgParser.parse(toList(arguments)));
  }




  // Utility Functions

  public void assertError(final String expected) {
    stderr.clear();
    exit.expectSystemExitWithStatus(ERR_CODE);
    exit.checkAssertionAfterwards(new Assertion() {
      @Override
      public void checkAssertion() throws Exception {
        assertEquals(expected + "\n", stderr.getLog());
      }
    });
  }

  public static HashMap<String, String> getMap(List<String> keys, List<String> values) throws Exception{
    if (keys.size() != values.size()) throw new Exception("keys and values have to match");
    final HashMap<String, String> map = new HashMap<>();
    for (int i = 0; i < keys.size(); i++) {
      map.put(keys.get(i), values.get(i));
    }
    return map;
  }

  public static ArrayList<String> toList(String str) {
    return new ArrayList<>(Arrays.asList(str.split(" ")));
  }

  public static void assertEqual(Map<String, String> e, Map<String, String> a) throws Exception {
    assertEquals(e.size(), a.size());
    boolean same = true;
    for (String key : a.keySet()) {
      if (!a.get(key).equals(e.get(key))) {
        same = false;
      }
    }
    assertTrue(same);
  }
}