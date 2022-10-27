package com.lucas.consumerOne.listeners;

import com.lucas.consumerOne.client.ClientGcTwo;
import com.lucas.consumerOne.dtos.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class TestListener {

    private final ClientGcTwo clientGcTwo;

    @RabbitListener(
            queues = {"${rabbitmq.queue.test}"})
    public void consumerApprovedQueue(@Payload final UserRequestDTO userRequestDTO) {

        System.out.println("************* enviando para o gc two ****************");

        clientGcTwo.sendUser(userRequestDTO);

    }

}
