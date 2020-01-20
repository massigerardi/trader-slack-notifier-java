package pro.ambulando.slack.stream;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import pro.ambulando.slack.KafkaTopic;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;
import pro.ambulando.slack.services.MessageSender;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaConsumerTaskFactory {

  private static AtomicInteger counter = new AtomicInteger();

  public static KafkaConsumerTask createTransactionConsumer(Properties config, MessageSender sender) {
    final Consumer<Long, Message<Transaction>> consumer = new KafkaConsumer<>(config);
    consumer.subscribe(Collections.singletonList(config.getProperty("topic.name")));
    return KafkaTransactionConsumerTask.builder().consumer(consumer).sender(sender).build();
  }

  public static KafkaConsumerTask createExecutionConsumer(Properties config, MessageSender sender) {
    final Consumer<Long, Message<Execution>> consumer = new KafkaConsumer<>(config);
    consumer.subscribe(Collections.singletonList(config.getProperty("topic.name")));
    return KafkaExecutionConsumerTask.builder().consumer(consumer).sender(sender).build();
  }

  public static KafkaConsumerTask createTextConsumer(Properties config, MessageSender sender) {
    final Consumer<Long, Message<Text>> consumer = new KafkaConsumer<>(config);
    consumer.subscribe(Collections.singletonList(config.getProperty("topic.name")));
    return KafkaTextConsumerTask.builder().consumer(consumer).sender(sender).build();
  }

  public static KafkaConsumerTask createConsumer(KafkaTopic topic, MessageSender sender) {
    final Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, topic.getBrokers());
    config.put(ConsumerConfig.GROUP_ID_CONFIG, topic.getGroup());
    config.put(ConsumerConfig.CLIENT_ID_CONFIG, topic.getType().toString()+String.valueOf(counter.incrementAndGet()));
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, topic.getKeyDeserializer());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, topic.getValueDeserializer());
    config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
    config.put("topic.name", topic.getName());
    switch (topic.getType()) {
      case TEXT: return createTextConsumer(config, sender);
      case EXECUTION: return createExecutionConsumer(config, sender);
      case TRANSACTION: return createTransactionConsumer(config, sender);
    }
    return null;
  }
}
