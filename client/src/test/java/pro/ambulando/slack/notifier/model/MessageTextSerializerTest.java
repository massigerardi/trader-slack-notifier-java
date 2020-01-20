package pro.ambulando.slack.notifier.model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MessageTextSerializerTest {

  private MessageTextSerializer deserializer = new MessageTextSerializer();

  @Test
  public void serialize() {
    byte[] expected = "{\"body\":{\"text\":\"this is a message\"}}".getBytes();
    Message<Text> message = new Message<Text>()
        .withBody(Text.builder().text("this is a message").build());
    byte[] serialized = deserializer.serialize(null, message);
    assertArrayEquals(expected, serialized);
  }

  }