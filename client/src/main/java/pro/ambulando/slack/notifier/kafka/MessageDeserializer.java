package pro.ambulando.slack.notifier.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;

import java.io.IOException;

@Slf4j
public class MessageDeserializer<T extends MessageBody> implements Deserializer<Message<T>> {

  private ObjectMapper mapper = new ObjectMapper();

  public MessageDeserializer() {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    this.mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    this.mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
  }

  public Message<T> deserialize(String s, byte[] data) {
    try {
      return mapper.readValue(data, Message.class);
    } catch (IOException e) {
      log.error("Deserializing: ", e);
    }
    return null;

  }

}
