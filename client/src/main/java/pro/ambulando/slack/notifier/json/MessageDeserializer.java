package pro.ambulando.slack.notifier.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;

import java.io.IOException;

@Slf4j
public class MessageDeserializer extends StdDeserializer<Message> {

  protected MessageDeserializer() {
    super(Message.class);
  }

  @Override
  public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    try {
      JsonNode node = jsonParser.getCodec().readTree(jsonParser);
      String type = node.get("type").asText();
      ObjectMapper mapper = new ObjectMapper();
      String className = "pro.ambulando.slack.notifier.model."+type;
      MessageBody body = (MessageBody) mapper.treeToValue(node.get("message"), Class.forName(className));
      String id = node.has("id") ? node.get("id").asText() : null;
      String sender = node.has("sender") ? node.get("sender").asText() : null;
      String receiver = node.has("receiver") ? node.get("receiver").asText() : null;
      String channel = node.has("channel") ? node.get("channel").asText() : null;
      String token = node.has("token") ? node.get("token").asText() : null;
      boolean ephemeral = node.has("ephemeral") ? node.get("ephemeral").asBoolean() : false;
      return Message.builder()
          .body(body)
          .id(id)
          .sender(sender)
          .receiver(receiver)
          .channel(channel)
          .token(token)
          .ephemeral(ephemeral)
          .build();
    } catch (ClassNotFoundException e) {
      log.error("Wrong Type", e);
      throw new IOException(e);
    }
  }

}
