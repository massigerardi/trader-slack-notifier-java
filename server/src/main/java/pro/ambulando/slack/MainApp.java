package pro.ambulando.slack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pro.ambulando.slack.services.SessionFactory;

@SpringBootApplication
@ComponentScan({
    "pro.ambulando"
})
public class MainApp {

  public static void main(final String[] args) {
    SpringApplication.run(MainApp.class);
  }

  @Bean
  public SessionFactory sessionFactory(){
    return new SessionFactory();
  }
}
