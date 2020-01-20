package pro.ambulando.slack.notifier.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pro.ambulando.slack.notifier.model.Message;

import java.io.IOException;

public class MessageSerializer extends StdSerializer<Message> {

  protected MessageSerializer() {
    super(Message.class);
  }

  @Override
  public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    String type = message.getBody().getClass().getSimpleName();
    jsonGenerator.writeStartObject();
    if (message.getId() != null) jsonGenerator.writeStringField("id", message.getId());
    if (message.getChannel() != null) jsonGenerator.writeStringField("channel", message.getChannel());
    if (message.getSender() != null) jsonGenerator.writeStringField("sender", message.getSender());
    if (message.getReceiver() != null) jsonGenerator.writeStringField("receiver", message.getReceiver());
    if (message.getToken() != null) jsonGenerator.writeStringField("token", message.getToken());
    jsonGenerator.writeBooleanField("ephemeral", message.isEphemeral());
    if (message.getBody() != null) jsonGenerator.writeObjectField("message", message.getBody());
    if (message.getBody() != null) jsonGenerator.writeStringField("type", type);
    jsonGenerator.writeEndObject();
  }
}
