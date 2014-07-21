package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TextParser implements AirlineParser{
  private DataInputStream input;

  public TextParser(DataInputStream input) {
    this.input = input;
  }

  /**
   * Parses some source and returns an airline.
   *
   * @throws edu.pdx.cs410J.ParserException If the source is malformatted.
   */
  @Override
  public AbstractAirline parse() throws ParserException {
    AbstractAirline airline;
    try {
      ObjectInputStream objectInputStream = new ObjectInputStream(input);
      try {
        airline = (AbstractAirline)objectInputStream.readObject();
      } catch (ClassNotFoundException e) {
        throw new ParserException(e.getMessage());
      }
    } catch (IOException e) {
      throw new ParserException(e.getMessage());
    }
    return airline;
  }
}

