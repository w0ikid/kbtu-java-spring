package com.example.demo.pet.app;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class PetExternalService {
    private final WebClient webClient = WebClient.create("https://dog.ceo");

    public String getRandomDogImage() {
        return webClient.get()
            .uri("/api/breeds/image/random")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
