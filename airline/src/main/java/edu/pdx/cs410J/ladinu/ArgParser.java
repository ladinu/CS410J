package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AirportNames;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class ArgParser {
  public static final String README_INFO = "" +
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

  public static final String SEARCH_OPTION = "-search";
  public static final int SEARCH_OPTION_ARG_COUNT = 4;
  public static final String HOST_OPTION = "-host";
  public static final String PORT_OPTION = "-port";
  public static final String README_OPTION = "-README";
  public static final String PRINT_OPTION = "-print";
  public static final int NAME_INDEX = 0;
  public static final int NUMBER_INDEX = 1;
  public static final int SRC_INDEX = 2;
  public static final int SRC_INDEX_FOR_SEARCH_OPTION = 2;
  public static final int DEPART_DATE_INDEX = 3;
  public static final int DEPART_TIME_INDEX = 4;
  public static final int DEPART_TIME_AM_PM_INDEX = 5;
  public static final int DEST_INDEX = 6;
  public static final int DEST_INDEX_FOR_SEARCH_OPTION = 3;
  public static final int ARRIVE_DATE_INDEX = 7;
  public static final int ARRIVE_TIME_INDEX = 8;
  public static final int ArriVE_TIME_AM_PM_INDEX = 9;

  public static Map<String, String> parse(ArrayList<String> args) {
    Map<String, String> map = new HashMap<>();
    exitIfReadMeOptionGiven(args);
    exitIfInvalidNumberOfArgs(args);
    exitIfArgsDoNotContainHostOrPortOption(args);
    putHostAndPortInMap(args, map);
    clearHostAndPortFromArgs(args);

    if (hasPrintOption(args)) {
      putPrintOptionsInMap(args, map);
      clearPrintOptionFromArgs(args);
    }

    if (hasSearchOption(args)) {
      putSearchOptionsInMap(args, map);
      clearSearchOptionFromArgs(args);
    } else if (!args.isEmpty()) {
      putArgumentsInMap(args, map);
    }

    return map;
  }

  public static void putHostAndPortInMap(ArrayList<String> args, Map<String, String> map) {
    map.put("host", args.get(getHostIndex(args)));
    map.put("port", args.get(getPortIndex(args)));
  }

  public static void clearHostAndPortFromArgs(ArrayList<String> args) {
    int hostOptionIndex = getHostOptionIndex(args);
    int hostIndex = getHostIndex(args);
    int portOptionIndex = getPortOptionIndex(args);
    int portIndex = getPortIndex(args);
    args.remove(hostOptionIndex);
    args.remove(hostIndex - 1);
    args.remove(portOptionIndex - 2);
    args.remove(portIndex - 3);
  }

  public static void putSearchOptionsInMap(ArrayList<String> args, Map<String, String> map) {
    exitIfInvalidNumberOfArgsForSearchOption(args);
    map.put("search", "true");
    map.put("name", getAirlineNameForSearchOption(args));
    map.put("src", getSrcAirportForSearchOption(args));
    map.put("dest", getDestAirportForSearchOption(args));
  }

  public static void clearSearchOptionFromArgs(ArrayList<String> args) {
    args.remove(args.indexOf(SEARCH_OPTION));
  }

  public static void putArgumentsInMap(ArrayList<String> args, Map<String, String> map) {
    if (hasName(args))
      map.put("name", getAirlineName(args));
    if (hasNumber(args))
      map.put("number", getAirlineNumber(args));
    if (hasSrc(args))
      map.put("src", getSrcAirport(args));
    if (hasDepartDate(args))
      map.put("departDate", getDepartDate(args));
    if (hasDepartTime(args))
      map.put("departTime", getDepartTime(args));
    if (hasDepartTimeAmPm(args))
      map.put("departTimeAmPm", getDepartTimeAmPm(args));
    if (hasDest(args))
      map.put("dest", getDestAirport(args));
    if (hasArriveDate(args))
      map.put("arriveDate", getArriveDate(args));
    if (hasArriveTime(args))
      map.put("arriveTime", getArriveTime(args));
    if (hasArriveTimeAmPm(args))
      map.put("arriveTimeAmPm", getArriveTimeAmPm(args));
  }


  public static void putPrintOptionsInMap(ArrayList<String> args, Map<String, String> map) {
    map.put("print", "true");
  }

  public static void clearPrintOptionFromArgs(ArrayList<String> args) {
    args.remove(args.indexOf(PRINT_OPTION));
  }

  public static void exitIfInvalidNumberOfArgs(ArrayList argsArrayList) {
    exitIfEmptyArgs(argsArrayList);
    exitIfArgsAreLessThanMinimumRequired(argsArrayList);
  }

  public static void exitIfArgsAreLessThanMinimumRequired(ArrayList argsArrayList) {
    if (argsArrayList.size() < 4) {
      Errors.printInvalidNumberOfArgumentsError();
    }
  }

  public static void exitIfEmptyArgs(ArrayList argsArrayList) {
    if (argsArrayList.isEmpty()) {
      Errors.printMissingCommanLineArgumentsError();
    }
  }

  public static void exitIfArgsDoNotContainHostOrPortOption(ArrayList<String> argsArrayList) {
    if (!(argsArrayList.contains(HOST_OPTION) && argsArrayList.contains(PORT_OPTION))) {
      Errors.printHostAndPortOptionRequiredError();
    }
  }

  public static void exitIfInvalidNumberOfArgsForSearchOption(ArrayList args) {
    if (hasSearchOption(args)) {
      if (args.size() != SEARCH_OPTION_ARG_COUNT) {
        Errors.printInvalidNumberOfArgumentsForSearchOptionError();
      }
    }
  }

  public static void exitIfReadMeOptionGiven(ArrayList<String> args) {
    if (args.contains(README_OPTION)) {
      System.out.print(README_INFO);
      exitWithZero();
    }
  }

  public static boolean hasSearchOption(ArrayList args) {
    return args.get(0).equals(SEARCH_OPTION) || args.get(1).equals(SEARCH_OPTION);
  }

  public static boolean hasPrintOption(ArrayList args) {
    return args.get(0).equals(PRINT_OPTION) || args.get(1).equals(PRINT_OPTION);
  }

  public static boolean hasName(ArrayList args) {
    return hasIndex(args, NAME_INDEX);
  }

  public static boolean hasNumber(ArrayList args) {
    return hasIndex(args, NUMBER_INDEX);
  }

  public static boolean hasSrc(ArrayList args) {
    return hasIndex(args, SRC_INDEX);
  }

  public static boolean hasDepartDate(ArrayList args) {
    return hasIndex(args, DEPART_DATE_INDEX);
  }

  public static boolean hasDepartTime(ArrayList args) {
    return hasIndex(args, DEPART_TIME_INDEX);
  }

  public static boolean hasDepartTimeAmPm(ArrayList args) {
    return hasIndex(args, DEPART_TIME_AM_PM_INDEX);
  }

  public static boolean hasDest(ArrayList args) {
    return hasIndex(args, DEST_INDEX);
  }

  public static boolean hasArriveDate(ArrayList args) {
    return hasIndex(args, ARRIVE_DATE_INDEX);
  }

  public static boolean hasArriveTime(ArrayList args) {
    return hasIndex(args, ARRIVE_TIME_INDEX);
  }

  public static boolean hasArriveTimeAmPm(ArrayList args) {
    return hasIndex(args, ArriVE_TIME_AM_PM_INDEX);
  }

  public static boolean hasIndex(ArrayList args, int index) {
    try {
      args.get(index);
      return true;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  public static int getPortIndex(ArrayList<String> args) {
    return getPortOptionIndex(args) + 1;
  }

  public static int getPortOptionIndex(ArrayList<String> args) {
    return args.indexOf(PORT_OPTION);
  }

  public static int getHostIndex(ArrayList<String> args) {
    return getHostOptionIndex(args) + 1;
  }

  public static int getHostOptionIndex(ArrayList<String> args) {
    return args.indexOf(HOST_OPTION);
  }
  public static String getAirlineNameForSearchOption(ArrayList<String> args) {
    return args.get(1);
  }

  public static String getAirlineName(ArrayList<String> args) {
    return args.get(NAME_INDEX);
  }

  public static String getAirlineNumber(ArrayList<String> args) {
    return args.get(NUMBER_INDEX);
  }

  public static String getSrcAirport(ArrayList<String> args) {
    String srcAirport = args.get(SRC_INDEX).toUpperCase();
    if (!AirportNames.getNamesMap().containsKey(srcAirport)) {
      Errors.printInvalidSrcAirportCodeError();
    }
    return srcAirport;
  }

  public static String getSrcAirportForSearchOption(ArrayList<String> args) {
    String srcAirport = args.get(SRC_INDEX_FOR_SEARCH_OPTION).toUpperCase();
    if (!AirportNames.getNamesMap().containsKey(srcAirport)) {
      Errors.printInvalidSrcAirportCodeError();
    }
    return srcAirport;
  }

  public static String getDepartDate(ArrayList<String> args) {
    return args.get(DEPART_DATE_INDEX);
  }

  public static String getDepartTime(ArrayList<String> args) {
    return args.get(DEPART_TIME_INDEX);
  }

  public static String getDepartTimeAmPm(ArrayList<String> args) {
    return args.get(DEPART_TIME_AM_PM_INDEX);
  }

  public static String getDestAirport(ArrayList<String> args) {
    String destAirport = args.get(DEST_INDEX).toUpperCase();
    if (!AirportNames.getNamesMap().containsKey(destAirport)) {
      Errors.printInvalidDestAirportCodeError();
    }
    return destAirport;
  }

  public static String getDestAirportForSearchOption(ArrayList<String> args) {
    String destAirport = args.get(DEST_INDEX_FOR_SEARCH_OPTION).toUpperCase();
    if (!AirportNames.getNamesMap().containsKey(destAirport)) {
      Errors.printInvalidDestAirportCodeError();
    }
    return destAirport;
  }

  public static String getArriveDate(ArrayList<String> args) {
    return args.get(ARRIVE_DATE_INDEX);
  }

  public static String getArriveTime(ArrayList<String> args) {
    return args.get(ARRIVE_TIME_INDEX);
  }

  public static String getArriveTimeAmPm(ArrayList<String> args) {
    return args.get(ArriVE_TIME_AM_PM_INDEX);
  }

  public static void exitWithZero() {
    System.exit(0);
  }
}
