package pro.ambulando.slack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pro.ambulando.slack.notifier.model.Execution;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.Text;
import pro.ambulando.slack.notifier.model.Transaction;
import pro.ambulando.slack.services.MessageSender;
import pro.ambulando.slack.services.NotificationException;

@RestController
@RequestMapping("/message")
public class NotifierResource {

  @Autowired
  private MessageSender notifier;

  @RequestMapping(path = "/text", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public SlackNotificationResponse sendStringNotification(@RequestBody Message<Text> message ) {
    try {
      return SlackNotificationResponse.of(notifier.sendTextMessage(message));
    } catch (NotificationException e) {
      return SlackNotificationResponse.error(e.getMessage());
    }
  }

  @RequestMapping(path = "/execution", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public SlackNotificationResponse sendExecutionMessage(@RequestBody Message<Execution> message ) {
    try {
      return SlackNotificationResponse.of(notifier.sendExecutionMessage(message));
    } catch (NotificationException e) {
      return SlackNotificationResponse.error(e.getMessage());
    }
  }

  @RequestMapping(path = "/transaction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public SlackNotificationResponse sendTransactionMessage(@RequestBody Message<Transaction> message ) {
    try {
      return SlackNotificationResponse.of(notifier.sendTransactionMessage(message));
    } catch (NotificationException e) {
      return SlackNotificationResponse.error(e.getMessage());
    }
  }


}
