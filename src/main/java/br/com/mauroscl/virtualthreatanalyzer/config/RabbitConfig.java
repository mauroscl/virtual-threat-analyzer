package br.com.mauroscl.virtualthreatanalyzer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Value("${whitelist-config.insertion-queue}")
  private String insertionQueue;

  @Value("${whitelist-config.validation-queue}")
  private String validationQueue;

  @Value("${whitelist-config.response-exchange}")
  private String responseExchange;

  @Bean
  Queue insertionQueue() {
    return new Queue(insertionQueue);
  }

  @Bean
  Queue validationQueue() {
    return new Queue(validationQueue);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(responseExchange);
  }

//  @Bean
//  Binding binding( Queue queue, TopicExchange exchange) {
//    return BindingBuilder
//        .bind(queue)
//        .to(exchange)
//        .with(responseRoutingKey);
//  }

//  @Bean
//  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//      MessageListenerAdapter listenerAdapter) {
//    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//    container.setConnectionFactory(connectionFactory);
//    container.setQueueNames(insertionQueue);
//    container.setMessageListener(listenerAdapter);
//    return container;
//  }
//
//  @Bean
//  MessageListenerAdapter listenerAdapter(Receiver receiver) {
//    return new MessageListenerAdapter(receiver, "receiveMessage");
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
}
