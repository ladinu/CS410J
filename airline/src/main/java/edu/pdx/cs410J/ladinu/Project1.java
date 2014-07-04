package edu.pdx.cs410J.ladinu;

import java.util.Arrays;
import java.util.List;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static final String USAGE = "" +
      "usage: java edu.pdx.cs410J.ladinu.Project1 [options] <args>\n" +
      "  args are (in this order):\n" +
      "    name                  The name of the airline\n" +
      "    flightNumber          The flight number\n" +
      "    src                   Three-letter code of departure airport\n" +
      "    departTime            Departure date and time (24-hour time)\n" +
      "    dest                  Three-letter code of arrival airport\n" +
      "    arriveTime            Arrival date and time (24-hour time)\n" +
      "  options are (options may appear in any order):\n" +
      "    -print                Prints a description of the new flight\n" +
      "    -README               Prints a README for this project and exits\n" +
      "  Date and time should be in the format: mm/dd/yyyy hh:mm";

  public static void main(String[] args) {
    List<String> commandLineArgs = Arrays.asList(args);
    IfReadmeArgGivenThenPrintUsageAndExitWithZero(commandLineArgs);
    ifNoCommandLineArgumentsGivenThenExitWithOne(commandLineArgs);
  }

  private static void ifNoCommandLineArgumentsGivenThenExitWithOne(List<String> commandLineArgs) {
    System.err.println("Missing command line arguments");
    for (String arg : commandLineArgs) {
      System.out.println(arg);
    }
    exitWithOne();
  }

  private static void IfReadmeArgGivenThenPrintUsageAndExitWithZero(List<String> commandLineArgs) {
    if (commandLineArgs.contains("-README")) {
      printUsage();
      exitWithZero();
    }
  }

  private static void printUsage() {
    System.out.println(USAGE);
  }

  private static void exitWithZero() {
    System.exit(0);
  }

  private static void exitWithOne() {
    System.exit(1);
  }

}