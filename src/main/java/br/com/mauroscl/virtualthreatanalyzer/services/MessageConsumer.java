package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  private static final Logger logger = Logger.getLogger(MessageConsumer.class.getName());

  //private final MessageProducer producer;

  /*public MessageConsumer(final MessageProducer producer) {
    this.producer = producer;
  }*/

  @RabbitListener(queues = {"${whitelist-config.insertion-queue.name}"})
//  public void receive(@Payload String order) {
//    logger.info("Order: " + order);
//  }
  public void receive(WhiteListRule rule) {
    logger.info(rule.toString());
    //producer.send(new UrlValidationResponse(rule.getRegex()));
  }
}
