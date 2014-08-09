package edu.pdx.cs410J.ladinu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;


/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {
  public void onModuleLoad() {
    Button button = getButton("flight-add-button");
    final TextBox textBox = getTextBox("airline-name-text-box");


    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        textBox.setText("foo bar");
      }
    });
  }

  public static Button getButton(String id) {
    return Button.wrap(Document.get().getElementById(id));
  }

  public static TextBox getTextBox(String id) {
    return TextBox.wrap(Document.get().getElementById(id));
  }
}
