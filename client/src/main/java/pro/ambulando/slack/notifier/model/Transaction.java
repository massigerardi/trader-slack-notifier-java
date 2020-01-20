package pro.ambulando.slack.notifier.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString(of = "command")
public class Transaction implements MessageBody {

  private String orderId;

  private String userId;

  private String currencyPair;

  private Long created;

  private Long closed;

  private Double volume;

  private Double price;

  private Double askingPrice;

  private Double cost;

  private Double fees;

  private Double feesPercentage;

  private Integer index;

  private String description;

  private String exchange;

  private String command;

  private String status;

}
