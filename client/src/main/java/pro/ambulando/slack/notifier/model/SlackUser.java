package pro.ambulando.slack.notifier.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SlackUser {

  private String userName;

  private String id;

  private String token;

}
