package edu.pdx.cs410J.ladinu;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TextDumperTest {

  @Test
  public void testSetOutputStream() throws Exception {
    TextDumper td = new TextDumper();
    td.setOutput(new DataOutputStream(new ByteArrayOutputStream()));
  }

  @Ignore
  @Test
  public void testDump() throws Exception {
    // Setup
    TextDumper td = new TextDumper();
    DataOutputStream output = new DataOutputStream(new ByteArrayOutputStream());
    td.setOutput(output);
    Airline airline = getPopulatedAirline("Alaska");

    // SUT
    td.dump(airline);

    // Verify
    String expected = "{\"name\":\"Alaska\",\"flights\":[{\"number\"" +
        ":42,\"src\":\"LAX\",\"departDate\":\"4/3/2012\",\"departTime\":\"15:00\"" +
        ",\"dest\":\"LAX\",\"arriveDate\":\"4/3/2012\",\"arriveTime\":\"17:00\"}]}";

    assertEquals(expected, output.toString());
  }

  private Airline getPopulatedAirline(String name) {
    Airline airline = new Airline(name);
    Flight flight0 = new Flight(32, "LAX", "4/3/2005 4:00", "PDX", "4/3/2010 13:00");
    Flight flight1 = new Flight(34, "ASX", "4/3/2005 12:00", "PDX", "4/3/2010 13:00");
    Flight flight2 = new Flight(35, "ASX", "4/3/2005 12:00", "PDX", "4/3/2010 13:00");
    airline.addFlight(flight0);
    airline.addFlight(flight1);
    airline.addFlight(flight2);
    return airline;
  }
}