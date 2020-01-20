package pro.ambulando.slack.notifier.model;

public class MessageTextDeserializer extends MessageDeserializer<Text> {

  @Override
  protected Class<Text> getConvertClass() {
    return Text.class;
  }
}
