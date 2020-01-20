package pro.ambulando.slack.streams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import pro.ambulando.slack.KafkaTopic;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.services.MessageSender;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTaskFactory {

  private static KafkaConsumerTask createConsumer(Properties config, MessageSender sender) {
    final Consumer<Long, Message> consumer = new KafkaConsumer<>(config);
    consumer.subscribe(Collections.singletonList(config.getProperty("topic.name")));
    return KafkaConsumerTask.builder().consumer(consumer).sender(sender).build();
  }

  public static KafkaConsumerTask createConsumer(KafkaTopic topic, MessageSender sender) {
    final Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, topic.getBrokers());
    config.put(ConsumerConfig.GROUP_ID_CONFIG, topic.getGroup());
    config.put(ConsumerConfig.CLIENT_ID_CONFIG, topic.getGroup());
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, topic.getKeyDeserializer());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, topic.getValueDeserializer());
    config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
    config.put("topic.name", topic.getName());
    return createConsumer(config, sender);
  }
}
