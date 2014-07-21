package edu.pdx.cs410J.ladinu;

import edu.pdx.cs410J.AbstractFlight;

import java.util.Date;

public class Flight extends AbstractFlight {

  private int number;
  private String src;
  private String departTime;
  private String dest;
  private String ariveTime;

  public Flight() {
    super();
  }

  public Flight(int number, String src, String departTime, String dest, String ariveTime) {
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
    return departTime;
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
    return ariveTime;
  }

  public  String toJSON() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("number:\"" + this.number + "\",");
    sb.append("src:\"" + this.src + "\",");
    sb.append("departDate:\"" + this.departTime.split(" ")[0] + "\",");
    sb.append("departTime:\"" + this.departTime.split(" ")[1] + "\",");
    sb.append("dest:\"" + this.dest + "\",");
    sb.append("arriveDate:\"" + this.ariveTime.split(" ")[0] + "\",");
    sb.append("arriveTime:\"" + this.ariveTime.split(" ")[1] + "\"");
    sb.append("}");
    return sb.toString();
  }
}
