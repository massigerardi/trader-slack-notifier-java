package pro.ambulando.slack.streams;

import org.junit.Test;

public class StreamConsumersIgnore {

  @Test
  public void testStream() throws Exception {
    ProducerRunnerIgnore runner = new ProducerRunnerIgnore();
    runner.run();
  }

  @Test
  public void testExecutionSingleMessage() throws Exception {
    ProducerRunnerIgnore runner = new ProducerRunnerIgnore();
    runner.sendExecutionMessage(runner.createExecutionProducer(), 0);
  }

  @Test
  public void testTransactionSingleMessage() throws Exception {
    ProducerRunnerIgnore runner = new ProducerRunnerIgnore();
    runner.sendTransactionMessage(runner.createTransactionProducer(), 0);
  }

  @Test
  public void testSingleTextMessage() throws Exception {
    ProducerRunnerIgnore runner = new ProducerRunnerIgnore();
    runner.sendTextMessage(runner.createTextProducer(), 0);
  }

}
