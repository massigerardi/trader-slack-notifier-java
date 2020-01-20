package pro.ambulando.slack.notifier.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MessageDeserializerTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void deserializeExecution() throws Exception{
    Message<Execution> message = mapper.readValue(new File("src/test/resources/executionMessage.json"), Message.class);
    assertNotNull(message);
    assertTrue(message.getBody() instanceof Execution);
  }

  @Test
  public void deserializeText() throws Exception{
    Message<Text> message = mapper.readValue(new File("src/test/resources/textMessage.json"), Message.class);
    assertNotNull(message);
    assertTrue(message.getBody() instanceof Text);
  }

  @Test
  public void deserializeTransaction() throws Exception{
    Message<Transaction> message = mapper.readValue(new File("src/test/resources/transactionMessage.json"), Message.class);
    assertNotNull(message);
    assertTrue(message.getBody() instanceof Transaction);
  }

  @Test(expected = IOException.class)
  public void deserializeUnknown() throws Exception{
    mapper.readValue(new File("src/test/resources/errorMessage.json"), Message.class);
  }


}