package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline {
  private String name;
  private ArrayList<AbstractFlight> flights = new ArrayList<>();


  public Airline(String name) {
    setName(name);
  }
  public Airline() {
    super();
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void addFlight(AbstractFlight abstractFlight) {
    flights.add(abstractFlight);
  }

  @Override
  public Collection getFlights() {
    return flights;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
