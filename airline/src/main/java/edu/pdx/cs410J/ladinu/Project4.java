package edu.pdx.cs410J.ladinu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project4 {

  public static void main(String[] args) {
  }

  public static Date parseDate(String dateStr) {
    try {
      return new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(dateStr);
    } catch (ParseException e) {
      Errors.printFatalError();
    }
    return null; // Execution should never get here
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


  public static void exitWithZero() {
    System.exit(0);
  }

}