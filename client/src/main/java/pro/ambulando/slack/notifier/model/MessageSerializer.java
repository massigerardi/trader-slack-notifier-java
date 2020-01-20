package pro.ambulando.slack.notifier.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public abstract class MessageSerializer<T extends MessageBody> implements Serializer<Message<T>> {

  private ObjectMapper mapper = new ObjectMapper();

  public byte[] serialize(String s, Message<T> transaction) {
    try {
      return mapper.writeValueAsString(transaction).getBytes();
    } catch (JsonProcessingException e) {
      log.error("Serializing: ", e);
    }
    return null;
  }
}
