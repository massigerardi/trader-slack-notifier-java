package pro.ambulando.slack.services;

import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.ambulando.slack.notifier.model.Message;
import pro.ambulando.slack.notifier.model.MessageBody;

import java.io.IOException;

@Service
public class NotifierService {

  private SessionFactory sessionFactory;

  public NotifierService(@Autowired SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public <T extends MessageBody> SlackMessageReply sendMessage(Message<T> message, SlackPreparedMessage preparedMessage) throws MessageException, IOException, SlackMessageException {
    if (StringUtils.isBlank(message.getToken())) {
      throw new MessageException("no_token_specified");
    }
    if (message.getBody() == null) {
      throw new MessageException("empty_message");
    }
    if (StringUtils.isBlank(message.getReceiver()) && StringUtils.isBlank(message.getChannel())) {
      throw new MessageException("no_receiver_or_channel_specified");
    }
    if (message.getEphemeral()) {
      return sendEphemeralMessage(message, preparedMessage);
    }
    if (StringUtils.isBlank(message.getReceiver())) {
      return sendMessageToChannel(message, preparedMessage);
    }
    return sendMessageToUser(message, preparedMessage);
  }

  private <T extends MessageBody> SlackMessageReply sendEphemeralMessage(Message<T> message, SlackPreparedMessage preparedMessage) throws IOException, SlackMessageException {
    SlackSession session = sessionFactory.getSession(message.getToken());
    String username = session.findUserById(message.getReceiver()).getUserName();
    return check(session.sendEphemeralMessage(message.getChannel(), username, preparedMessage));
  }

  private <T extends MessageBody> SlackMessageReply sendMessageToUser(Message<T> message, SlackPreparedMessage preparedMessage) throws IOException, SlackMessageException {
    SlackSession session = sessionFactory.getSession(message.getToken());
    String username = session.findUserById(message.getReceiver()).getUserName();
    return check(session.sendMessageToUser(username, preparedMessage));
  }

  private <T extends MessageBody> SlackMessageReply sendMessageToChannel(Message<T> message, SlackPreparedMessage preparedMessage) throws IOException, SlackMessageException {
    return check(sessionFactory.getSession(message.getToken()).sendMessage(message.getChannel(), preparedMessage));
  }

  private SlackMessageReply check(SlackMessageHandle<SlackMessageReply> handle) throws SlackMessageException {
    SlackMessageReply reply = handle.getReply();
    if (reply.isOk()) return reply;
    throw new SlackMessageException(reply.getErrorMessage());

  }



}
