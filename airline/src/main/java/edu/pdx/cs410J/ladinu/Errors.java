package edu.pdx.cs410J.ladinu;

public class Errors {

  public static void printFatalError() {
    printFatal();
    exitWithOne();
  }

  public static void printMissingCommanLineArgumentsError() {
    printMissingCommandLineArguments();
    exitWithOne();
  }

  public static void printInvalidNumberOfArgumentsError() {
    printInvalidNumberOfArguments();
    exitWithOne();
  }

  public static void printInvalidArriveDateTimeError(String arriveTime) {
    printInvalidArriveDateTime(arriveTime);
    exitWithOne();
  }


  public static void printInvalidDepartDateTimeError(String departTime) {
    printInvalidDepartDateTime(departTime);
    exitWithOne();
  }

  public static void printInvalidFlightNumberError(String flightNumber) {
    printInvalidFlightNumber(flightNumber);
    exitWithOne();
  }

  public static void printInvalidNumberOfArgumentsForSearchOptionError() {
    printInvalidNumberOfArgumentsForSearchOption();
    exitWithOne();
  }

  public static void printInvalidSrcAirportCodeError() {
    printInvalidSrcAirportCode();
    exitWithOne();
  }

  public static void printInvalidDestAirportCodeError() {
    printInvalidDestAirportCode();
    exitWithOne();
  }

  public static void printHostAndPortOptionRequiredError() {
    printHostAndPortOptionRequired();
    exitWithOne();
  }

  public static void exitWithOne() {
    System.exit(1);
  }


  //
  // All of the following methods print some string to STDOUT
  //

  private static void printInvalidArriveDateTime(String arriveTime) {
    System.err.println("Invalid arrive date time '" + arriveTime + "'");
  }

  private static void printInvalidFlightNumber(String flightNumber) {
    System.err.println("Invalid flight number '" + flightNumber + "'");
  }

  private static void printInvalidDepartDateTime(String departTime) {
    System.err.println("Invalid depart date time '" + departTime + "'");
  }

  private static void printFatal() {
    System.err.println("Fatal Error!");
  }

  private static void printInvalidNumberOfArguments() {
    System.err.println("Invalid number of arguments");
  }

  private static void printMissingCommandLineArguments() {
    System.err.println("Missing command line arguments");
  }

  private static void printInvalidNumberOfArgumentsForSearchOption() {
    System.err.println("Invalid number of arguments for -search option");
  }

  private static void printInvalidSrcAirportCode() {
    System.err.println("Invalid src airport code");
  }

  private static void printInvalidDestAirportCode() {
    System.err.println("Invalid dest airport code");
  }

  private static void printHostAndPortOptionRequired() {
    System.err.println("Options -host and -port required");
  }
}
