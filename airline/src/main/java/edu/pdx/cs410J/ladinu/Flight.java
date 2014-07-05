package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractFlight;

import java.util.Date;

public class Flight extends AbstractFlight {
  public Flight() {
    super();
  }

  @Override
  public int getNumber() {
    return 0;
  }

  @Override
  public String getSource() {
    return null;
  }

  @Override
  public Date getDeparture() {
    return super.getDeparture();
  }

  @Override
  public String getDepartureString() {
    return null;
  }

  @Override
  public String getDestination() {
    return null;
  }

  @Override
  public Date getArrival() {
    return super.getArrival();
  }

  @Override
  public String getArrivalString() {
    return null;
  }
}
