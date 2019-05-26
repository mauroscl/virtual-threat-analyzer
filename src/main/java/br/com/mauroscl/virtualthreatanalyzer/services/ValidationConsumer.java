package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidationConsumer {

  private static final Logger logger = Logger.getLogger(ValidationConsumer.class.getName());

  private final RabbitTemplate rabbitTemplate;
  private final UrlValidationService urlValidationService;

  @Value("${vta-config.response-exchange}")
  private String responseExchange;

  @Value("${vta-config.response-routing-key}")
  private String responseRoutingKey;

  public ValidationConsumer(final RabbitTemplate rabbitTemplate,
      final UrlValidationService urlValidationService) {
    this.rabbitTemplate = rabbitTemplate;
    this.urlValidationService = urlValidationService;
  }

  @RabbitListener(queues = {"${vta-config.validation-queue}"},concurrency = "${vta-config.number-of-validation-consumers}")
  public void receive(ValidationCommand command) {
    //logger.info("url para validação: " + command.toString());
    final UrlValidationResponse urlValidationResponse = urlValidationService.validar(command);
    //logger.info("resultado da validação: " + urlValidationResponse.toString());
    rabbitTemplate.convertAndSend(responseExchange, responseRoutingKey, urlValidationResponse);
  }

}
