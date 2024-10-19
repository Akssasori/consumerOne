package com.lucas.consumerOne.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Value("${rabbit.config.exchange.shopping}")
    private String exchange;

    @Value("${rabbit.config.routing.shopping}")
    private String routing_key;

    @Value("${rabbit.config.queue.shopping}")
    private String queue;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange myExchange() {
        return ExchangeBuilder.topicExchange(exchange).durable(true).build();
    }

    @Bean
    public Queue createQueues() {
        return QueueBuilder.durable(queue).build();

    }

    @Bean
    public Binding binding(Queue myQueue, Exchange myExchange) {
        return BindingBuilder.bind(myQueue)
                .to(myExchange)
                .with(routing_key)
                .noargs();
    }

}
