package pro.ambulando.slack.notifier.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;

import java.util.Properties;

public class ProducerFactory {

  public MessageTextProducer messageTextProducer(Properties configuration) {
    KafkaProducer<Long, Message<Text>> producer = new KafkaProducer<>(configuration);
    return MessageTextProducer.builder().producer(producer).build();
  }

  public MessageExecutionProducer messageExecutionProducer(Properties configuration) {
    KafkaProducer<Long, Message<Execution>> producer = new KafkaProducer<>(configuration);
    return MessageExecutionProducer.builder().producer(producer).build();
  }

  public MessageTransactionProducer messageTransactionProducer(Properties configuration) {
    KafkaProducer<Long, Message<Transaction>> producer = new KafkaProducer<>(configuration);
    return MessageTransactionProducer.builder().producer(producer).build();
  }


}
