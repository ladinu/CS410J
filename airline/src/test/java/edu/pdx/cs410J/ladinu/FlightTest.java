package edu.pdx.cs410J.ladinu;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlightTest {

  public static final int FLIGHT_NUMBER = 42;
  public static final String SRC = "PDX";
  public static final String DEPART_TIME = "3/15/2014 10:39";
  public static final String DEST = "LAX";
  public static final String ARIVEL_TIME = "4/15/2014 10:39";

  private Flight getTestFlight() {
    return new Flight(FLIGHT_NUMBER, SRC, DEPART_TIME, DEST, ARIVEL_TIME);
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
    assertEquals(DEPART_TIME, getTestFlight().getDepartureString());
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
    assertEquals(ARIVEL_TIME, getTestFlight().getArrivalString());
  }
}