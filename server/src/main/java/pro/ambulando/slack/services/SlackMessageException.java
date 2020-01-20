package pro.ambulando.slack.services;

public class SlackMessageException extends Throwable {

  public SlackMessageException(String errorMessage) {
    super(errorMessage);
  }
}
