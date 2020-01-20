package pro.ambulando.slack.notifier.model;

import org.junit.Test;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageTransactionDeserializer;
import pro.ambulando.slack.notifier.model.Transaction;

import static org.junit.Assert.*;

public class TransactionDeserializerTest {

  private MessageTransactionDeserializer deserializer = new MessageTransactionDeserializer();

  @Test
  public void deserialize() {
    byte[] data = "{\"body\":{\"orderId\":\"1234\",\"currencyPair\":\"ETHEUR\",\"exchange\":\"test\",\"command\":\"BUY X@Y\"},\"channel\":\"ABC\",\"sender\":\"sender\"}".getBytes();
    Transaction expectedTransaction =
        Transaction.builder()
        .command("BUY X@Y")
        .currencyPair("ETHEUR")
        .exchange("test")
        .orderId("1234")
        .build();
    Message<Transaction> expected = new Message<Transaction>()
        .withBody(expectedTransaction)
        .withSender("sender")
        .withChannel("ABC");
    Message<Transaction> message = deserializer.deserialize(null, data);
    assertEquals(expected, message);
  }
}