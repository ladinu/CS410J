package edu.pdx.cs410J.ladinu.client;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    ArrayList<Flight> flights1 = new ArrayList<>();
    for (AbstractFlight f : flights) {
      flights1.add((Flight)f);
    }
    Collections.sort(flights1);
    return flights1;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  public String toJSON() {
    StringBuilder sb = new StringBuilder();
    Iterator iterator = this.getFlights().iterator();

    sb.append("{\n\tname: \"" + this.name + "\",\n\tflights: [");
    while (iterator.hasNext()) {
      sb.append(((Flight)iterator.next()).toJSON());
      if (iterator.hasNext())
        sb.append(",\n");
    }
    sb.append("\t         ]\n}\n");
    return sb.toString();
  }
}
