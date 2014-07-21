package edu.pdx.cs410J.ladinu;

import org.junit.Ignore;
import org.junit.Test;

import java.text.MessageFormat;

import static org.junit.Assert.*;

public class FlightTest {

  public static final int FLIGHT_NUMBER = 42;
  public static final String SRC = "PDX";
  public static final String DEPART_TIME = "10:39";
  public static final String DEPART_DATE = "3/15/2014";
  public static final String DEPART_DATE_TIME = DEPART_DATE+ " " + DEPART_TIME;
  public static final String DEST = "LAX";
  public static final String ARIVEL_TIME = "10:39";
  public static final String ARIVEL_DATE = "4/15/2014";
  public static final String ARIVEL_DATE_TIME = ARIVEL_DATE + " " + ARIVEL_TIME;

  private Flight getTestFlight() {
    return new Flight(FLIGHT_NUMBER, SRC, DEPART_DATE_TIME, DEST, ARIVEL_DATE_TIME);
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
    assertEquals(DEPART_DATE_TIME, getTestFlight().getDepartureString());
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
    assertEquals(ARIVEL_DATE_TIME, getTestFlight().getArrivalString());
  }

  @Test
  public void testToJSON() throws Exception {
    String json = "{" +
        "number:\"" + FLIGHT_NUMBER + "\",src:\"" + SRC + "\",departDate:\"" + DEPART_DATE +
        "\",departTime:\"" + DEPART_TIME +"\",dest:\"" + DEST + "\",arriveDate:\"" + ARIVEL_DATE +
        "\",arriveTime:\"" + ARIVEL_TIME + "\"}";
    assertEquals(json, getTestFlight().toJSON());
  }
}