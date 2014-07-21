package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TextParserTest {

  @Test
  public void testConstruct() throws Exception {
    TextParser parser = new TextParser(getIn("foo"));
  }

  @Test
  public void testParse() throws Exception {
    // Setup
    Airline airline = getPopulatedAirline("Alaska");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    TextDumper dumper = new TextDumper(dataOutputStream);
    dumper.dump(airline);
    ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    DataInputStream dataInputStream = new DataInputStream(bais);
    TextParser parser = new TextParser(dataInputStream);

    // SUT
    AbstractAirline abstractAirline = parser.parse();

    // Verify
    assertEquals(airline.getName(), abstractAirline.getName());
    assertEquals(airline.getFlights().size(), abstractAirline.getFlights().size());
  }


  private DataInputStream getIn(String str) {
    return new DataInputStream(new ByteArrayInputStream(str.getBytes()));
  }

  private Airline getPopulatedAirline(String name) {
    Airline airline = new Airline(name);
    Flight flight = new Flight(42, "PDX", "02/20/1992 16:00", "LAX", "10/29/1992 17:00");
    airline.addFlight(flight);
    return airline;
  }
}