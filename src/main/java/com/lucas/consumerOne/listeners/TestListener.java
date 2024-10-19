package com.lucas.consumerOne.listeners;

import com.lucas.consumerOne.dtos.TransactionDTO;
import com.lucas.consumerOne.dtos.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public final class TestListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @RabbitListener(
            queues = {"${rabbit.config.queue.shopping}"})
    public void consumerApprovedQueue(@Payload final TransactionDTO transactionDTO) {

        System.out.println("************* enviando para o gc two ****************");
        log.info(transactionDTO.getName());

    }

}
