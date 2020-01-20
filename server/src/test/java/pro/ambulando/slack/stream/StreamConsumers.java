package pro.ambulando.slack.stream;

import org.junit.Test;

public class StreamConsumers {

  @Test
  public void testStream() throws Exception {
    ProducerRunner runner = new ProducerRunner();
    runner.run();
  }

  @Test
  public void testExecutionSingleMessage() throws Exception {
    ProducerRunner runner = new ProducerRunner();
    runner.sendExecutionMessage(runner.createExecutionProducer(), 0);
  }

  @Test
  public void testTransactionSingleMessage() throws Exception {
    ProducerRunner runner = new ProducerRunner();
    runner.sendTransactionMessage(runner.createTransactionProducer(), 0);
  }

  @Test
  public void testSingleTextMessage() throws Exception {
    ProducerRunner runner = new ProducerRunner();
    runner.sendTextMessage(runner.createTextProducer(), 0);
  }

}
