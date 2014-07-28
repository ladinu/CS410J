package edu.pdx.cs410J.ladinu;

public class Errors {

  public static void printPickPrintOrPrettyOptionError() {
    printPickPrintOrPrettyOption();
    exitWithOne();
  }

  public static void printUnableToPrettyPrintError() {
    printUnableToPrettyPrint();
    exitWithOne();
  }

  public static void printUnableToOpenFileError() {
    printUnableToOpenFile();
    exitWithOne();
  }

  public static void printCannotOpenFileError(String textFilePath) {
    printCannotOpenFile(textFilePath);
    exitWithOne();
  }

  public static void printFatalError() {
    printFatal();
    exitWithOne();
  }

  public static void printCannotExtractOptionsError() {
    printCannotExtractOptions();
    exitWithOne();
  }

  public static void printMissingCommanLineArgumentsError() {
    printMissingCommandLineArguments();
    exitWithOne();
  }

  public static void printInvalidOptionError() {
    printInvalidOption();
    exitWithOne();
  }

  public static void printAirlineNameInFileIsDifferentError() {
    printAirlineNameInFileIsDifferent();
    exitWithOne();
  }

  public static void printInvalidNumberOfArgumentsError() {
    printInvalidNumberOfArguments();
    exitWithOne();
  }

  public static void printCouldNotParseAirlineError() {
    printCouldNotParseAirline();
    exitWithOne();
  }

  public static void printCouldNotReadFileError(String file) {
    printCouldNotReadFile(file);
    exitWithOne();
  }

  public static void printCouldNotWriteToFileError() {
    printCouldNotWriteToFile();
    exitWithOne();
  }

  public static void printInvalidAirportCodeError(String code) {
    printInvalidAirportCode(code);
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

  private static void printPickPrintOrPrettyOption() {
    System.err.println("Cannot print and pretty print at same time. Pick one option.");
  }

  private static void printUnableToPrettyPrint() {
    System.err.println("Unable to pretty print");
  }

  private static void printUnableToOpenFile() {
    System.err.println("Unable to open file!");
  }

  private static void printAirlineNameInFileIsDifferent() {
    System.err.println("Airline name in file is different");
  }

  private static void printCouldNotParseAirline() {
    System.err.println("Could not parse airline from file");
  }

  private static void printCouldNotReadFile(String file) {
    System.err.println("Could not read file '" + file + "'");
  }

  private static void printCouldNotWriteToFile() {
    System.err.println("Could not write Airline info to file!");
  }

  private static void printCannotOpenFile(String textFilePath) {
    System.err.println("Could not open file '" + textFilePath + "' for some reason!");
  }

  private static void printFatal() {
    System.err.println("Fatal Error!");
  }

  private static void printInvalidOption() {
    System.err.println("Invalid option");
  }

  private static void printInvalidNumberOfArguments() {
    System.err.println("Invalid number of arguments");
  }

  private static void printCannotExtractOptions() {
    System.err.println("Command line arguments does not contain valid argument number. Hence, cannot extract options");
  }

  private static void printMissingCommandLineArguments() {
    System.err.println("Missing command line arguments");
  }

  private static void printInvalidAirportCode(String code) {
    System.err.println("Invalid airport code '" + code + "'");
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
