package pro.ambulando.slack;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka")
@Component
public class KafkaConfiguration {

  private List<KafkaTopic> topics;

}
