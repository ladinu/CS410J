package edu.pdx.cs410J.ladinu;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AirlineRestClientTest {

  @Test
  public void testGeneratesValidUrl() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put(ArgParser.HOST_OPTION_KEY, "i");
    map.put(ArgParser.PORT_OPTION_KEY, "8080");
    map.put(ArgParser.NAME_ARG_KEY, "Alaska");
    AirlineRestClient client = new AirlineRestClient(map);
    assertThat(client.getUrl(), equalTo("http://i:8080/airline/flights?name=Alaska"));
  }
}