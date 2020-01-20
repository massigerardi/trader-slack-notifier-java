package pro.ambulando.slack.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SlackNotificationResponse<T> {

  private boolean ok;
  private String error;
  private T body;

  public static <T> SlackNotificationResponse of(T body) {
    return new SlackNotificationResponse(body);
  }

  public static <T> SlackNotificationResponse error(String errorMessage) {
    return new SlackNotificationResponse(errorMessage);
  }

  public SlackNotificationResponse(T body) {
    this.ok = true;
    this.body = body;
  }

  public SlackNotificationResponse(String error) {
    this.ok = false;
    this.error = error;
  }
}
