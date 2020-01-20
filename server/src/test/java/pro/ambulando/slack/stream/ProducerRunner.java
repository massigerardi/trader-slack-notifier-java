package pro.ambulando.slack.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageExecutionSerializer;
import pro.ambulando.slack.notifier.model.MessageTextSerializer;
import pro.ambulando.slack.notifier.model.MessageTransactionSerializer;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;
import pro.ambulando.slack.notifier.producer.MessageExecutionProducer;
import pro.ambulando.slack.notifier.producer.MessageTextProducer;
import pro.ambulando.slack.notifier.producer.MessageTransactionProducer;
import pro.ambulando.slack.notifier.producer.ProducerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ProducerRunner {

  private ProducerFactory factory = new ProducerFactory();

  AtomicInteger idCounter = new AtomicInteger();
  private String getId() {
    String date = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
    String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
    String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
    return "MESSAGE_"+date+hour+minute+"_"+idCounter.getAndIncrement();
  }

  public MessageTextProducer createTextProducer() {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "textClient");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageTextSerializer.class.getName());
    return factory.messageTextProducer(props) ;
  }

  public MessageTransactionProducer createTransactionProducer() {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "transactionProducer");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageTransactionSerializer.class.getName());
    return factory.messageTransactionProducer(props) ;
  }

  public MessageExecutionProducer createExecutionProducer() {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaConstants.CLIENT_ID);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageExecutionSerializer.class.getName());
    return factory.messageExecutionProducer(props) ;
  }

  private Runnable textRunner = () -> {
    int count = KafkaConstants.MESSAGE_COUNT;
    MessageTextProducer producer = createTextProducer();
    while(count > 0) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      sendTextMessage(producer, count);
      count--;
    }
  };

  public void sendTextMessage(MessageTextProducer producer, int count) {
    Message message = new Message()
        .withBody(Text.builder().text("message "+count+" "+new Date()).build())
        .withId(getId())
        .withToken("xoxb-252026281427-rgXJt774B2Yp3LDqzJbXokYk")
        .withChannel("C9VS20DNH")
        .withType("Text")
        .withSender("test");
    producer.produce(message, "text_topic");

  }

  private Runnable executionRunner = () -> {
    int count = KafkaConstants.MESSAGE_COUNT;
    MessageExecutionProducer producer = createExecutionProducer();
    while(count > 0) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      sendExecutionMessage(producer, count);
      count--;
    }
  };

  public void sendExecutionMessage(MessageExecutionProducer producer, int count) {
    Message message = new Message()
        .withBody(Execution.builder().strategy("strategy "+count+" "+new Date()).build())
        .withId(getId())
        .withToken("xoxb-252026281427-rgXJt774B2Yp3LDqzJbXokYk")
        .withChannel("C9VS20DNH")
        .withReceiver("U0XJ3Q1T3")
        .withType("Execution")
        .withSender("test");
    producer.produce(message, "execution_topic");
  }

  private Runnable transactionRunner = () -> {
    int count = KafkaConstants.MESSAGE_COUNT;
    MessageTransactionProducer producer = createTransactionProducer();
    while(count > 0) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      sendTransactionMessage(producer, count);
      count--;
    }
  };

  public void sendTransactionMessage(MessageTransactionProducer producer, int count) {
    Message<Transaction> message = new Message<Transaction>()
        .withId(getId())
        .withBody(Transaction.builder().command("command "+count+" "+new Date()).build())
        .withToken("xoxb-252026281427-rgXJt774B2Yp3LDqzJbXokYk")
        .withChannel("C9VS20DNH")
        .withReceiver("U0XJ3Q1T3")
        .withType("Transaction")
        .withSender("test");
    producer.produce(message, "transaction_topic");
  }


  public void run() throws Exception {
    Executor executor = new ThreadPoolExecutor(6, 6, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    executor.execute(textRunner);
    executor.execute(executionRunner);
    executor.execute(transactionRunner);
    Thread.sleep(60000);
  }



}
