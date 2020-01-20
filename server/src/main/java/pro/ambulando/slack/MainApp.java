package pro.ambulando.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper;
  }
}
