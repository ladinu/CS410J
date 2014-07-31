package edu.pdx.cs410J.ladinu;

/**
 * Every method in this class prints some sort of error message
 * to STDERR and exits with code 1
 */
public class Errors {

  public static void printMissingCommanLineArgumentsError() {
    printMissingCommandLineArguments();
    exitWithOne();
  }

  public static void printInvalidNumberOfArgumentsError() {
    printInvalidNumberOfArguments();
    exitWithOne();
  }

  public static void printInvalidNumberOfArgumentsForSearchOptionError() {
    printInvalidNumberOfArgumentsForSearchOption();
    exitWithOne();
  }

  public static void printHostAndPortOptionRequiredError() {
    printHostAndPortOptionRequired();
    exitWithOne();
  }

  public static void printCannotSearchAndPrintAtTheSameTimeError() {
    printCannotSearchAndPrintAtTheSameTime();
    exitWithOne();
  }

  public static void exitWithOne() {
    System.exit(1);
  }


  //
  // All of the following methods print some string to STDOUT
  //

  private static void printInvalidNumberOfArguments() {
    System.err.println("Invalid number of arguments");
  }

  private static void printMissingCommandLineArguments() {
    System.err.println("Missing command line arguments");
  }

  private static void printInvalidNumberOfArgumentsForSearchOption() {
    System.err.println("Invalid number of arguments for -search option");
  }

  private static void printHostAndPortOptionRequired() {
    System.err.println("Options -host and -port required");
  }

  private static void printCannotSearchAndPrintAtTheSameTime() {
    System.err.println("cannot search and print at the same time");
  }
}
