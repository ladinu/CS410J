package edu.pdx.cs410J.ladinu.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

/**
 * The client-side interface to the ping service
 */
public interface FlightServiceAsync {

  /**
   * Return the current date/time on the server
   */
  void save(Flight flight, String airlineName, AsyncCallback<Void> async);
  void getMap(AsyncCallback<Map<String, Airline>> async);
}
