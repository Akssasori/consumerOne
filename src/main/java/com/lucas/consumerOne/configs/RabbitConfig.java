package com.lucas.consumerOne.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.exchange.producer}")
    private String exchangeProducerName;

    @Value("${rabbitmq.route.test}")
    private String routeTestName;

    @Value("${rabbitmq.queue.test}")
    private String queueTestName;


    @Bean
    public TopicExchange exchangeProducer() {
        return new TopicExchange(exchangeProducerName);
    }


    @Bean
    public Queue queueTest() {
        return new Queue(queueTestName, true);
    }


    @Bean
    public Binding bindingTest(
            final Queue queueTest,
            final TopicExchange exchangeProducer) {
        return BindingBuilder.bind(queueTest).to(
                exchangeProducer).with(
                routeTestName);
    }

    /**
     * Json message converter.
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
