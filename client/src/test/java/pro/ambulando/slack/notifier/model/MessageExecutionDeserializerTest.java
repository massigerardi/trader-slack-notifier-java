package pro.ambulando.slack.notifier.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageExecutionDeserializerTest {

  private MessageExecutionDeserializer deserializer = new MessageExecutionDeserializer();

  @Test
  public void deserialize() {
    byte[] data = "{\"body\":{\"strategy\":\"strategy\",\"transactions\":[{\"orderId\":\"1234\",\"currencyPair\":\"ETHEUR\",\"exchange\":\"test\",\"command\":\"BUY X@Y\"}]},\"sender\":\"sender\",\"channel\":\"ABC\"}".getBytes();
    Transaction transaction =
        Transaction.builder()
            .command("BUY X@Y")
            .currencyPair("ETHEUR")
            .exchange("test")
            .orderId("1234")
            .build();
    Execution execution = Execution.builder().strategy("strategy").transaction(transaction).build();
    Message<Execution> expected = new Message<Execution>()
        .withBody(execution)
        .withSender("sender")
        .withChannel("ABC");
    Message<Execution> message = deserializer.deserialize(null, data);
    assertEquals(expected, message);
    assertEquals(expected.getSender(), message.getSender());
    assertEquals(expected.getChannel(), message.getChannel());
    assertEquals(expected.getBody(), message.getBody());
  }

}