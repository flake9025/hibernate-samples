package fr.vvlabs.hibernate.sample.exception;

public class SampleException extends Exception {

  public SampleException(String message) {
    super(message);
  }

  public SampleException(String message, Object key) {
    super(message + ", key=" + key);
  }
}
