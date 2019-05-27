package br.com.mauroscl.virtualthreatanalyzer.services;

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
    whiteListRuleService.salvar(rule);
  }
}
