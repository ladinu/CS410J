package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class TextParserTest {

  @Test
  public void testConstruct() throws Exception {
    TextParser parser = new TextParser(getIn("foo"));
  }

  @Test
  public void testParse() throws Exception {
    // Setup
    Airline airline = new Airline("Alaska Airline");
    Flight flight = new Flight(42, "PDX", "02/20/1992 16:00", "LAX", "10/29/1992 17:00");
    airline.addFlight(flight);
    String airlineJSON = "{name:\"Alaska Airline\",flights:[{number:\"42\"," +
        "src:\"PDX\",departDate:\"02/20/1992\",departTime:\"16:00\"," +
        "dest:\"LAX\",arriveDate:\"10/29/1992\",arriveTime:\"17:00\"}]}";
    TextParser parser = new TextParser(getIn(airlineJSON, "UTF-8"));

    // SUT
    AbstractAirline abstractAirline = parser.parse();

    // Verify
    assertEquals(airline, abstractAirline);
    assertEquals(airline.getFlights().size(), 1);
    for ( Object o : airline.getFlights()) {
      Flight f = (Flight) o;
      assertEquals(f, flight);
    }
  }

  private DataInputStream getIn(String str, String charSet) throws UnsupportedEncodingException {
    return new DataInputStream(new ByteArrayInputStream(str.getBytes(charSet)));
  }

  private DataInputStream getIn(String str) {
    return new DataInputStream(new ByteArrayInputStream(str.getBytes()));
  }
}