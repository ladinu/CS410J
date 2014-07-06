package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import static org.junit.Assert.*;

public class AirlineTest {

  @Test
  public void testGetName() throws Exception {
    Airline airline = new Airline("Alaska");
    assertEquals("Alaska", airline.getName());
  }

  @Test
  public void testSetName() throws Exception {
    Airline airline = new Airline();
    assertNull(airline.getName());
    airline.setName("Alaska");
    assertEquals("Alaska", airline.getName());
  }

  @Test
  public void testAddFlight() throws Exception {
    Flight flight = new Flight();
    Airline airline = new Airline();
    assertTrue(airline.getFlights().isEmpty());
    airline.addFlight(flight);
    assertEquals(1, airline.getFlights().size());
  }

  @Test
  public void testGetFlights() throws Exception {
    Flight flight = new Flight();
    Airline airline = new Airline();
    airline.addFlight(flight);
    for (Object f : airline.getFlights()) {
      assertEquals(flight, f);
    }
  }

  @Test
  public void testToString() throws Exception {
    Airline airline = new Airline("Alaska Airline");
    assertEquals("Alaska Airline with 0 flights", airline.toString());
    airline.addFlight(new Flight());
    airline.addFlight(new Flight());
    assertEquals("Alaska Airline with 2 flights", airline.toString());
  }
}