package edu.pdx.cs410J.ladinu;

import java.util.ArrayList;
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

  public static final String[] OPTIONS = new String[]{"-print", "-README"};

  public static void main(String[] args) {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
    ifReadmeArgGivenThenPrintUsageAndExitWithZero(argsList);
    ifNoCommandLineArgumentsGivenThenExitWithOne(argsList);
    handleOptions(argsList);
    handleArguments(argsList);
  }

  private static void handleOptions(ArrayList<String> argsList) {
    if (isOption(argsList)) {
      handleInvalidOption(argsList);
      handlePrintOption(argsList);
    }
  }

  private static void handleArguments(ArrayList<String> argsList) {
    containValidNumberOfArguments(argsList);
    exitWithZero();
  }

  public static boolean validate_IATA_AirportCode(String iataCode) {
    return iataCode.toUpperCase().matches("[A-Z][A-Z][A-Z]");
  }

  private static void handleInvalidOption(ArrayList<String> argsList) {
    if (!containValidOption(argsList)) {
      printInvalidOptionMessage();
      exitWithOne();
    }
  }

  private static boolean containValidOption(ArrayList<String> argsList) {
    String option = argsList.get(0);
    boolean validOption = false;
    for ( String opt : OPTIONS) {
      if (option.equals(opt)) {
        validOption = true;
      }
    }
    return validOption;
  }

  private static boolean isOption(ArrayList<String> arrayList) {
    return arrayList.get(0).startsWith("-");
  }

  private static void printInvalidOptionMessage() {
    System.err.println("Invalid option");
  }

  private static void handlePrintOption(ArrayList<String> argsList) {
    if (argsList.get(0).equals("-print")) {
      argsList.remove(0);
      handleArguments(argsList);
    }
  }

  private static void containValidNumberOfArguments(ArrayList<String> argsList) {
    if (argsList.size() != 8) {
      printInvalidNumberOfArgumentsForPrintOptionMessage();
      exitWithOne();
    }
  }

  private static void printInvalidNumberOfArgumentsForPrintOptionMessage() {
    System.err.println("Invalid number of arguments for -print option");
  }

  private static void ifNoCommandLineArgumentsGivenThenExitWithOne(List<String> argsList) {
    if (argsList.isEmpty()) {
      printMissingCommanLineArgumentsMessage();
      exitWithOne();
    }
  }

  private static void ifReadmeArgGivenThenPrintUsageAndExitWithZero(List<String> argsList) {
    if (argsList.contains("-README")) {
      printUsage();
      exitWithZero();
    }
  }

  private static void printMissingCommanLineArgumentsMessage() {
    System.err.println("Missing command line arguments");
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