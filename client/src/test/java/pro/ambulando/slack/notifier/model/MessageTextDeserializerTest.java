package pro.ambulando.slack.notifier.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTextDeserializerTest {

  private MessageTextDeserializer deserializer = new MessageTextDeserializer();

  @Test
  public void deserialize() {
    byte[] data = "{\"body\":{\"text\":\"this is a message\"}}".getBytes();
    Message<Text> expected = new Message<Text>()
        .withBody(Text.builder().text("this is a message").build());
    Message<Text> message = deserializer.deserialize(null, data);
    assertEquals(expected, message);
  }

  }