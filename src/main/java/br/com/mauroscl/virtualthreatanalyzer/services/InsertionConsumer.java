package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InsertionConsumer {

  private static final Logger logger = Logger.getLogger(InsertionConsumer.class.getName());

  private final WhiteListRuleRepository repository;

  public InsertionConsumer(final WhiteListRuleRepository repository) {
    this.repository = repository;
  }

  @RabbitListener(queues = {"${vta-config.insertion-queue}"})
  public void receive(WhiteListRule rule) {
    logger.info("nova regra: " + rule.toString());
    repository.save(rule);
  }
}
