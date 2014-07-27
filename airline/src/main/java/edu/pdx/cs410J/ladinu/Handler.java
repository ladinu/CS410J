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
    Project3.checkForValidOptions(argsList);
    HashMap<String, String> optMap = Project3.getOptionMap(argsList);
    if (Project3.hasKey(optMap, "-print", "-textFile", "-pretty")) {
      handlePrintAndTextFileAndPrettyOption(argsList);
    } else if (Project3.hasKey(optMap, "-textFile", "-pretty")) {
      handleTextFileAndPrettyOption(argsList);
    } else if (Project3.hasKey(optMap, "-pretty", "-print")) {
      handlePrettyAndPrintOption(argsList);
    } else if (Project3.hasKey(optMap, "-print", "-textFile")) {
      handlePrintAndTextFileOption(argsList);
    } else if (Project3.hasKey(optMap, "-print")) {
      handlePrintOption(argsList);
    } else if (Project3.hasKey(optMap, "-textFile")) {
      handleTextFileOption(argsList);
    } else if (Project3.hasKey(optMap, "-pretty")) {
      handlePretty(argsList);
    }
  }

  public static void handlePrintAndTextFileAndPrettyOption(ArrayList<String> argsList) {
    Project3.readWriteAirline(argsList);
    Project3.exitIfFileIsStdoutForPretty(argsList);
    Project3.prettyPrintToFile(argsList);
    Project3.printFlight(argsList);
    Project3.exitWithZero();
  }

  public static void handleTextFileAndPrettyOption(ArrayList<String> argsList) {
    Project3.readWriteAirline(argsList);
    Project3.prettyPrintToFile(argsList);
    Project3.exitWithZero();
  }

  public static void handlePrettyAndPrintOption(ArrayList<String> argsList) {
    Project3.exitIfFileIsStdoutForPretty(argsList);
    Project3.addFlightToAirlines(argsList);
    Project3.prettyPrintToFile(argsList);
    Project3.printFlight(argsList);
    Project3.exitWithZero();
  }

  public static void handlePretty(ArrayList<String> argsList) {
    Project3.addFlightToAirlines(argsList);
    Project3.prettyPrintToFile(argsList);
    Project3.exitWithZero();
  }

  public static void handlePrintAndTextFileOption(ArrayList<String> argsList) {
    Project3.readWriteAirline(argsList);
    Project3.printFlight(argsList);
    Project3.exitWithZero();
  }

  public static void handleTextFileOption(ArrayList<String> argsList) {
    Project3.readWriteAirline(argsList);
    Project3.exitWithZero();
  }

  public static void handlePrintOption(ArrayList<String> argsList) {
    Project3.addFlightToAirlines(argsList);
    Project3.printFlight(argsList);
  }
}
