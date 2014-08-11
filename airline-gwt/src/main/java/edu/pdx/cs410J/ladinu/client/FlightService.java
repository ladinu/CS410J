package edu.pdx.cs410J.ladinu.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;

/**
 * A GWT remote service that returns a dummy airline
 */
@RemoteServiceRelativePath("save")
public interface FlightService extends RemoteService {
  /**
   * Returns the current date and time on the server
   */
  public void save(Flight flight, String airlineName);

  public Map<String, Airline> getMap();

}
