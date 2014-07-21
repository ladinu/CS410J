package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TextDumper implements AirlineDumper {

  private DataOutputStream output;

  public TextDumper(DataOutputStream output) {
    this.output = output;
  }

  @Override
  public void dump(AbstractAirline airline) throws IOException {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
    objectOutputStream.writeObject(airline);
    objectOutputStream.flush();
    objectOutputStream.close();
  }
}
