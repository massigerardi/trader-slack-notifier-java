package pro.ambulando.slack.notifier.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class MessageSerializerTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void serializeExecution() throws IOException {
    Message message = Message.builder().ephemeral(true).channel("channel").sender("sender").receiver("receiver").token("my_token")
        .body(Execution.builder().strategy("run x at y").transaction(Transaction.builder().command("sell").status("Completed").build()).build())
        .build();
    String expected = FileUtils.readFileToString(new File("src/test/resources/executionMessage.json"), Charset.defaultCharset());
    String value = mapper.writeValueAsString(message);
    assertEquals(mapper.readTree(expected), mapper.readTree(value));
  }

  @Test
  public void serializeText() throws IOException {
    Message message = Message.builder().ephemeral(true).channel("channel").sender("sender").receiver("receiver").token("my_token").body(Text.builder().text("test_text").build()).build();
    String expected = FileUtils.readFileToString(new File("src/test/resources/textMessage.json"), Charset.defaultCharset());
    String value = mapper.writeValueAsString(message);
    assertEquals(mapper.readTree(expected), mapper.readTree(value));
  }

  @Test
  public void serializeTransaction() throws IOException {
    Message message = Message.builder().ephemeral(true).channel("channel").sender("sender").receiver("receiver").token("my_token").body(Transaction.builder().command("sell").status("Completed").build()).type("Transaction").build();
    String expected = FileUtils.readFileToString(new File("src/test/resources/transactionMessage.json"), Charset.defaultCharset());
    String value = mapper.writeValueAsString(message);
    assertEquals(mapper.readTree(expected), mapper.readTree(value));
  }


}