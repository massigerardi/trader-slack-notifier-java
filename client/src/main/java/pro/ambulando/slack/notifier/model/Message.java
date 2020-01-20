package pro.ambulando.slack.notifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import pro.ambulando.slack.notifier.json.MessageDeserializer;
import pro.ambulando.slack.notifier.json.MessageSerializer;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonDeserialize(using = MessageDeserializer.class)
@JsonSerialize(using = MessageSerializer.class)
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
