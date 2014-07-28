package edu.pdx.cs410J.ladinu;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FlightValidatorTest {

  @Test
  public void testGetFlightNumber() throws Exception {
    Map<String, String> map = m(FlightValidator.NUMBER_KEY, "4");
    assertThat(FlightValidator.getFlightNumber(map), equalTo(4));
  }

  @Test (expected = FlightValidatorException.class)
  public void testGetFlightNunberThrow() throws Exception {
    FlightValidator.getFlightNumber(m(FlightValidator.NUMBER_KEY, "q"));
  }

  @Test
  public void testGetSrcAirport() throws Exception {
    Map<String, String> map = m(FlightValidator.SRC_KEY, "PDX");
    assertThat(FlightValidator.getSrcAirport(map), equalTo("PDX"));
  }

  @Test
  public void testGetSrcAirportCase() throws Exception {
    Map<String, String> map = m(FlightValidator.SRC_KEY, "pDx");
    assertThat(FlightValidator.getSrcAirport(map), equalTo("PDX"));
  }

  @Test (expected = FlightValidatorException.class)
  public void testGetSrcAirportThrows() throws Exception {
    FlightValidator.getSrcAirport(m(FlightValidator.SRC_KEY, "foo"));
  }

  @Test
  public void testGetDestcAirport() throws Exception {
    Map<String, String> map = m(FlightValidator.DEST_KEY, "PDX");
    assertThat(FlightValidator.getDestAirport(map), equalTo("PDX"));
  }

  @Test
  public void testGetDestAirportCase() throws Exception {
    Map<String, String> map = m(FlightValidator.DEST_KEY, "pDx");
    assertThat(FlightValidator.getDestAirport(map), equalTo("PDX"));
  }

  @Test (expected = FlightValidatorException.class)
  public void testGetDestAirportThrows() throws Exception {
    FlightValidator.getDestAirport(m(FlightValidator.DEST_KEY, "foo"));
  }

  @Test
  public void testGetDateTimeDepart() throws Exception {
    Map<String, String> map = m(FlightValidator.DEPART_DATE_KEY, "3/12/2014");
    map.put(FlightValidator.DEPART_TIME_KEY, "9:00");
    map.put(FlightValidator.DEPART_TIME_AM_PM_KEY, "PM");
    Date expected = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse("3/12/2014 9:00 PM");
    Date actual = FlightValidator.getDepartDateTime(map);
    assertThat(actual.toString(), equalTo(expected.toString()));
  }

  @Test
  public void test1GetDateTimeDepart() throws Exception {
    Map<String, String> map = m(FlightValidator.DEPART_DATE_KEY, "03/12/2014");
    map.put(FlightValidator.DEPART_TIME_KEY, "9:00");
    map.put(FlightValidator.DEPART_TIME_AM_PM_KEY, "PM");
    Date expected = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse("3/12/2014 9:00 PM");
    Date actual = FlightValidator.getDepartDateTime(map);
    assertThat(actual.toString(), equalTo(expected.toString()));
  }

  @Test
  public void testGetDateTimeAmPmDepart() throws Exception {
    Map<String, String> map = m(FlightValidator.DEPART_DATE_KEY, "3/12/2014");
    map.put(FlightValidator.DEPART_TIME_KEY, "9:00");
    map.put(FlightValidator.DEPART_TIME_AM_PM_KEY, "aM");
    Date expected = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse("3/12/2014 9:00 AM");
    Date actual = FlightValidator.getDepartDateTime(map);
    assertThat(actual.toString(), equalTo(expected.toString()));
  }

  @Test (expected = FlightValidatorException.class)
  public void testGetDateTimeThrowsDepart() throws Exception {
    Map<String, String> map = m(FlightValidator.DEPART_DATE_KEY, "3/12/14");
    map.put(FlightValidator.DEPART_TIME_KEY, "9:00");
    map.put(FlightValidator.DEPART_TIME_AM_PM_KEY, "aM");
    FlightValidator.getDepartDateTime(map);
  }

  @Test (expected = FlightValidatorException.class)
  public void test1GetDateTimeThrowsDepart() throws Exception {
    Map<String, String> map = m(FlightValidator.DEPART_DATE_KEY, "3/12/14");
    map.put(FlightValidator.DEPART_TIME_KEY, "19:00");
    map.put(FlightValidator.DEPART_TIME_AM_PM_KEY, "aM");
    FlightValidator.getDepartDateTime(map);
  }


  @Test
  public void testGetDateTimeArrive() throws Exception {
    Map<String, String> map = m(FlightValidator.ARRIVE_DATE_KEY, "3/12/2014");
    map.put(FlightValidator.ARRIVE_TIME_KEY, "9:00");
    map.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, "PM");
    Date expected = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse("3/12/2014 9:00 PM");
    Date actual = FlightValidator.getArriveDateTime(map);
    assertThat(actual.toString(), equalTo(expected.toString()));
  }

  @Test
  public void test1GetDateTimeArrive() throws Exception {
    Map<String, String> map = m(FlightValidator.ARRIVE_DATE_KEY, "03/12/2014");
    map.put(FlightValidator.ARRIVE_TIME_KEY, "9:00");
    map.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, "PM");
    Date expected = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse("3/12/2014 9:00 PM");
    Date actual = FlightValidator.getArriveDateTime(map);
    assertThat(actual.toString(), equalTo(expected.toString()));
  }

  @Test
  public void testGetDateTimeAmPmArrive() throws Exception {
    Map<String, String> map = m(FlightValidator.ARRIVE_DATE_KEY, "3/12/2014");
    map.put(FlightValidator.ARRIVE_TIME_KEY, "9:00");
    map.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, "aM");
    Date expected = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse("3/12/2014 9:00 AM");
    Date actual = FlightValidator.getArriveDateTime(map);
    assertThat(actual.toString(), equalTo(expected.toString()));
  }

  @Test (expected = FlightValidatorException.class)
  public void testGetDateTimeThrowsArrive() throws Exception {
    Map<String, String> map = m(FlightValidator.ARRIVE_DATE_KEY, "3/12/14");
    map.put(FlightValidator.ARRIVE_TIME_KEY, "9:00");
    map.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, "aM");
    FlightValidator.getArriveDateTime(map);
  }

  @Test (expected = FlightValidatorException.class)
  public void test1GetDateTimeThrowsArrive() throws Exception {
    Map<String, String> map = m(FlightValidator.ARRIVE_DATE_KEY, "3/12/14");
    map.put(FlightValidator.ARRIVE_TIME_KEY, "19:00");
    map.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, "aM");
    FlightValidator.getArriveDateTime(map);
  }

  public Map<String, String> m(String key, String value) {
    Map<String, String> map = new HashMap<>();
    map.put(key, value);
    return map;
  }
}