package edu.pdx.cs410J.ladinu;


import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project3 {

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
      "  Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
      "\n" +
      "This program extends the functionality of project 2. In this version,\n" +
      "you need to enter time using 12hour format followed by am/pm. For example: \n" +
      " $ java -jar program -print Alaska 31 PDX 2/1/2014 9:00 am LAX 2/1/2014 11:30 am\n" +
      "Also note that you cannot use -print option with -pretty - options togather. Since \n" +
      "they both print to STDOUT the program will complain. ALso, the program does not complain\n" +
      "if you use -textFile and -pretty option for the same file.";

  public static final String[] OPTIONS = new String[]{"-print", "-pretty", "-README", "-textFile"};
  public static final HashMap<String, Airline> AIRLINES = new HashMap<>();
  public static final int ARGUMENT_COUNT = 10;

  public static void main(String[] args) {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
    ifReadmeArgGivenThenPrintUsageAndExitWithZero(argsList);
    ifNoCommandLineArgumentsGivenThenExitWithOne(argsList);
    checkForValidNumberOfArguments(argsList);
    checkForValidOptions(argsList);
    Handler.handleOptions(argsList);
    addFlightToAirlines(argsList);
    exitWithZero();
  }

  /**
   * Checks if arguments are empty or not. If arguments are empty, exit with 1.
   * @param argsList
   */
  public static void ifNoCommandLineArgumentsGivenThenExitWithOne(List<String> argsList) {
    if (argsList.isEmpty()) {
      Errors.printMissingCommanLineArgumentsError();
    }
  }

  /**
   * If any item in the argument list contain -README option, then print usage and exit with
   * code 1
   * @param argsList
   */
  public static void ifReadmeArgGivenThenPrintUsageAndExitWithZero(List<String> argsList) {
    if (argsList.contains("-README")) {
      printUsage();
      exitWithZero();
    }
  }

  public static void checkForValidOptions(ArrayList<String> argsList) {
    if (!containValidOptions(argsList)) {
      Errors.printInvalidOptionError();
    }
  }

  public static boolean containValidOptions(ArrayList<String> argsList) {
    ArrayList<String> options;
    options = extractOptions(argsList);
    // no options are okay
    if (options.isEmpty())
      return true;

    if (options.size() > 5)
      return false;

    if (options.contains("-textFile")) {
      int filePathIndex = options.indexOf("-textFile") + 1;
      if (filePathIndex > options.size() - 1) {
        return false;
      } else {
        options.remove(filePathIndex);
      }
    }

    if (options.contains("-pretty")) {
      int filePathIndex = options.indexOf("-pretty") + 1;
      if (filePathIndex > options.size() - 1) {
        return false;
      } else {
        options.remove(filePathIndex);
      }
    }

    for (String opt : options) {
      if (!Arrays.asList(OPTIONS).contains(opt)) {
        return false;
      }
    }
    return true;
  }

  public static void exitIfFileIsStdoutForPretty(ArrayList<String> argsList) {
    if (isFilePathStdout((argsList))) {
      Errors.printPickPrintOrPrettyOptionError();
    }
  }

  public static void prettyPrintToFile(ArrayList<String> argsList) {
    PrettyPrinter pp = new PrettyPrinter(getPrintStream(argsList));
    String airlineName = extractName(argsList);
    Airline airline = AIRLINES.get(airlineName);
    try {
      pp.dump(airline);
    } catch (IOException e) {
      Errors.printUnableToPrettyPrintError();
    }
  }

  public static PrintStream getPrintStream(ArrayList<String> argsList) {
    PrintStream printStream;
    String filePath = extractPrettyPrinterFilePath(argsList);
    if (isFilePathStdout(argsList)) {
      printStream = System.out;
    } else {
      try {
        FileOutputStream file = new FileOutputStream(filePath);
        printStream = new PrintStream(file);
      } catch (FileNotFoundException e) {
        Errors.printUnableToOpenFileError();
        return null;
      }
    }
    return printStream;
  }

  public static boolean isFilePathStdout(ArrayList<String> argsList) {
    String filePath = extractPrettyPrinterFilePath(argsList);
    return filePath.equals("-");
  }

  public static String extractPrettyPrinterFilePath(ArrayList<String> argsList) {
    HashMap<String, String> optMap = getOptionMap(argsList);
    return optMap.get("-pretty");
  }

  public static boolean hasKey(HashMap<String, String> optMap, String... keys) {
    boolean containAll = true;
    for (String key : keys) {
      if (!optMap.containsKey(key))
        containAll = false;
    }
    return containAll;
  }

  public static void readWriteAirline(ArrayList<String> argsList) {
    String textFilePath = extractTextFilePath(argsList);
    String airlineName = extractName(argsList);

    // If file exist, then read the file and populate AIRLINE map
    if (fileExist(textFilePath)) {
      AbstractAirline abstractAirline = readAirlineFromFile(textFilePath);
      if (!abstractAirline.getName().equals(airlineName)) {
        Errors.printAirlineNameInFileIsDifferentError();
      }
      AIRLINES.put(abstractAirline.getName(), (Airline)abstractAirline);
      addFlightToAirlines(argsList);
      writeAirlineToFile(airlineName, textFilePath);
    // Create a new file and write airline info
    } else {
      addFlightToAirlines(argsList);
      writeAirlineToFile(airlineName, textFilePath);
    }
  }

  public static AbstractAirline readAirlineFromFile(String file) {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      Errors.printCouldNotReadFileError(file);
    }
    DataInputStream dataInputStream = new DataInputStream(inputStream);
    TextParser parser = new TextParser(dataInputStream);
    try {
      return parser.parse();
    } catch (ParserException e) {
      Errors.printCouldNotParseAirlineError();
    }
    return null; // Execution should not get here
  }

  public static void writeAirlineToFile(String airlineName, String textFilePath) {
    Airline airline = AIRLINES.get(airlineName);
    TextDumper textDumper = getTextDumper(textFilePath);
    try {
      textDumper.dump(airline);
    } catch (IOException e) {
      Errors.printCouldNotWriteToFileError();
    }
  }

  public static TextDumper getTextDumper(String textFilePath) {
    try {
      OutputStream outputStream = new FileOutputStream(textFilePath);
      DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
      TextDumper textDumper = new TextDumper(dataOutputStream);
      return textDumper;
    } catch (FileNotFoundException e) {
      Errors.printCannotOpenFileError(textFilePath);
    }
    return null; // Execution should not reach here
  }

  public static String extractTextFilePath(ArrayList<String> argsList) {
    HashMap<String, String> optMap = getOptionMap(argsList);
    return optMap.get("-textFile");
  }

  public static HashMap<String, String> getOptionMap(ArrayList<String> argsList) {
    HashMap<String, String> optionMap = new HashMap<>();
    for(String opt: extractOptions(argsList)) {
      if (Arrays.asList(OPTIONS).contains(opt)) {
        optionMap.put(opt, "");
      }
    }
    if (optionMap.containsKey("-textFile")) {
      optionMap.put("-textFile", argsList.get(argsList.indexOf("-textFile") + 1));
    }
    if (optionMap.containsKey("-pretty")) {
      optionMap.put("-pretty", argsList.get(argsList.indexOf("-pretty") + 1));
    }
    return optionMap;
  }

  /**
   * Retrevies a flight object by using the information given in arguments
   * @param argsList
   * @return
   */
  public static Flight getFlight(ArrayList<String> argsList) {
    ArrayList<String> args = extractArgs(argsList);
    String departTime = extractDepartTime(args);
    String arriveTime = extractArriveTime(args);
    String flightNumber = extractFlightNumber(args);
    String srcAirport = extractSrc(args);
    String destAirport = extractDest(args);

    checkArgsContainValidFlightNumber(flightNumber);
    checkArgsContainValidDepartTime(departTime);
    checkArgsContainValidArriveTime(arriveTime);
    checkArgsContainValidSrcAirportCode(srcAirport);
    checkArgsContainValidDestAirportCode(destAirport);

    return new Flight(parseInt(flightNumber), srcAirport,
          parseDate(departTime), destAirport, parseDate(arriveTime));
  }

  public static Date parseDate(String dateStr) {
    try {
      return new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(dateStr);
    } catch (ParseException e) {
      Errors.printFatalError();
    }
    return null; // Execution should never get here
  }

  /**
   * This function adds a flight to the <code>Airline</code> map by using the
   * arguments specified in <code>argsList</code>
   * @param argsList
   */
  public static void addFlightToAirlines(ArrayList<String> argsList) {
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

  public static ArrayList<String> extractArgs(ArrayList<String> argsList) {
    return new ArrayList<>(argsList.subList(argsList.size() - ARGUMENT_COUNT, argsList.size()));
  }

  public static String extractArriveTime(ArrayList<String> argsList) {
    return argsList.get(7) + " " + argsList.get(8) + " " + argsList.get(9);
  }

  public static String extractDest(ArrayList<String> argsList) {
    return argsList.get(6);
  }

  public static String extractDepartTime(ArrayList<String> argsList) {
    return argsList.get(3) + " " + argsList.get(4) + " " + argsList.get(5);
  }

  public static String extractSrc(ArrayList<String> argsList) {
    return argsList.get(2);
  }

  public static String extractFlightNumber(ArrayList<String> argsList) {
    return argsList.get(1);
  }

  public static String extractName(ArrayList<String> argsList) {
    return extractArgs(argsList).get(0);
  }

  public static void checkArgsContainValidSrcAirportCode(String src) {
    if (!isValid_IATA_AirportCode(src)) {
      Errors.printInvalidAirportCodeError(src);
    }
  }

  public static void checkArgsContainValidDestAirportCode(String dest) {
    if (!isValid_IATA_AirportCode(dest)) {
      Errors.printInvalidAirportCodeError(dest);
    }
  }

  public static void checkArgsContainValidArriveTime(String arriveTime) {
    if (!isValidDateTime(arriveTime)) {
      Errors.printInvalidArriveDateTimeError(arriveTime);
    }
  }

  public static void checkArgsContainValidDepartTime(String departTime) {
    if (!isValidDateTime(departTime)) {
      Errors.printInvalidDepartDateTimeError(departTime);
    }
  }

  public static void checkArgsContainValidFlightNumber(String flightNumber) {
    if (!isValidInt(flightNumber)) {
      Errors.printInvalidFlightNumberError(flightNumber);
    }
  }

  public static boolean fileExist(String filePath) {
    File file = new File(filePath);
    return file.isFile();
  }


  public static void printFlight(ArrayList<String> argsList) {
    System.out.println(getFlight(argsList).toString());
  }


  /**
   * This option check if a string is a 3 letter code.
   * @param iataCode
   * @return True if valid IATA code
   */
  public static boolean isValid_IATA_AirportCode(String iataCode) {
    String code = iataCode.toUpperCase();
    return AirportNames.getNamesMap().containsKey(code);
  }

  public static boolean isValidDateTime(String dateTime) {
    if (dateTime.split(" ").length != 3) {
      return false;
    }
    String date = dateTime.split(" ")[0];
    if (isValidDate(date)) {
      String dateFormat = "MM/dd/yyyy h:mm a";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
      simpleDateFormat.setLenient(false);
      try {
        simpleDateFormat.parse(dateTime);
        return true;
      } catch (ParseException e) {
        return false;
      }
    } else {
      return false;
    }
  }


  /**
   * This function check if a date is any of this format:
   *  1/2/2013
   *  03/4/2013
   *  3/04/2013
   *  03/07/2013
   *
   *  Note: This does not check if the dates contain valid day/month or
   *  take into account of leap years
   * @param date
   * @return True if it matches any of the mentioned formats
   */
  public static boolean isValidDate(String date) {
    return  date.matches("[0-9]/[0-9]/[0-9][0-9][0-9][0-9]") ||
    date.matches("[0-9][0-9]/[0-9]/[0-9][0-9][0-9][0-9]") ||
    date.matches("[0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]") ||
    date.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]");
  }

  public static void checkForValidNumberOfArguments(ArrayList<String> argsList) {
    if (!containValidNumberOfArguments(argsList)) {
      Errors.printInvalidNumberOfArgumentsError();
    }
  }

  public static boolean containValidNumberOfArguments(ArrayList<String> argsList) {
    return argsList.size() >= ARGUMENT_COUNT;
  }

  public static ArrayList<String> extractOptions(ArrayList<String> argsList) {
    if (!containValidNumberOfArguments(argsList)) {
      Errors.printCannotExtractOptionsError();
    }
    return new ArrayList<>(argsList.subList(0, argsList.size() - ARGUMENT_COUNT));
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

  public static void printUsage() {
    System.out.println(USAGE);
  }

  public static void exitWithZero() {
    System.exit(0);
  }

}