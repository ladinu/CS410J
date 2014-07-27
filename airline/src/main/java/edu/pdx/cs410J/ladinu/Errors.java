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

  public static void exitWithOne() {
    System.exit(1);
  }


  //
  // All of the following methods print some string to STDOUT
  //

  public static void printInvalidArriveDateTime(String arriveTime) {
    System.err.println("Invalid arrive date time '" + arriveTime + "'");
  }

  public static void printInvalidFlightNumber(String flightNumber) {
    System.err.println("Invalid flight number '" + flightNumber + "'");
  }

  public static void printInvalidDepartDateTime(String departTime) {
    System.err.println("Invalid depart date time '" + departTime + "'");
  }

  public static void printPickPrintOrPrettyOption() {
    System.err.println("Cannot print and pretty print at same time. Pick one option.");
  }

  public static void printUnableToPrettyPrint() {
    System.err.println("Unable to pretty print");
  }

  public static void printUnableToOpenFile() {
    System.err.println("Unable to open file!");
  }

  public static void printAirlineNameInFileIsDifferent() {
    System.err.println("Airline name in file is different");
  }

  public static void printCouldNotParseAirline() {
    System.err.println("Could not parse airline from file");
  }

  public static void printCouldNotReadFile(String file) {
    System.err.println("Could not read file '" + file + "'");
  }

  public static void printCouldNotWriteToFile() {
    System.err.println("Could not write Airline info to file!");
  }

  public static void printCannotOpenFile(String textFilePath) {
    System.err.println("Could not open file '" + textFilePath + "' for some reason!");
  }

  public static void printFatal() {
    System.err.println("Fatal Error!");
  }

  public static void printInvalidOption() {
    System.err.println("Invalid option");
  }

  public static void printInvalidNumberOfArguments() {
    System.err.println("Invalid number of arguments");
  }

  public static void printCannotExtractOptions() {
    System.err.println("Command line arguments does not contain valid argument number. Hence, cannot extract options");
  }

  public static void printMissingCommandLineArguments() {
    System.err.println("Missing command line arguments");
  }

  public static void printInvalidAirportCode(String code) {
    System.err.println("Invalid airport code '" + code + "'");
  }

}
