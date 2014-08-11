package edu.pdx.cs410J.ladinu.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.ladinu.client.Airline;
import edu.pdx.cs410J.ladinu.client.FlightService;
import edu.pdx.cs410J.ladinu.client.Flight;
import edu.pdx.cs410J.ladinu.client.FlightValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Airline service
 */
public class FlightServiceImpl extends RemoteServiceServlet implements FlightService
{

  private Map<String, Airline> airlineMap = new HashMap<>();

  public void save(Flight flight, String airlineName) {
    if (airlineMap.containsKey(airlineName)) {
      airlineMap.get(airlineName).addFlight(flight);
    } else {
      Airline airline = new Airline(airlineName);
      airline.addFlight(flight);
      airlineMap.put(airlineName, airline);
    }
  }

  public Map<String, Airline> getMap() {
    return airlineMap;
  }
}
