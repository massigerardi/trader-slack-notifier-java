package pro.ambulando.slack.services;

public class NotificationException extends Throwable {

  public NotificationException(Throwable e) {
    super(e);
  }

  public NotificationException(String message, Throwable cause) {
    super(message, cause);
  }
}
