package edu.pdx.cs410J.ladinu.common;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AirportNames;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class FlightValidator {

  public static final String NUMBER_KEY = "number";
  public static final String SRC_KEY = "src";
  public static final String DEPART_DATE_KEY = "departDate";
  public static final String DEPART_TIME_KEY = "departTime";
  public static final String DEPART_TIME_AM_PM_KEY = "departTimeAmPm";
  public static final String DEST_KEY = "dest";
  public static final String ARRIVE_DATE_KEY = "arriveDate";
  public static final String ARRIVE_TIME_KEY = "arriveTime";
  public static final String ARRIVE_TIME_AM_PM_KEY = "arriveTimeAmPm";

  /**
   * Tries to return a Flight object by using the K/V pairs in the map
   * @param map
   * @return Flight
   * @throws FlightValidatorException
   */
  public static Flight getFlight(Map<String, String> map) throws FlightValidatorException {
    return new Flight(getFlightNumber(map), getSrcAirport(map), getDepartDateTime(map),
        getDestAirport(map), getArriveDateTime(map));
  }

  /**
   * Validates and return a flight number
   * @param map
   * @return Integer
   * @throws FlightValidatorException
   */
  public static int getFlightNumber(Map<String, String> map) throws FlightValidatorException {
    String number = map.get(NUMBER_KEY);
    if (number != null) {
      try {
        return Integer.parseInt(number);
      } catch (NumberFormatException e) {
        throw new FlightValidatorException("could not parse flight number");
      }
    } else {
      throw new FlightValidatorException("could not find flight number in params");
    }
  }

  /**
   * Validate and returns a IATA code
   * @param map
   * @return String
   * @throws FlightValidatorException
   */
  public static String getSrcAirport(Map<String, String> map) throws FlightValidatorException {
    String src = map.get(SRC_KEY);
    if (src != null) {
      src = src.toUpperCase();
      if (AirportNames.getNamesMap().get(src) != null) {
        return src;
      } else {
        throw new FlightValidatorException("invalid src airport code");
      }
    } else {
      throw new FlightValidatorException("could not find src airport in params");
    }
  }

  /**
   * Validate and returns a IATA code.
   * @param map
   * @return String
   * @throws FlightValidatorException
   */
  public static String getDestAirport(Map<String, String> map) throws FlightValidatorException {
    String src = map.get(DEST_KEY);
    if (src != null) {
      src = src.toUpperCase();
      if (AirportNames.getNamesMap().get(src) != null) {
        return src;
      } else {
        throw new FlightValidatorException("invalid dest airport code");
      }
    } else {
      throw new FlightValidatorException("could not find dest airport in params");
    }
  }

  /**
   * Validate and returns a Date object corresponding to Depart date time
   * @param map
   * @return Date
   * @throws FlightValidatorException
   */
  public static Date getDepartDateTime(Map<String, String> map) throws FlightValidatorException {
    String departDate = map.get(DEPART_DATE_KEY);
    String departTime  = map.get(DEPART_TIME_KEY);
    String departTimeAmPm  = map.get(DEPART_TIME_AM_PM_KEY);

    if (departDate == null)
      throw new FlightValidatorException("could not find depart date in params");

    if (departTime == null)
      throw new FlightValidatorException("could not find depart time in params");

    if (departTimeAmPm == null)
      throw new FlightValidatorException("could not find depart time Am/PM in params");

    if (isValidDate(departDate)) {
      try {
        String dateStr = departDate + " " + departTime + " " + departTimeAmPm;
        String dateFormat = "MM/dd/yyyy h:mm a";
        DateTimeFormat fmt = DateTimeFormat.getFormat(dateFormat);
        return fmt.parse(dateStr);
      } catch (IllegalArgumentException e){
        throw new FlightValidatorException("could not parse depart date time");
      }
    } else {
      throw new FlightValidatorException("could not parse depart date");
    }
  }

  /**
   * Validate arrive date time
   * @param map
   * @return Date
   * @throws FlightValidatorException
   */
  public static Date getArriveDateTime(Map<String, String> map) throws FlightValidatorException {

    String arriveDate = map.get(ARRIVE_DATE_KEY);
    String arriveTime  = map.get(ARRIVE_TIME_KEY);
    String arriveTimeAmPm  = map.get(ARRIVE_TIME_AM_PM_KEY);

    if (arriveDate == null)
      throw new FlightValidatorException("could not find arrive date in params");

    if (arriveTime == null)
      throw new FlightValidatorException("could not find arrive time in params");

    if (arriveTimeAmPm == null)
      throw new FlightValidatorException("could not find arrive time Am/PM in params");

    if (isValidDate(arriveDate)) {
      try {
        String dateStr = arriveDate + " " + arriveTime + " " + arriveTimeAmPm;
        String dateFormat = "MM/dd/yyyy h:mm a";
        DateTimeFormat fmt = DateTimeFormat.getFormat(dateFormat);
        return fmt.parse(dateStr);
      } catch (IllegalArgumentException e){
        throw new FlightValidatorException("could not parse arrive date time");
      }
    } else {
      throw new FlightValidatorException("could not parse arrive date");
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
}
