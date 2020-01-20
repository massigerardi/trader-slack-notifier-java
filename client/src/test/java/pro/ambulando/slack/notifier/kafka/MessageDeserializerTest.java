package pro.ambulando.slack.notifier.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class MessageDeserializerTest {

  MessageDeserializer deserializer = new MessageDeserializer();

  @Test
  public void deserializeExecution() throws IOException {
    Message expected = Message.builder().ephemeral(true).channel("channel").sender("sender").receiver("receiver").token("my_token")
        .body(Execution.builder().strategy("run x at y").transaction(Transaction.builder().command("sell").status("Completed").build()).build())
        .build();
    String data = FileUtils.readFileToString(new File("src/test/resources/executionMessage.json"), Charset.defaultCharset());
    Message message = deserializer.deserialize(null, data.getBytes());
    assertEquals(expected, message);
  }

  @Test
  public void deserializeTransaction() throws IOException {
    Message expected = Message.builder().ephemeral(true).channel("channel").sender("sender").receiver("receiver").token("my_token").body(Transaction.builder().command("sell").status("Completed").build()).build();
    String data = FileUtils.readFileToString(new File("src/test/resources/transactionMessage.json"), Charset.defaultCharset());
    Message message = deserializer.deserialize(null, data.getBytes());
    assertEquals(expected, message);
  }

  @Test
  public void deserializeText() throws IOException {
    Message expected = Message.builder().ephemeral(true).channel("channel").sender("sender").receiver("receiver").token("my_token").body(Text.builder().text("test_text").build()).build();
    String data = FileUtils.readFileToString(new File("src/test/resources/textMessage.json"), Charset.defaultCharset());
    Message message = deserializer.deserialize(null, data.getBytes());
    assertEquals(expected, message);
  }


}