package pro.ambulando.slack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;
import pro.ambulando.slack.services.MessageSender;
import pro.ambulando.slack.services.NotificationException;

@RestController
@RequestMapping("/message")
public class NotifierResource {

  @Autowired
  private MessageSender notifier;

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public <T extends MessageBody> SlackNotificationResponse sendStringNotification(@RequestBody Message<T> message ) {
    try {
      return SlackNotificationResponse.of(notifier.sendMessage(message));
    } catch (NotificationException e) {
      return SlackNotificationResponse.error(e.getMessage());
    }
  }


}
