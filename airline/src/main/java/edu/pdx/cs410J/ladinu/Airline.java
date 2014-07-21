package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

  public String toJSON() {
    StringBuilder sb = new StringBuilder();
    Iterator iterator = flights.iterator();

    sb.append("[");
    while (iterator.hasNext()) {
      sb.append(((Flight)iterator.next()).toJSON());
      if (iterator.hasNext())
        sb.append(",");
    }
    sb.append("]");
    return sb.toString();
  }
}
