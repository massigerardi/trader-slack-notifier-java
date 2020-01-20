package pro.ambulando.slack.notifier.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;

@Slf4j
public class MessageSerializer<T extends MessageBody> implements Serializer<Message<T>> {

  private ObjectMapper mapper = new ObjectMapper();

  public MessageSerializer() {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    this.mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    this.mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
  }

  public byte[] serialize(String s, Message<T> transaction) {
    try {
      return mapper.writeValueAsString(transaction).getBytes();
    } catch (JsonProcessingException e) {
      log.error("Serializing: ", e);
    }
    return null;
  }
}
