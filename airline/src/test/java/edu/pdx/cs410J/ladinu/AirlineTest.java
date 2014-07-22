package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

  @Test
  public void testToJSON() throws Exception {
    // Setup
    Airline airline = new Airline("Alaska Airline");
    Flight flight = new Flight(42, "PDX", parseDate("11/20/1992 12:00 pm"), "LAX", parseDate("10/29/1992 12:00 pm"));
    airline.addFlight(flight);

    // SUT & Verification
    String json = "{name:\"Alaska Airline\",flights:" +
        "[{number:\"42\",src:\"PDX\",departDate:\"11/20/92\"" + ",dest:\"LAX\",arriveDate:\"10/29/92\"}]}";
    assertEquals(json, airline.toJSON());
  }
  @Test
  public void testToJSONEmpty() throws Exception {
    Airline airline = new Airline("Alaska Airline");
    assertEquals("{name:\"Alaska Airline\",flights:[]}", airline.toJSON());
  }

  private Date parseDate(String dateStr) throws ParseException {
    return new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(dateStr);
  }
}