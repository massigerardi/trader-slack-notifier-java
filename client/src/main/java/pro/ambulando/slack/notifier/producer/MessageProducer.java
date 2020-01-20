package pro.ambulando.slack.notifier.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class MessageProducer<V extends MessageBody> {

  private AtomicLong key = new AtomicLong();

  public void produce(Message<V> message, String topic) {
    ProducerRecord<Long, Message<V>> record = new ProducerRecord<Long, Message<V>>(topic, key.incrementAndGet(), message);
    try {
      RecordMetadata metadata = getProducer().send(record).get();
      log.info("record sent with offset {} to topic {}: {}", metadata.offset(), topic, message);
    } catch (InterruptedException | ExecutionException e) {
      log.error("Text Producer", e);
    }
  }

  protected abstract KafkaProducer<Long, Message<V>> getProducer();

}
