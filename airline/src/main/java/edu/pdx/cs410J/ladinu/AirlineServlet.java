package edu.pdx.cs410J.ladinu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AirlineServlet extends HttpServlet {
  private final Map<String, Airline> airlineMap = new HashMap<>();
  private final Map<String, String> data = new HashMap<String, String>();

  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
    response.setContentType( "text/plain" );
    String airlineName = getParameter("name", request);
    if (airlineName != null) {
      writeAirline(airlineName, response);
    } else {
      missingRequiredParameter(response, "name");
    }
  }

  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
    response.setContentType( "text/plain" );

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
        break;
      }
    }

    try {
      Flight flight = FlightValidator.getFlight(map);
      if (airlineMap.get("Alaska") != null) {
        airlineMap.get("Alaska").addFlight(flight);
      } else {
        airlineMap.put("Alaska", new Airline("Alaska"));
        airlineMap.get("Alaska").addFlight(flight);
      }
    } catch (FlightValidatorException e) {
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

  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

}
