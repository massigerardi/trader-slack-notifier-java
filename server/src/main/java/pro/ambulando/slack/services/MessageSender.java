package pro.ambulando.slack.services;

import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.blocks.Context;
import com.ullink.slack.simpleslackapi.blocks.Section;
import com.ullink.slack.simpleslackapi.blocks.compositions.Markdown;
import com.ullink.slack.simpleslackapi.blocks.compositions.PlainText;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;
import lombok.AllArgsConstructor;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class MessageSender {

  @Autowired
  @With private NotifierService notifier;

  public SlackMessageReply sendExecutionMessage(Message<Execution> message) throws NotificationException {
    SlackPreparedMessage slackMessage = prepareExecutionMessage(message);
    return sendMessage(message, slackMessage);
  }

  public SlackMessageReply sendTransactionMessage(Message<Transaction> message) throws NotificationException {
    SlackPreparedMessage slackMessage = prepareTransactionMessage(message);
    return sendMessage(message, slackMessage);
  }

  public SlackMessageReply sendTextMessage(Message<Text> message) throws NotificationException {
    SlackPreparedMessage slackMessage = prepareTextMessage(message);
    return sendMessage(message, slackMessage);
  }

  public <T extends MessageBody> SlackMessageReply sendMessage(Message<T> message, SlackPreparedMessage preparedMessage) throws NotificationException {
    log.info("MG Sending Message {}", message.getBody());
    try {
        SlackMessageReply reply = notifier.sendMessage(message, preparedMessage);
        return reply;
    } catch (SlackMessageException | IOException | MessageException e) {
      log.error("sending message", e);
      throw new NotificationException(e.getMessage(), e);
    }
  }

  private SlackPreparedMessage prepareTransactionMessage(Message<Transaction> message) {
    Transaction body = message.getBody();
    SlackPreparedMessage preparedMessage = SlackPreparedMessage.builder()
        .block(Section.builder().field(PlainText.builder().text(message.getBody().getCommand()).build()).build())
        .block(Context.builder().element(Markdown.builder().text(message.getId()+" sent by Java").build()).build())
        .build();
    return preparedMessage;
  }

  private SlackPreparedMessage prepareTextMessage(Message<Text> message) {
    SlackPreparedMessage preparedMessage = SlackPreparedMessage.builder()
        .block(Section.builder().field(PlainText.builder().text(message.getBody().getText()).build()).build())
        .block(Context.builder().element(Markdown.builder().text(message.getId()+" sent by Java").build()).build())
        .build();
    return preparedMessage;
  }

  private SlackPreparedMessage prepareExecutionMessage(Message<Execution> message) {
    SlackPreparedMessage preparedMessage = SlackPreparedMessage.builder()
        .block(Section.builder().field(PlainText.builder().text(message.getBody().getStrategy()).build()).build())
        .block(Context.builder().element(Markdown.builder().text(message.getId()+" sent by Java").build()).build())
        .build();
    return preparedMessage;
  }

}
