package edu.pdx.cs410J.ladinu;

import java.util.*;

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
  public static final HashMap<String, Airline> AIRLINES = new HashMap<>();

  public static void main(String[] args) {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
    ifReadmeArgGivenThenPrintUsageAndExitWithZero(argsList);
    ifNoCommandLineArgumentsGivenThenExitWithOne(argsList);
    handleOptions(argsList);
    handleArguments(argsList);
    exitWithZero();
  }

  /**
   * Checks if arguments are empty or not. If arguments are empty, exit with 1.
   * @param argsList
   */
  private static void ifNoCommandLineArgumentsGivenThenExitWithOne(List<String> argsList) {
    if (argsList.isEmpty()) {
      printMissingCommanLineArgumentsMessage();
      exitWithOne();
    }
  }

  /**
   * If any item in the argument list contain -README option, then print usage and exit with
   * code 1
   * @param argsList
   */
  private static void ifReadmeArgGivenThenPrintUsageAndExitWithZero(List<String> argsList) {
    if (argsList.contains("-README")) {
      printUsage();
      exitWithZero();
    }
  }

  /**
   * This function is responsible for delegating control to other functions based on the
   * arguments
   * @param argsList
   */
  private static void handleOptions(ArrayList<String> argsList) {
    if (isOption(argsList)) {
      handleInvalidOption(argsList);
      if (argsList.get(0).equals("-print")) {
        handlePrintOption(argsList);
      }
    }
  }

  /**
   * Checks if first item in arguments start with a dash. If so, then it is considered
   * a valid argument.
   * @param arrayList
   * @return
   */
  private static boolean isOption(ArrayList<String> arrayList) {
    return arrayList.get(0).startsWith("-");
  }

  private static void handleArguments(ArrayList<String> argsList) {
    containValidNumberOfArguments(argsList);
    addFlightToAirlines(argsList);
  }

  private static Flight getFlight(ArrayList<String> argsList) {
    String departTime = extractDepartTime(argsList);
    String arriveTime = extractArriveTime(argsList);
    String flightNumber = extractFlightNumber(argsList);
    String srcAirport = extractSrc(argsList);
    String destAirport = extractDest(argsList);

    checkArgsContainValidFlightNumber(flightNumber);
    checkArgsContainValidDepartTime(departTime);
    checkArgsContainValidArriveTime(arriveTime);
    checkArgsContainValidSrcAirportCode(srcAirport);
    checkArgsContainValidDestAirportCode(destAirport);

    return new Flight(parseInt(flightNumber), srcAirport,
          departTime, destAirport, arriveTime);
  }

  private static void addFlightToAirlines(ArrayList<String> argsList) {
    String airlineName = extractName(argsList);
    Flight flight = getFlight(argsList);

    if (AIRLINES.containsKey(airlineName)) {
      AIRLINES.get(airlineName).addFlight(flight);
    } else {
      Airline airline = new Airline(airlineName);
      airline.addFlight(flight);
      AIRLINES.put(airlineName, airline);
    }
  }

  private static String extractArriveTime(ArrayList<String> argsList) {
    return argsList.get(6) + " " + argsList.get(7);
  }

  private static String extractDest(ArrayList<String> argsList) {
    return argsList.get(5);
  }

  private static String extractDepartTime(ArrayList<String> argsList) {
    return argsList.get(3) + " " + argsList.get(4);
  }

  private static String extractSrc(ArrayList<String> argsList) {
    return argsList.get(2);
  }

  private static String extractFlightNumber(ArrayList<String> argsList) {
    return argsList.get(1);
  }

  private static String extractName(ArrayList<String> argsList) {
    return argsList.get(0);
  }

  private static void checkArgsContainValidSrcAirportCode(String src) {
    if (!isValid_IATA_AirportCode(src)) {
      System.err.println("Invalid src '" + src + "'");
      exitWithOne();
    }
  }

  private static void checkArgsContainValidDestAirportCode(String dest) {
    if (!isValid_IATA_AirportCode(dest)) {
      System.err.println("Invalid src '" + dest + "'");
      exitWithOne();
    }
  }

  private static void checkArgsContainValidArriveTime(String arriveTime) {
    if (!isValidDateTime(arriveTime)) {
      System.err.println("Invalid arrive date time '" + arriveTime + "'");
      exitWithOne();
    }
  }

  private static void checkArgsContainValidDepartTime(String departTime) {
    if (!isValidDateTime(departTime)) {
      System.err.println("Invalid depart date time '" + departTime + "'");
      exitWithOne();
    }
  }

  private static void checkArgsContainValidFlightNumber(String flightNumber) {
    if (!isValidInt(flightNumber)) {
      System.err.println("Invalid flight number '" + flightNumber + "'");
      exitWithOne();
    }
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

  private static void handlePrintOption(ArrayList<String> argsList) {
    argsList.remove(0);
    handleArguments(argsList);
    printFlight(argsList);
  }

  private static void printFlight(ArrayList<String> argsList) {
    System.out.println(getFlight(argsList).toString());
  }


  public static boolean isValid_IATA_AirportCode(String iataCode) {
    return iataCode.toUpperCase().matches("[A-Z][A-Z][A-Z]");
  }

  public static boolean isValidDateTime(String dateTime) {
    if (dateTime.split(" ").length != 2) {
      return false;
    }
    String date = dateTime.split(" ")[0];
    String time = dateTime.split(" ")[1];
    return isValidDate(date) && isValidTime(time);
  }

  public static boolean isValidDate(String date) {
    return  date.matches("[0-9]/[0-9]/[0-9][0-9][0-9][0-9]") ||
    date.matches("[0-9][0-9]/[0-9]/[0-9][0-9][0-9][0-9]") ||
    date.matches("[0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]") ||
    date.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]");
  }

  public static boolean isValidTime(String time) {
    if (time.split(":").length != 2) {
      return false;
    }

    String minutePart = time.split(":")[1];
    String hourPart = time.split(":")[0];

    int minute;
    int hour;
    try {
       minute = Integer.parseInt(minutePart);
       hour = Integer.parseInt(hourPart);
    } catch (NumberFormatException e) {
      return false;
    }

    boolean validMinute = minute > -1 && minute < 60;
    boolean validHour = hour > -1 && hour < 25;

    return validHour && validMinute;
  }

  private static void printInvalidOptionMessage() {
    System.err.println("Invalid option");
  }

  private static void containValidNumberOfArguments(ArrayList<String> argsList) {
    if (argsList.size() != 8) {
      printInvalidNumberOfArgumentsForPrintOptionMessage();
      exitWithOne();
    }
  }

  public static boolean isValidInt(String integer) {
    try {
      parseInt(integer);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static int parseInt(String integer) throws NumberFormatException {
      return Integer.parseInt(integer);
  }

  private static void printInvalidNumberOfArgumentsForPrintOptionMessage() {
    System.err.println("Invalid number of arguments for -print option");
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