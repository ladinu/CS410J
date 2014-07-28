package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

public class PrettyPrinter implements AirlineDumper{

  Writer writer;
  public PrettyPrinter(Writer writer) {
    this.writer = writer;
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
    writer.write(airline1.toJSON());
    writer.flush();
    writer.close();
  }
}
