package edu.pdx.cs410J.ladinu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {
  public void onModuleLoad() {
    HorizontalPanel container = new HorizontalPanel();

    Label airlineNameLabel = new Label("Airline Name: ");
    TextBox airlineNameTextBox = new TextBox();
    setPlaceHolder(airlineNameTextBox, "Alaska");

    container.add(airlineNameLabel);
    container.add(airlineNameTextBox);

    RootPanel rootPanel = RootPanel.get();
    //rootPanel.add(container);
  }

  private static void setPlaceHolder(Widget widget, String placeHolderText) {
    widget.getElement().setAttribute("placeholder", placeHolderText);
  }
}
