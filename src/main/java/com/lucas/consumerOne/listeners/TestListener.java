package com.lucas.consumerOne.listeners;

import com.lucas.consumerOne.dtos.TransactionDTO;
import com.lucas.consumerOne.dtos.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public final class TestListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Value("${spring.application.name}")
    private String appName;

//        defini somente a fila que ele irá comsumir
//        @RabbitListener(
//            queues = {"${rabbit.config.queue.shopping}"})

//  aqui eu crio a fila, a exchange, e o binding diratamente na anotação. definimo a estrutura completa
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${rabbit.config.queue.shopping}", durable = "true"),
            exchange = @Exchange(value = "${rabbit.config.exchange.shopping}", type = ExchangeTypes.TOPIC),
            key = "${rabbit.config.routing.shopping}"
    ), id = "appName", autoStartup = "true")
    public void consumerApprovedQueue(@Payload final TransactionDTO transactionDTO) {
        System.out.println("*********" + appName);
        System.out.println("************* enviando para o gc two ****************");
        log.info(transactionDTO.getName());

    }

}
