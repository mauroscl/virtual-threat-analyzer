package br.com.mauroscl.virtualthreatanalyzer.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

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

  @Bean
  RabbitListenerErrorHandler listenerErrorHandler() {
    return new RabbitListenerErrorHandler() {
      @Override
      public Object handleError(final Message message,
          final org.springframework.messaging.Message<?> message1,
          final ListenerExecutionFailedException e) {
        throw new AmqpRejectAndDontRequeueException(e);
      }
    };
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

    final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2);
    final RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(simpleRetryPolicy);

    rabbitTemplate.setRetryTemplate(retryTemplate);

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
