package com.example.payment_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaymentGatewayClient {
    private final WebClient webClient;

    public PaymentGatewayClient(WebClient.Builder webClientBuilder, @Value("${payment.gateway.url}") String gatewayUrl) {
        this.webClient = webClientBuilder.baseUrl(gatewayUrl).build();
    }

    public Mono<String> processPayment(String studentId, Double amount, String courseId) {
        // Mock payment processing
        return Mono.just("txn_" + System.currentTimeMillis()); // Simulate transaction ID
        // For real gateway (e.g., Stripe), replace with:
        // return webClient.post()
        //         .uri("/payments")
        //         .bodyValue(new PaymentRequest(studentId, amount, courseId))
        //         .retrieve()
        //         .bodyToMono(PaymentResponse.class)
        //         .map(PaymentResponse::getTransactionId);
    }
}
