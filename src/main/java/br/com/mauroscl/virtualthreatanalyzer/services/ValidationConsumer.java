package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ValidationConsumer {

  private static final Logger logger = Logger.getLogger(ValidationConsumer.class.getName());

  private final MessageProducer producer;

  public ValidationConsumer(final MessageProducer producer) {
    this.producer = producer;
  }

  @RabbitListener(queues = {"${whitelist-config.validation-queue}"})
  public void receive(ValidationCommand command) {
    logger.info("nova validação: " + command.toString());
    producer.send(new UrlValidationResponse(command.getCorrelationId()));
  }

//  public void receive(@Payload String order) {
//    logger.info("Order: " + order);
//  }

}
