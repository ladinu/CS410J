package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AirportNames;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class ArgParser {
  public static final String SEARCH_OPTION = "-search";
  public static final int SEARCH_OPTION_ARG_COUNT = 4;
  public static final String HOST_OPTION = "-host";
  public static final String PORT_OPTION = "-port";

  public static Map<String, String> parse(ArrayList<String> args) {
    Map<String, String> map = new HashMap<>();
    exitIfInvalidNumberOfArgs(args);
    exitIfArgsDoNotContainHostOrPortOption(args);
    putHostAndPortInMap(args, map);
    clearHostAndPortFromArgs(args);

    if (hasSearchOption(args)) {
      putSearchOptionsInMap(args, map);
      return map;
    } else {
      return new HashMap<>();
    }
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
    String airlineName = getAirlineNameForSearchOption(args);
    String srcAirport = getSrcAirportForSearchOption(args);
    String destAirport = getDestAirportForSearchOption(args);
  }

  public static String getAirlineNameForSearchOption(ArrayList<String> args) {
    return args.get(1);
  }

  public static String getSrcAirportForSearchOption(ArrayList<String> args) {
    String srcAirport = args.get(2);
    if (!AirportNames.getNamesMap().containsKey(srcAirport)) {
      Errors.printInvalidSrcAirportCodeError();
    }
    return srcAirport;
  }

  public static String getDestAirportForSearchOption(ArrayList<String> args) {
    String destAirport = args.get(3);
    if (!AirportNames.getNamesMap().containsKey(destAirport)) {
      Errors.printInvalidDestAirportCodeError();
    }
    return destAirport;
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

  public static boolean hasSearchOption(ArrayList args) {
    return args.get(0).equals(SEARCH_OPTION);
  }

  public static int getPortIndex(ArrayList args) {
    return getPortOptionIndex(args) + 1;
  }

  public static int getPortOptionIndex(ArrayList<String> args) {
    return args.indexOf(PORT_OPTION);
  }

  public static int getHostIndex(ArrayList args) {
    return getHostOptionIndex(args) + 1;
  }

  public static int getHostOptionIndex(ArrayList<String> args) {
    return args.indexOf(HOST_OPTION);
  }
}
