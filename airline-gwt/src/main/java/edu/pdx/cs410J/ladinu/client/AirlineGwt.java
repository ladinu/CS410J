package edu.pdx.cs410J.ladinu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.ladinu.common.Flight;
import edu.pdx.cs410J.ladinu.common.FlightValidator;
import edu.pdx.cs410J.ladinu.common.FlightValidatorException;

import java.util.ArrayList;
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

  public void onModuleLoad() {
    flightNumberTextBox = getNumberBox("flight-number-text-box");
    airlineNameTextBox = getTextBox("airline-name-text-box");
    sourcAirportTextBox = getTextBox("source-airport-text-box");
    departureDateTextBox = getTextBox("departure-date-text-box");
    departureTimeTextBox = getTextBox("departure-time-text-box");
    destinationAirportTextBox = getTextBox("destination-airport-text-box");
    arrivalDateTextBox = getTextBox("arrival-date-text-box");
    arrivalTimeTextBox = getTextBox("arrival-time-text-box");

    Button addFlightButton = getButton("flight-add-button");


    addFlightButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        Map<String, String> flightInfoMap = getFlightInfoMap();
        ArrayList<String> errorList = new ArrayList<>();

        validateAirlineName(errorList, "airline-name-field");
        validateFlightNumber(flightInfoMap, errorList);
        validateSourceAirport(flightInfoMap, errorList);
        validateDestinationAirport(flightInfoMap, errorList);
        validateDepartureDateTime(flightInfoMap, errorList);
        validateArrivalDateTime(flightInfoMap, errorList);

        if (errorList.isEmpty()) {
          try {
            Flight flight = FlightValidator.getFlight(flightInfoMap);
            Window.alert(flight.toJSON());
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
    return Button.wrap(Document.get().getElementById(id));
  }

  public static SimpleRadioButton getRadioButton(String id) {
    return SimpleRadioButton.wrap(Document.get().getElementById(id));
  }

  public static TextBox getTextBox(String id) {
    return TextBox.wrap(Document.get().getElementById(id));
  }

  public static TextBox getNumberBox(String id) {
    TextBox tb = getTextBox(id);
    tb.getElement().setAttribute("type", "number");
    return tb;
  }

  public static void addErrorStyle(String id) {
    Document.get().getElementById(id).addClassName("has-error");
  }

  public static void removeErrorStyle(String id) {
    Document.get().getElementById(id).removeClassName("has-error");
  }

}
