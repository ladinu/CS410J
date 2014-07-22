package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintStream;

public class PrettyPrinter implements AirlineDumper{

  PrintStream printStream;

  public PrettyPrinter(PrintStream printStream) {
    this.printStream = printStream;
  }
  /**
   * Dumps an airline to some destination.
   *
   * @param airline The airline being written to a destination
   * @throws java.io.IOException Something went wrong while writing the airline
   */
  @Override
  public void dump(AbstractAirline airline) throws IOException {
    Airline airline1 = (Airline) airline;
    printStream.write(airline1.toJSON().getBytes());
    printStream.flush();
    printStream.close();
  }
}
