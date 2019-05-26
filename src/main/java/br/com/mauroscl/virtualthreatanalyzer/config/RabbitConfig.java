package br.com.mauroscl.virtualthreatanalyzer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

  private static final String DEAD_LETTER_QUEUE = "dead-letter-queue";
  private static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";

  @Value("${vta-config.insertion-queue}")
  private String insertionQueue;

  @Value("${vta-config.validation-queue}")
  private String validationQueue;

  @Value("${vta-config.response-exchange}")
  private String responseExchange;

  @Value("${vta-config.response-routing-key}")
  private String responseRoutingKey;

  @Bean
  Queue deadLetterQueue() {
    return new Queue(DEAD_LETTER_QUEUE);
  }

  @Bean
  Queue insertionQueue() {
    return new Queue(insertionQueue, true,false, false, generateDeadLetterConfig());
  }

  @Bean
  Queue validationQueue() {
    return new Queue(validationQueue, true,false, false, generateDeadLetterConfig());
  }

//  @Bean
//  Queue responseQueue() {
//    return new Queue(responseQueue);
//  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(responseExchange);
  }

  @Bean
  TopicExchange deadLetterExchange() {
    return new TopicExchange(DEAD_LETTER_EXCHANGE);
  }

  @Bean
  Binding bindingDeadLetter(Queue deadLetterQueue, TopicExchange deadLetterExchange)
  {
    return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_QUEUE);
  }

//  @Bean
//  SimpleMessageListenerContainer validationContainer(ConnectionFactory connectionFactory/*,
//      MessageListenerAdapter validationListenerAdapter*/) {
//    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//    container.setConnectionFactory(connectionFactory);
//    container.setQueueNames(validationQueue);
//    //container.setConcurrentConsumers(numberOfValidationConsumers);
//    //container.setMessageListener(validationListenerAdapter);
//    return container;
//  }

//  @Bean
//  MessageListenerAdapter validationListenerAdapter(ValidationConsumer validationConsumer) {
//    return new MessageListenerAdapter(validationConsumer, "receive");
//  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  private Map<String, Object> generateDeadLetterConfig() {

    Map<String, Object> args = new HashMap<>();
    args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
    args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE);

    return args;
  }
}
