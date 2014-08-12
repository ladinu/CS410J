package edu.pdx.cs410J.ladinu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AirportNames;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

  private TextBox flightNumberTextBox;
  private TextBox airlineNameTextBox;
  private TextBox sourcAirportTextBox;
  private TextBox departureDateTextBox;
  private TextBox departureTimeTextBox;
  private TextBox destinationAirportTextBox;
  private TextBox arrivalDateTextBox;
  private TextBox arrivalTimeTextBox;
  private TextBox airlineSearchNameTextBox;
  private TextBox airlineSearchSourceTextBox;
  private TextBox airlineSearchDestinationTextBox;
  private FlightServiceAsync async;

  public void onModuleLoad() {
    async = GWT.create(FlightService.class);

    flightNumberTextBox = getNumberBox("flight-number-text-box");
    airlineNameTextBox = getTextBox("airline-name-text-box");
    sourcAirportTextBox = getTextBox("source-airport-text-box");
    departureDateTextBox = getTextBox("departure-date-text-box");
    departureTimeTextBox = getTextBox("departure-time-text-box");
    destinationAirportTextBox = getTextBox("destination-airport-text-box");
    arrivalDateTextBox = getTextBox("arrival-date-text-box");
    arrivalTimeTextBox = getTextBox("arrival-time-text-box");

    airlineSearchNameTextBox = getTextBox("airline-name-search");
    airlineSearchSourceTextBox = getTextBox("airport-source-search");
    airlineSearchDestinationTextBox = getTextBox("airport-destination-search");

    Button searchFlightButton = getButton("search-flight-button");
    Button addFlightButton = getButton("flight-add-button");

    // TODO: remove
    remvove();

    addFlightButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        Map<String, String> flightInfoMap = getFlightInfoMap();
        ArrayList<String> errorList = new ArrayList<>();
        String airlineName = airlineNameTextBox.getText();

        validateAirlineName(errorList, "airline-name-field");
        validateFlightNumber(flightInfoMap, errorList);
        validateSourceAirport(flightInfoMap, errorList);
        validateDestinationAirport(flightInfoMap, errorList);
        validateDepartureDateTime(flightInfoMap, errorList);
        validateArrivalDateTime(flightInfoMap, errorList);

        if (errorList.isEmpty()) {
          try {
            Flight flight = FlightValidator.getFlight(flightInfoMap);
            async.save(flight, airlineName, new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable throwable) {
                Window.alert(throwable.getMessage());
              }

              @Override
              public void onSuccess(Void result) {
                Window.alert("Saved!");
              }
            });
          } catch (FlightValidatorException e) {
            Window.alert("Something went wrong: " + e.getMessage());
          }
        } else {
          String allErrors = "";
          for (String err : errorList) {
            allErrors += err + "\n";
          }
          Window.alert(allErrors);
        }
      }
    });

    searchFlightButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        final String airlineName = airlineSearchNameTextBox.getText();
        final String srcAirport = airlineSearchSourceTextBox.getText();
        final String destAirport = airlineSearchDestinationTextBox.getText();

        async.getMap(new AsyncCallback<Map<String, Airline>>() {
          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.getMessage());
          }

          @Override
          public void onSuccess(Map<String, Airline> map) {
            // Clear the result table
            Element element = getElementById("result-table");
            element.removeAllChildren();
            removeErrorStyle("airport-source-search-field");
            removeErrorStyle("airport-destination-search-field");

            Airline airline = map.get(airlineName);
            if (airline != null) {
              if (airlineSearchDestinationTextBox.getText().isEmpty() &&
                  airlineSearchSourceTextBox.getText().isEmpty()) {
                for (Object o : airline.getFlights()) {
                  Flight f = (Flight) o;
                  element.appendChild(getTR(f));
                }
                return;
              }
              if (airlineSearchSourceTextBox.getText().isEmpty()) {
                addErrorStyle("airport-source-search-field");
                return;
              }
              if (airlineSearchDestinationTextBox.getText().isEmpty()) {
                addErrorStyle("airport-destination-search-field");
                return;
              }
              ArrayList<Flight> flights = new ArrayList<>();
              for (Object o : airline.getFlights()) {
                Flight f = (Flight) o;
                if (f.getDestination().equals(destAirport) && f.getSource().equals(srcAirport)) {
                  flights.add(f);
                }
              }
              if (!flights.isEmpty()) {
                for (Flight f : flights) {
                  element.appendChild(getTR(f));
                }
              }
            }
          }
        });
      }
    });
  }

  private void remvove() {
    flightNumberTextBox.setText("42");
    airlineNameTextBox.setText("Foo Bar");
    sourcAirportTextBox.setText("PDX");
    destinationAirportTextBox.setText("LAX");
    departureDateTextBox.setText("3/12/2014");
    departureTimeTextBox.setText("4:00");
    arrivalDateTextBox.setText("3/12/2014");
    arrivalTimeTextBox.setText("4:00");
  }

  public Element getTR(Flight flight) {
    Element tr = DOM.createTR();
    tr.appendChild(getTD(String.valueOf(flight.getNumber())));
    tr.appendChild(getTD(AirportNames.getName(flight.getSource())));
    tr.appendChild(getTD(flight.getDepartureString()));
    tr.appendChild(getTD(AirportNames.getName(flight.getDestination())));
    tr.appendChild(getTD(flight.getArrivalString()));
    tr.appendChild(getTD(String.valueOf(flight.getDuration())));
    return tr;
  }

  public Element getTD(String str) {
    Element td = DOM.createTD();
    td.setInnerHTML(str);
    return td;
  }

  public void validateArrivalDateTime(Map<String, String> flightInfoMap, ArrayList<String> errorList) {
    // Validate Arrival Time
    String arrivalDateTimeFieldId = "dest-date-time-field";
    try {
      FlightValidator.getArriveDateTime(flightInfoMap);
      removeErrorStyle(arrivalDateTimeFieldId);
    } catch (FlightValidatorException e) {
      addErrorStyle(arrivalDateTimeFieldId);
      errorList.add(e.getMessage());
    }
  }

  public void validateDepartureDateTime(Map<String, String> flightInfoMap, ArrayList<String> errorList) {
    // Validate Departure Time
    String sourceDateTimeFieldId = "src-date-time-field";
    try {
      FlightValidator.getDepartDateTime(flightInfoMap);
      removeErrorStyle(sourceDateTimeFieldId);
    } catch (FlightValidatorException e) {
      addErrorStyle(sourceDateTimeFieldId);
      errorList.add(e.getMessage());
    }
  }

  public void validateDestinationAirport(Map<String, String> flightInfoMap, ArrayList<String> errorList) {
    // Validate Destination Airport
    String destinationAirportFieldId = "destination-airport-field";
    try {
      FlightValidator.getDestAirport(flightInfoMap);
      removeErrorStyle(destinationAirportFieldId);
    } catch (FlightValidatorException e) {
      addErrorStyle(destinationAirportFieldId);
      errorList.add(e.getMessage());
    }
  }

  public void validateSourceAirport(Map<String, String> flightInfoMap, ArrayList<String> errorList) {
    // Validate Source Airport
    String sourceAirportFieldId = "source-airport-field";
    try {
      FlightValidator.getSrcAirport(flightInfoMap);
      removeErrorStyle(sourceAirportFieldId);
    } catch (FlightValidatorException e) {
      addErrorStyle(sourceAirportFieldId);
      errorList.add(e.getMessage());
    }
  }

  public void validateFlightNumber(Map<String, String> flightInfoMap, ArrayList<String> errorList) {
    // Validate Flight Number
    String flightNumberFieldId = "flight-number-field";
    try {
      FlightValidator.getFlightNumber(flightInfoMap);
      removeErrorStyle(flightNumberFieldId);
    } catch (FlightValidatorException e) {
      errorList.add(e.getMessage());
      addErrorStyle(flightNumberFieldId);
    }
  }

  public void validateAirlineName(ArrayList<String> errorList, String airlineNameFieldId) {
    // Validate AIrline Name
    if (airlineNameTextBox.getText().isEmpty()) {
      errorList.add("Airline name should not be blank");
      addErrorStyle(airlineNameFieldId);
    } else {
      removeErrorStyle(airlineNameFieldId);
    }
  }

  public  Map<String, String> getFlightInfoMap() {
    HashMap<String, String> flightInfo = new HashMap<>();
    flightInfo.put(FlightValidator.NUMBER_KEY, flightNumberTextBox.getText());
    flightInfo.put(FlightValidator.SRC_KEY, sourcAirportTextBox.getText());
    flightInfo.put(FlightValidator.DEPART_DATE_KEY, departureDateTextBox.getText());
    flightInfo.put(FlightValidator.DEPART_TIME_KEY, departureTimeTextBox.getText());
    flightInfo.put(FlightValidator.DEPART_TIME_AM_PM_KEY, getAmPm("src-am"));
    flightInfo.put(FlightValidator.DEST_KEY, destinationAirportTextBox.getText());
    flightInfo.put(FlightValidator.ARRIVE_DATE_KEY, arrivalDateTextBox.getText());
    flightInfo.put(FlightValidator.ARRIVE_TIME_KEY, arrivalTimeTextBox.getText());
    flightInfo.put(FlightValidator.ARRIVE_TIME_AM_PM_KEY, getAmPm("dest-am"));
    return flightInfo;
  }

  public static String getAmPm(String amId) {
    if (getRadioButton(amId).isEnabled()) {
      return "AM";
    } else {
      return "PM";
    }
  }

  public static Button getButton(String id) {
    return Button.wrap(getElementById(id));
  }

  public static SimpleRadioButton getRadioButton(String id) {
    return SimpleRadioButton.wrap(getElementById(id));
  }

  public static TextBox getTextBox(String id) {
    return TextBox.wrap(getElementById(id));
  }

  private static Element getElementById(String id) {
    return Document.get().getElementById(id);
  }

  public static TextBox getNumberBox(String id) {
    TextBox tb = getTextBox(id);
    tb.getElement().setAttribute("type", "number");
    return tb;
  }

  public static void addErrorStyle(String id) {
    getElementById(id).addClassName("has-error");
  }

  public static void removeErrorStyle(String id) {
    getElementById(id).removeClassName("has-error");
  }

}
