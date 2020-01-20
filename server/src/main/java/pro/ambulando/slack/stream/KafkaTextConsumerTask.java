package pro.ambulando.slack.stream;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.services.MessageSender;
import pro.ambulando.slack.services.NotificationException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
@Slf4j
public class KafkaTextConsumerTask implements KafkaConsumerTask {

  private MessageSender sender;

  private Consumer<Long, Message<Text>> consumer;

  public void run() {

    AtomicInteger count = new AtomicInteger();
    while (true) {
      ConsumerRecords<Long, Message<Text>> records = consumer.poll(Duration.of(30, ChronoUnit.SECONDS));
      if (records.count() == 0) continue;
      AtomicBoolean success = new AtomicBoolean(false);
      records.forEach(record -> {
        try {
          success.set(success.get() || sender.sendTextMessage(record.value()).isOk());
        } catch (NotificationException e) {
          e.printStackTrace();
        }
        log.info("MG Sent message {} - {}", count.getAndIncrement(), record.value().getBody());
      });

      if (success.get()) {
        consumer.commitAsync();
      }
    }

  }
}
