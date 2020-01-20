package pro.ambulando.slack.notifier.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageExecutionSerializerTest {

  private MessageExecutionSerializer serializer = new MessageExecutionSerializer();

  @Test
  public void serialize() {
    Transaction transaction = Transaction.builder()
        .command("BUY X@Y")
        .currencyPair("ETHEUR")
        .exchange("test")
        .orderId("1234")
        .build();
    Execution execution = Execution.builder().strategy("strategy").transaction(transaction).build();
    Message<Execution> message = new Message<Execution>()
        .withBody(execution)
        .withSender("sender")
        .withChannel("ABC");
    byte[] serialized = serializer.serialize(null, message);
    byte[] expected = "{\"body\":{\"strategy\":\"strategy\",\"transactions\":[{\"orderId\":\"1234\",\"currencyPair\":\"ETHEUR\",\"exchange\":\"test\",\"command\":\"BUY X@Y\"}]},\"sender\":\"sender\",\"channel\":\"ABC\"}".getBytes();

    assertArrayEquals(expected, serialized);

  }

}