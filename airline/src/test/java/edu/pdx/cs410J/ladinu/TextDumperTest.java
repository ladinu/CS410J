package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TextDumperTest {

  @Test
  public void testSetOutputStream() throws Exception {
    TextDumper td = new TextDumper();
    td.setOutput(new DataOutputStream(new ByteArrayOutputStream()));
  }

  @Test
  public void testDump() throws Exception {
    // Setup
    TextDumper td = new TextDumper();
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(output);
    td.setOutput(out);
    Airline airline = getPopulatedAirline("Alaska");

    // SUT
    td.dump(airline);

    // Verify
    String json = "{name:\"Alaska\",flights:" +
        "[{number:\"42\",src:\"PDX\",departDate:\"02/20/1992\",departTime:\"16:00\"," +
        "dest:\"LAX\",arriveDate:\"10/29/1992\",arriveTime:\"17:00\"}]}";

    ByteArrayOutputStream output2 = new ByteArrayOutputStream();
    DataOutputStream out2 = new DataOutputStream(output2);
    out2.writeUTF(json);

    assertTrue(Arrays.equals(output2.toByteArray(), output.toByteArray()));
  }

  private Airline getPopulatedAirline(String name) {
    Airline airline = new Airline(name);
    Flight flight = new Flight(42, "PDX", "02/20/1992 16:00", "LAX", "10/29/1992 17:00");
    airline.addFlight(flight);
    return airline;
  }
}