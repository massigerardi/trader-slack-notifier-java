package pro.ambulando.slack.notifier.producer;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.clients.producer.KafkaProducer;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;

@Builder
public class MessageTextProducer extends MessageProducer<Text> {

  @Getter
  private KafkaProducer<Long, Message<Text>> producer;

}
