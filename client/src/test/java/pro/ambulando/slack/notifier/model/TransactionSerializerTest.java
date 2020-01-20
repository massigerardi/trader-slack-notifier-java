package pro.ambulando.slack.notifier.model;

import org.junit.Test;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageTransactionSerializer;
import pro.ambulando.slack.notifier.model.Transaction;

import static org.junit.Assert.assertArrayEquals;

public class TransactionSerializerTest {

  private MessageTransactionSerializer serializer = new MessageTransactionSerializer();

  @Test
  public void serialize() {
    Transaction transaction = Transaction.builder()
        .command("BUY X@Y")
        .currencyPair("ETHEUR")
        .exchange("test")
        .orderId("1234")
        .build();
    Message<Transaction> message = new Message<Transaction>()
        .withBody(transaction)
        .withSender("sender")
        .withChannel("ABC");
    byte[] serialized = serializer.serialize(null, message);
    byte[] expected = "{\"body\":{\"orderId\":\"1234\",\"currencyPair\":\"ETHEUR\",\"exchange\":\"test\",\"command\":\"BUY X@Y\"},\"sender\":\"sender\",\"channel\":\"ABC\"}".getBytes();
    assertArrayEquals(expected, serialized);

  }
}