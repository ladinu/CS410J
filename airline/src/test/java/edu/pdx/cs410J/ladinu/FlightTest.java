package edu.pdx.cs410J.ladinu;

import org.junit.Ignore;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class FlightTest {

  public static final int FLIGHT_NUMBER = 42;
  public static final String SRC = "PDX";
  public static final String DEPART_TIME = "10:39 PM";
  public static final String DEPART_DATE = "3/15/2014";
  public static final String DEPART_DATE_TIME = DEPART_DATE+ " " + DEPART_TIME;
  public static final String DEST = "LAX";
  public static final String ARIVEL_TIME = "10:59 PM";
  public static final String ARIVEL_DATE = "4/15/2014";
  public static final String ARIVEL_DATE_TIME = ARIVEL_DATE + " " + ARIVEL_TIME;

  private Flight getTestFlight() throws ParseException {
    Date departDate = new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(DEPART_DATE_TIME);
    Date arriveDate = new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(ARIVEL_DATE_TIME);
    return new Flight(FLIGHT_NUMBER, SRC, departDate, DEST, arriveDate);
  }

  @Test
  public void testGetNumber() throws Exception {
    assertEquals(FLIGHT_NUMBER, getTestFlight().getNumber());
  }

  @Test
  public void testGetSource() throws Exception {
    assertEquals(SRC, getTestFlight().getSource());
  }

  @Ignore
  @Test
  public void testGetDeparture() throws Exception {
  }

  @Test
  public void testGetDepartureString() throws Exception {
    assertEquals(shortDate(DEPART_DATE_TIME), getTestFlight().getDepartureString());
  }

  @Test
  public void testGetDestination() throws Exception {
    assertEquals(DEST, getTestFlight().getDestination());
  }

  @Ignore
  @Test
  public void testGetArrival() throws Exception {
  }

  @Test
  public void testGetArrivalString() throws Exception {
    assertEquals(shortDate(ARIVEL_DATE_TIME), getTestFlight().getArrivalString());
  }

  @Test
  public void testToJSON() throws Exception {
    String json = "{" +
        "number:\"" + FLIGHT_NUMBER + "\",src:\"" + SRC + "\",departDate:\"" + shortDate(DEPART_DATE_TIME) +
        "\",dest:\"" + DEST + "\",arriveDate:\"" + shortDate(ARIVEL_DATE_TIME) + "\"}";
    assertEquals(json, getTestFlight().toJSON());
  }

  @Test
  public void testEquality() throws Exception {
    Flight f1 = new Flight(32, "PDX", parseDate("4/4/1992 10:00 pm"), "LAX", parseDate("4/4/1992 11:00 pm"));
    Flight f2 = new Flight(32, "PDX", parseDate("4/4/1992 10:00 pm"), "ABQ", parseDate("4/4/1992 11:00 pm"));
    Flight f3 = new Flight(32, "ABQ", parseDate("4/4/1992 10:00 pm"), "ABQ", parseDate("4/4/1992 11:00 pm"));

    assertEquals(0, f1.compareTo(f1));
    assertEquals(0, f1.compareTo(f2));
    assertTrue(f1.compareTo(f3) < 0);
  }

  private String shortDate(String dateStr) throws Exception {
    DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.SHORT);
    return dateInstance.format(parseDate(dateStr));
  }

  private Date parseDate(String dateStr) throws ParseException {
    return new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(dateStr);
  }
}