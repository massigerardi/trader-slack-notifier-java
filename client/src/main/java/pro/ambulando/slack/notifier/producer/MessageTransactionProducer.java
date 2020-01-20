package pro.ambulando.slack.notifier.producer;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.clients.producer.KafkaProducer;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Transaction;

@Builder
public class MessageTransactionProducer extends MessageProducer<Transaction> {

  @Getter
  private KafkaProducer<Long, Message<Transaction>> producer;

}
