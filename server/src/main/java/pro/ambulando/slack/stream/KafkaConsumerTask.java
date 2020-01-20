package pro.ambulando.slack.stream;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.services.MessageSender;
import pro.ambulando.slack.services.NotificationException;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Builder
@Slf4j
public class KafkaConsumerTask implements Runnable {

  private MessageSender sender;

  Consumer<Long, Message> consumer;

  @PostConstruct
  public void run() {
    int count = 0;
    while(true) {
      ConsumerRecords<Long, Message> records = consumer.poll(Duration.of(30, ChronoUnit.SECONDS));
      if (records.count() == 0) continue;
      AtomicBoolean success = new AtomicBoolean(false);
      records.forEach(record -> {
        try {
          success.set(success.get() || sender.sendMessage(record.value()).isOk());
        } catch (NotificationException e) {
          e.printStackTrace();
        }
        log.info("MG Sent message {} - {} - {}", count, record.value().getId(), record.value().getBody());
      });

      if (success.get()) {
        consumer.commitAsync();
      }
    }

  }
}
