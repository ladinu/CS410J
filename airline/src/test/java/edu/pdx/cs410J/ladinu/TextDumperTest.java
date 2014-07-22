package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

  private Airline getPopulatedAirline(String name) throws Exception {
    Airline airline = new Airline(name);
    Flight flight = new Flight(42, "PDX", parseDate("02/20/1992 15:00 pm"), "LAX", parseDate("10/29/1992 12:00 pm"));
    airline.addFlight(flight);
    return airline;
  }

  private Date parseDate(String dateStr) throws ParseException {
    return new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(dateStr);
  }
}