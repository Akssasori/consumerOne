package com.lucas.consumerOne.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /*
    conversor de mensagem personalizado
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
    Se não adicionar essa configuração ele irá funcionar porque o spring amqp irá criar um por padrão e dessa
    forma não conseguimos garantir que ele irá usar o conversor de mensagem que escolhemos, nesse caso o Jackson2JsonMessageConverter
    */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
//        factory.setConsumerTagStrategy(queue -> "applicationName" + "_" + UUID.randomUUID());
        return factory;
    }

    /*
    dar um nome especifico a tag do consumidor, assim podemos ver qual app é o consumidor
     */
    @Bean
    ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer(@Value("${spring.application.name}") String applicationName) {
        return container -> container.setConsumerTagStrategy(queue -> applicationName + "_" + UUID.randomUUID());
    }


//    @Bean
//    public Exchange myExchange() {
//        return ExchangeBuilder.topicExchange(exchange).durable(true).build();
//    }
//
//    @Bean
//    public Queue createQueues() {
//        return QueueBuilder.durable(queue).build();
//
//    }
//
//    @Bean
//    public Binding binding(Queue myQueue, Exchange myExchange) {
//        return BindingBuilder.bind(myQueue)
//                .to(myExchange)
//                .with(routing_key)
//                .noargs();
//    }

}
