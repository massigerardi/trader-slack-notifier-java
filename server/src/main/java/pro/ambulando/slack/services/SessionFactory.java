package pro.ambulando.slack.services;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import pro.ambulando.slack.notifier.model.SlackUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SessionFactory {

  Map<String, SlackSession> sessionMap = new HashMap<String, SlackSession>();

  public SlackSession getSession(String token) throws IOException {
    if (!sessionMap.containsKey(token)) {
      SlackUser bot = getBot(token);
      SlackSession session = SlackSessionFactory.createWebSocketSlackSession(bot.getToken());
      session.connect();
      sessionMap.put(token, session);
    }
    return sessionMap.get(token);
  }

  private SlackUser getBot(String token) {
    return SlackUser.builder().token(token).build();
  }

}
