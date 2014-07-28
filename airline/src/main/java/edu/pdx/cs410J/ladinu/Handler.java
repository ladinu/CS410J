package edu.pdx.cs410J.ladinu;

import java.util.ArrayList;
import java.util.HashMap;

public class Handler {
  /**
   * This function is responsible for delegating control to other functions based on the
   * arguments
   * @param argsList
   */
  public static void handleOptions(ArrayList<String> argsList) {
    Project4.checkForValidOptions(argsList);
    HashMap<String, String> optMap = Project4.getOptionMap(argsList);
    if (Project4.hasKey(optMap, "-print", "-textFile", "-pretty")) {
      handlePrintAndTextFileAndPrettyOption(argsList);
    } else if (Project4.hasKey(optMap, "-textFile", "-pretty")) {
      handleTextFileAndPrettyOption(argsList);
    } else if (Project4.hasKey(optMap, "-pretty", "-print")) {
      handlePrettyAndPrintOption(argsList);
    } else if (Project4.hasKey(optMap, "-print", "-textFile")) {
      handlePrintAndTextFileOption(argsList);
    } else if (Project4.hasKey(optMap, "-print")) {
      handlePrintOption(argsList);
    } else if (Project4.hasKey(optMap, "-textFile")) {
      handleTextFileOption(argsList);
    } else if (Project4.hasKey(optMap, "-pretty")) {
      handlePretty(argsList);
    }
  }

  public static void handlePrintAndTextFileAndPrettyOption(ArrayList<String> argsList) {
    Project4.readWriteAirline(argsList);
    Project4.exitIfFileIsStdoutForPretty(argsList);
    Project4.prettyPrintToFile(argsList);
    Project4.printFlight(argsList);
    Project4.exitWithZero();
  }

  public static void handleTextFileAndPrettyOption(ArrayList<String> argsList) {
    Project4.readWriteAirline(argsList);
    Project4.prettyPrintToFile(argsList);
    Project4.exitWithZero();
  }

  public static void handlePrettyAndPrintOption(ArrayList<String> argsList) {
    Project4.exitIfFileIsStdoutForPretty(argsList);
    Project4.addFlightToAirlines(argsList);
    Project4.prettyPrintToFile(argsList);
    Project4.printFlight(argsList);
    Project4.exitWithZero();
  }

  public static void handlePretty(ArrayList<String> argsList) {
    Project4.addFlightToAirlines(argsList);
    Project4.prettyPrintToFile(argsList);
    Project4.exitWithZero();
  }

  public static void handlePrintAndTextFileOption(ArrayList<String> argsList) {
    Project4.readWriteAirline(argsList);
    Project4.printFlight(argsList);
    Project4.exitWithZero();
  }

  public static void handleTextFileOption(ArrayList<String> argsList) {
    Project4.readWriteAirline(argsList);
    Project4.exitWithZero();
  }

  public static void handlePrintOption(ArrayList<String> argsList) {
    Project4.addFlightToAirlines(argsList);
    Project4.printFlight(argsList);
  }
}
