package edu.pdx.cs410J.ladinu.common;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.util.Date;

public class Flight extends AbstractFlight implements Comparable <Flight> {

  private int number;
  private String src;
  private Date departTime;
  private String dest;
  private Date ariveTime;

  public Flight() {
    super();
  }

  public Flight(int number, String src, Date departTime, String dest, Date ariveTime) {
    this.number = number;
    this.src = src;
    this.departTime = departTime;
    this.dest = dest;
    this.ariveTime = ariveTime;
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public String getSource() {
    return src;
  }

  @Override
  public Date getDeparture() {
    return super.getDeparture();
  }

  @Override
  public String getDepartureString() {
    DateTimeFormat format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT);
    return format.format(departTime);
  }

  @Override
  public String getDestination() {
    return dest;
  }

  @Override
  public Date getArrival() {
    return super.getArrival();
  }

  @Override
  public String getArrivalString() {
    DateTimeFormat format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT);
    return format.format(departTime);
  }

  /**
   * Return a JSON version of all the flight data
   * @return
   */
  public  String toJSON() {
    long duration = ariveTime.getTime() - departTime.getTime();
    duration = duration / (60 * 1000) % 60;

    StringBuilder sb = new StringBuilder();
    sb.append("\n\t\t\t{ number: \"" + this.number + "\",\n");
    sb.append("\t\t\t  src: \"" + AirportNames.getName(this.src) + "\",\n");
    sb.append("\t\t\t  departDate: \"" + this.getDepartureString() + "\",\n");
    sb.append("\t\t\t  dest: \"" + AirportNames.getName(this.dest) + "\",\n");
    sb.append("\t\t\t  arriveDate: \"" + this.getArrivalString() + "\"\n");
    sb.append("\t\t\t  duration: \"" + duration + " minutes\"\n");
    sb.append("\t\t\t}");
    return sb.toString();
  }

  @Override
  public int compareTo(Flight f) {
    if (f == this) {
      return 0;
    } else if (!f.src.equals(this.src)) {
      return f.src.compareTo(this.src);
    } else {
      return f.departTime.compareTo(this.departTime);
    }
  }
}
