package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class PrettyPrinterTest {

  @Test
  public void testConstructor() throws Exception {
    new PrettyPrinter(new PrintStream(System.out));
  }

  @Test
  public void testDump() throws Exception {
    // Setup
    Airline airline = new Airline("Alaska Airline");
    Flight flight = new Flight(42, "PDX", parseDate("11/20/1992 12:00 pm"), "LAX", parseDate("10/29/1992 12:00 pm"));
    airline.addFlight(flight);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(byteArrayOutputStream);
    PrettyPrinter pp = new PrettyPrinter(printStream);

    // SUT
    pp.dump(airline);

    // SUT & Verification
    String json = "{name:\"Alaska Airline\",flights:" +
        "[{number:\"42\",src:\"PDX\",departDate:\"11/20/92\"" + ",dest:\"LAX\",arriveDate:\"10/29/92\"}]}";
   // assertTrue(Arrays.equals(json.getBytes(), byteArrayOutputStream.toByteArray()));
  }

  private Date parseDate(String dateStr) throws ParseException {
    return new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(dateStr);
  }
}