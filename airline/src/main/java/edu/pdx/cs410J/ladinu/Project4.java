package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project4 {

  public static final String MISSING_ARGS = "Missing command line arguments";

  public static void main(String... args) {

    Map<String, String> map = ArgParser.parse(new ArrayList<String>(Arrays.asList(args)));
    exitIfSearchAndPrintOptionsAreBothGiven(map);
    handlePrintOption(map);
    handleSearchOption(map);
    handleArguments(map);
  }

  public static void handleArguments(Map<String, String> map) {
    postFlight(map);
    exitWithZero();
  }


  public static void handleSearchOption(Map<String, String> map) {
    if (hasKey(map, ArgParser.SEARCH_OPTION_KEY)) {
      validateSearchArguments(map);
      AirlineRestClient client = new AirlineRestClient();
      HttpRequestHelper.Response response;
      try {
        response = client.getFlights(map);
        if (response.getCode() != HttpURLConnection.HTTP_NOT_FOUND) {
          checkResponseCode( HttpURLConnection.HTTP_OK, response);
        }
        System.out.println(response.getContent());
        exitWithZero();
      } catch ( IOException e ) {
        error("While contacting server: " + e);
      }
    }
  }

  public static void handlePrintOption(Map<String, String> map) {
    if (hasKey(map, ArgParser.PRINT_OPTION_KEY)) {
      HttpRequestHelper.Response res = postFlight(map);
      System.out.println(res.getContent());
      exitWithZero();
    }
  }

  public static HttpRequestHelper.Response postFlight(Map<String, String> map) {
    validateArguments(map);
    AirlineRestClient client = new AirlineRestClient();
    HttpRequestHelper.Response response;
    try {
      response = client.postFlight(map);
      checkResponseCode( HttpURLConnection.HTTP_OK, response);
      return response;
    } catch ( IOException ex ) {
      error("While contacting server: " + ex);
      return null;
    }
  }

  public static void validateArguments(Map<String, String> map) {
    try {
      FlightValidator.getFlight(map);
    } catch (FlightValidatorException e) {
      error(e.getMessage());
    }
  }

  public static void validateSearchArguments(Map<String, String> map) {
    try {
      FlightValidator.getSrcAirport(map);
      FlightValidator.getDestAirport(map);
    } catch (FlightValidatorException e) {
      error(e.getMessage());
    }
  }

  public static void exitIfSearchAndPrintOptionsAreBothGiven(Map<String, String> map) {
    if (hasKey(map, ArgParser.SEARCH_OPTION_KEY) && hasKey(map, ArgParser.PRINT_OPTION_KEY)) {
      Errors.printCannotSearchAndPrintAtTheSameTimeError();
    }
  }

  public static void exitWithZero() {
    System.exit(0);
  }

  /**
   * For a given map, check if given key is in it
   * @param map Some map
   * @param key The key to check
   * @return true if key is in map
   */
  public static boolean hasKey(Map<String, String> map, String key) {
    return map.get(key) != null;
  }

  /**
   * Makes sure that the give response has the expected HTTP status code
   * @param code The expected status code
   * @param response The response from the server
   */
  private static void checkResponseCode( int code, HttpRequestHelper.Response response )
  {
      if (response.getCode() != code) {
          error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                              response.getCode(), response.getContent()));
      }
  }

  public static void error( String message )
  {
      PrintStream err = System.err;
      err.println("** " + message);

      System.exit(1);
  }

  /**
   * Prints usage information for this program and exits
   * @param message An error message to print
   */
  private static void usage( String message )
  {
      PrintStream err = System.err;
      err.println("** " + message);
      err.println();
      err.println("usage: java Project4 host port [key] [value]");
      err.println("  host    Host of web server");
      err.println("  port    Port of web server");
      err.println("  key     Key to query");
      err.println("  value   Value to add to server");
      err.println();
      err.println("This simple program posts key/value pairs to the server");
      err.println("If no value is specified, then all values are printed");
      err.println("If no key is specified, all key/value pairs are printed");
      err.println();

      System.exit(1);
  }
}