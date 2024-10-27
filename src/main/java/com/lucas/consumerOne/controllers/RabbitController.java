package com.lucas.consumerOne.controllers;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rabbit")
public class RabbitController {


    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;


    public RabbitController(RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry) {
        this.rabbitListenerEndpointRegistry = rabbitListenerEndpointRegistry;
    }

    @GetMapping("stoListener")
    public ResponseEntity<String> stopListeners(@RequestParam String listenerId) {
        rabbitListenerEndpointRegistry.getListenerContainer(listenerId).stop();
        System.out.println("Consumidor com ID " + listenerId + " foi parado.");
        return ResponseEntity.ok().body("success when stop " + listenerId);
    }

    @GetMapping("startListener")
    public ResponseEntity<String> startListener(@RequestParam String listenerId) {
        rabbitListenerEndpointRegistry.getListenerContainer(listenerId).start();
        System.out.println("Consumidor com ID " + listenerId + " foi inicido.");
        return ResponseEntity.ok().body("success when start " + listenerId);
    }
}
