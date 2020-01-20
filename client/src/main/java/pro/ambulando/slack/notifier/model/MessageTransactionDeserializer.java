package pro.ambulando.slack.notifier.model;

public class MessageTransactionDeserializer extends MessageDeserializer<Transaction> {

  @Override
  protected Class<Transaction> getConvertClass() {
    return Transaction.class;
  }
}
