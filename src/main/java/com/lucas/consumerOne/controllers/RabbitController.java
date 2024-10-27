package com.lucas.consumerOne.controllers;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@RestController
@RequestMapping("rabbit")
public class RabbitController {

    private static final String RABBITMQ_API_URL = "http://localhost:15672/api/consumers";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

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

    @GetMapping("listerConsumers")
    public ResponseEntity<String> listerAllConsumers() {
        String consumers;
        try {
            consumers = listerConsumers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(consumers);
    }

    private String listerConsumers() throws IOException, InterruptedException {

        HttpResponse<String> response = sendRequestToRabbitMq();

        if (response.statusCode() == 200) {
            System.out.println("consumidores conectados: "+ response.body());
            return response.body();
        }
        else {
            return "nehum consumidor encontrado";
        }

    }

    public HttpResponse<String> sendRequestToRabbitMq() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String auth = USERNAME + ":" + PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RABBITMQ_API_URL))
                .header("Authorization", "Basic " + encodedAuth)
                .GET()
                .build();

//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
