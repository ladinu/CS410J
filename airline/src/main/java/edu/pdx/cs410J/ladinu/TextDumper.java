package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.DataOutputStream;
import java.io.IOException;

public class TextDumper implements AirlineDumper {

  private DataOutputStream output;

  public void setOutput(DataOutputStream output) {
    this.output = output;
  }


  @Override
  public void dump(AbstractAirline airline) throws IOException {

  }
}
