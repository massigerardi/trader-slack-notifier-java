package pro.ambulando.slack.notifier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(of = "strategy")
public class Execution implements MessageBody {

  private String strategy;

  @Singular
  private List<Transaction> transactions;

}
