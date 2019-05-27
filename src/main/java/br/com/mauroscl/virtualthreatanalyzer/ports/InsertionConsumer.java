package br.com.mauroscl.virtualthreatanalyzer.ports;

import br.com.mauroscl.virtualthreatanalyzer.model.WhiteListRule;
import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRuleService;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InsertionConsumer {

  private static final Logger logger = Logger.getLogger(InsertionConsumer.class.getName());

  private final WhiteListRuleService whiteListRuleService;

  public InsertionConsumer(final WhiteListRuleService whiteListRuleService) {
    this.whiteListRuleService = whiteListRuleService;
  }

  @RabbitListener(queues = {"${vta-config.insertion-queue}"}, errorHandler = "listenerErrorHandler")
  public void receive(WhiteListRule rule) {
    logger.info("nova regra: " + rule.toString());
    whiteListRuleService.createNew(rule);
  }
}
