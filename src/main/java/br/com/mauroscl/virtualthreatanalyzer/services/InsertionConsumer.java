package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InsertionConsumer {

  private static final Logger logger = Logger.getLogger(InsertionConsumer.class.getName());

  @RabbitListener(queues = {"${whitelist-config.insertion-queue}"})
  public void receive(WhiteListRule rule) {
    logger.info("nova regra: " + rule.toString());
  }

//  public void receive(@Payload String order) {
//    logger.info("Order: " + order);
//  }

}
