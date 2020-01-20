package pro.ambulando.slack.notifier.producer;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.clients.producer.KafkaProducer;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;

@Builder
public class MessageExecutionProducer extends MessageProducer<Execution> {

  @Getter
  private KafkaProducer<Long, Message<Execution>> producer;

}
