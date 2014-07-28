package edu.pdx.cs410J.ladinu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AirlineServlet extends HttpServlet {
  private final Map<String, Airline> airlineMap = new HashMap<>();
  private final Map<String, String> data = new HashMap<String, String>();

  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
    response.setContentType( "text/plain" );
    String airlineName = getParameter("name", request);
    String srcAirport = getParameter("src", request);
    String destAirport = getParameter("dest", request);


    if (airlineName != null && (srcAirport == null && destAirport == null)) {
      writeAirline(airlineName, response);
    } else {
      Map<String, String> map = new HashMap<>();
      map.put("name", airlineName);
      map.put(FlightValidator.SRC_KEY, srcAirport);
      map.put(FlightValidator.DEST_KEY, destAirport);

      for (String key : map.keySet()) {
        if (map.get(key) == null) {
          missingRequiredParameter(response, key);
          return;
        }
      }
      try {
        FlightValidator.getSrcAirport(map);
        FlightValidator.getDestAirport(map);
      } catch (FlightValidatorException e) {
        PrintWriter pw = response.getWriter();
        pw.println(e.getMessage());
        pw.flush();
        response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
      }
      writeAirline(response, airlineName, srcAirport, destAirport);
    }
  }

  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
    response.setContentType( "text/plain" );

    String airline = getParameter("name", request);
    String number = getParameter(FlightValidator.NUMBER_KEY, request);
    String src = getParameter(FlightValidator.SRC_KEY, request);
    String departDate = getParameter(FlightValidator.DEPART_DATE_KEY, request);
    String departTime  = getParameter(FlightValidator.DEPART_TIME_KEY, request);
    String departTimeAmPm  = getParameter(FlightValidator.DEPART_TIME_AM_PM_KEY, request);
    String dest  = getParameter(FlightValidator.DEST_KEY, request);
    String arriveDate = getParameter(FlightValidator.ARRIVE_DATE_KEY, request);
    String arriveTime  = getParameter(FlightValidator.ARRIVE_TIME_KEY, request);
    String arriveTimeAmPm  = getParameter(FlightValidator.ARRIVE_TIME_AM_PM_KEY, request);

    Map<String, String> map = new HashMap<>();
    map.put("name", airline);
    map.put(FlightValidator.NUMBER_KEY, number);
    map.put(FlightValidator.SRC_KEY, src);
    map.put(FlightValidator.DEPART_DATE_KEY, departDate);
    map.put(FlightValidator.DEPART_TIME_KEY, departTime);
    map.put(FlightValidator.DEPART_TIME_AM_PM_KEY, departTimeAmPm);
    map.put(FlightValidator.DEST_KEY, dest);
    map.put(FlightValidator.ARRIVE_DATE_KEY, arriveDate);
    map.put(FlightValidator.ARRIVE_TIME_KEY, arriveTime);
    map.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, arriveTimeAmPm);

    for (String key : map.keySet()) {
      if (map.get(key) == null) {
        missingRequiredParameter(response, key);
        return;
      }
    }

    try {
      Flight flight = FlightValidator.getFlight(map);
      if (airlineMap.get(airline) != null)  {
        airlineMap.get(airline).addFlight(flight);
      } else {
        airlineMap.put(airline, new Airline(airline));
        airlineMap.get(airline).addFlight(flight);
      }
      response.setStatus( HttpServletResponse.SC_OK);
    } catch (FlightValidatorException e) {
      PrintWriter pw = response.getWriter();
      pw.println(e.getMessage());
      pw.flush();
      response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
      e.printStackTrace();
    }
  }

  private void missingRequiredParameter( HttpServletResponse response, String key )
      throws IOException
  {
    PrintWriter pw = response.getWriter();
    pw.println( Messages.missingRequiredParameter(key));
    pw.flush();

    response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
  }

  private void writeAirline( String airlineName, HttpServletResponse response ) throws IOException
  {
    Airline airline = this.airlineMap.get(airlineName);
    if (airline == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      PrettyPrinter pp = new PrettyPrinter(response.getWriter());
      pp.dump(airline);
      response.setStatus( HttpServletResponse.SC_OK );
    }
  }

  private void writeAirline(HttpServletResponse response, String airlineName, String srcAirport, String destAirport) throws IOException {
    Airline airline = this.airlineMap.get(airlineName);
    if (airline != null) {
      ArrayList<Flight> flights = new ArrayList<>();
      for (Object o : airline.getFlights()) {
        Flight f = (Flight)o;
        if (f.getDestination().equals(destAirport) && f.getSource().equals(srcAirport)) {
          flights.add(f);
        }
      }
      if (flights.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      PrintWriter pw = response.getWriter();
      pw.print("[");
      Iterator iterator = flights.iterator();
      while (iterator.hasNext()) {
        pw.print(((Flight) iterator.next()).toJSON());
        if (iterator.hasNext())
          pw.append(",\n");
      }
      pw.append("]");
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

}
