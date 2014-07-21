package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TextDumperTest {

  @Test
  public void testConstructor() throws Exception {
    TextDumper td = new TextDumper(new DataOutputStream(new ByteArrayOutputStream()));
  }

  @Test
  public void testDump() throws Exception {
    // Setup
    Airline airline = getPopulatedAirline("Alaska");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    TextDumper dumper = new TextDumper(dataOutputStream);

    // SUT
    dumper.dump(airline);

    // Verify
    ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    DataInputStream dataInputStream = new DataInputStream(bais);
    ObjectInputStream objectInputStream = new ObjectInputStream(dataInputStream);

    Airline actualAirline = (Airline) objectInputStream.readObject();
    assertEquals(airline.getName(), actualAirline.getName());
    assertEquals(airline.getFlights().size(), actualAirline.getFlights().size());
  }

  private Airline getPopulatedAirline(String name) {
    Airline airline = new Airline(name);
    Flight flight = new Flight(42, "PDX", "02/20/1992 16:00", "LAX", "10/29/1992 17:00");
    airline.addFlight(flight);
    return airline;
  }
}