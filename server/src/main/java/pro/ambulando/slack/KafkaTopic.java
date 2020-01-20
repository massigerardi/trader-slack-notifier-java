package pro.ambulando.slack;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
public class KafkaTopic {

  private String[] brokers;

  private String name;

  private String group;

  private String keyDeserializer;

  private String valueDeserializer;

  public String getBrokers() {
    return Arrays.stream(brokers).collect(Collectors.joining(","));
  }

}
