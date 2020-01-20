package pro.ambulando.slack.notifier.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public abstract class MessageDeserializer<T extends MessageBody> implements Deserializer<Message<T>> {

  private ObjectMapper mapper = new ObjectMapper();

  public Message<T> deserialize(String s, byte[] data) {
    try {
      JavaType type = mapper.getTypeFactory().constructParametricType(Message.class, getConvertClass());
      return mapper.readValue(data, type);
    } catch (IOException e) {
      log.error("Deserializing: ", e);
    }
    return null;

  }

  protected abstract Class<T> getConvertClass();

}
