package br.com.mauroscl.virtualthreatanalyzer.services;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class MessageProducer {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  @Qualifier("validationQueue")
  private Queue queue;

  public void send(UrlValidationResponse response) {
    rabbitTemplate.convertAndSend(this.queue.getName(), response);
  }
}
