package pro.ambulando.slack.notifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
public class Message<T extends MessageBody> {

  @JsonProperty("message")
  @With private T body;

  @With private String id;

  @With private String comment;

  @With private String token;

  @With private String sender;

  @With private String channel;

  @With private String receiver;

  @With private boolean ephemeral;

  @With private String type;
}
