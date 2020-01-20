package pro.ambulando.slack.notifier.model;

public class MessageExecutionDeserializer extends MessageDeserializer<Execution> {

  @Override
  protected Class<Execution> getConvertClass() {
    return Execution.class;
  }
}
