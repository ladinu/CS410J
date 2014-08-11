package edu.pdx.cs410J.ladinu.client;

/**
 * This exception is thrown when a Flight cannot be created by given data
 */
public class FlightValidatorException extends Exception {
  public FlightValidatorException(String message) {
    super(message);
  }
}
