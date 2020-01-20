package pro.ambulando.slack.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pro.ambulando.slack.KafkaConfiguration;
import pro.ambulando.slack.KafkaTopic;
import pro.ambulando.slack.services.MessageSender;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@Profile("kafka")
public class KafkaConsumerRunner {

  @Autowired
  private MessageSender sender;

  @Autowired
  private KafkaConfiguration configuration;

  @PostConstruct
  public void init() {
    log.info("running consumers");
    List<KafkaTopic> topics = configuration.getTopics();
    List<KafkaConsumerTask> consumers = topics.stream().map(topic -> KafkaConsumerTaskFactory.createConsumer(topic, sender)).collect(Collectors.toList());
    Executor executor = new ThreadPoolExecutor(topics.size(), topics.size(), 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    consumers.stream().forEach(consumer -> executor.execute(consumer));
  }



}
