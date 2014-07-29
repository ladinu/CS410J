package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;

    public String getUrl() {
      return url;
    }

    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param map The map gotten from ArgParse
     */
    public AirlineRestClient( Map<String, String> map)
    {
        String hostName = map.get(ArgParser.HOST_OPTION_KEY);
        String port = map.get(ArgParser.PORT_OPTION_KEY);
        String name = map.get(ArgParser.NAME_ARG_KEY);
        try {
          name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          Project4.error(e.getMessage());
        }
      this.url = MessageFormat.format(
            "http://{0}:{1}/{2}/{3}?name={4}", hostName, port, WEB_APP, SERVLET, name);
    }

    /**
     * Returns all keys and values from the server
     */
    public Response getAllKeysAndValues() throws IOException
    {
        return get(this.url );
    }

    /**
     * Returns all values for the given key
     */
    public Response getValues( String key ) throws IOException
    {
        return get(this.url, "key", key);
    }

  /**
   * Posts the flight data to the server. At this point, the map is expected
   * to have all valid data (regardless if server validate or not).
   * @param map Contains all flight info from commandline
   * @return
   * @throws IOException
   */
    public Response postFlight(Map<String, String> map) throws IOException
    {
      return post( this.url, FlightValidator.SRC_KEY, map.get(FlightValidator.SRC_KEY),
                     FlightValidator.NUMBER_KEY, map.get(FlightValidator.NUMBER_KEY),
                     FlightValidator.DEPART_DATE_KEY, map.get(FlightValidator.DEPART_DATE_KEY),
                     FlightValidator.DEPART_TIME_KEY, map.get(FlightValidator.DEPART_TIME_KEY),
                     FlightValidator.DEPART_TIME_AM_PM_KEY, map.get(FlightValidator.DEPART_TIME_AM_PM_KEY),
                     FlightValidator.DEST_KEY, map.get(FlightValidator.DEST_KEY),
                     FlightValidator.ARRIVE_DATE_KEY, map.get(FlightValidator.ARRIVE_DATE_KEY),
                     FlightValidator.ARRIVE_TIME_KEY, map.get(FlightValidator.ARRIVE_TIME_KEY),
                     FlightValidator.ARRIVE_TIME_AM_PM_KEY, map.get(FlightValidator.ARRIVE_TIME_AM_PM_KEY));
      }
}
