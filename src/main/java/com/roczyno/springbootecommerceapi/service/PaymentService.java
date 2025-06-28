package com.roczyno.springbootecommerceapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roczyno.springbootecommerceapi.request.PaymentRequest;
import com.roczyno.springbootecommerceapi.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {
	@Value("${spring.application.payStack.secretKey}")
	private String payStackSecret;

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public PaymentResponse initializeTransaction(PaymentRequest paymentRequest) {
		String url = "https://api.paystack.co/transaction/initialize";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + payStackSecret);
		headers.set("Content-Type", "application/json");

		HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			return objectMapper.readValue(responseEntity.getBody(), PaymentResponse.class);
		} catch (HttpClientErrorException ex) {
			throw new RuntimeException("Error initializing transaction: " + ex.getResponseBodyAsString());
		} catch (Exception ex) {
			throw new RuntimeException("Error initializing transaction", ex);
		}
	}

	public String verifyTransaction(String reference) {
		String url = "https://api.paystack.co/transaction/verify/" + reference;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + payStackSecret);

		HttpEntity<Void> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			return responseEntity.getBody();
		} catch (HttpClientErrorException ex) {
			throw new RuntimeException("Error verifying transaction: " + ex.getResponseBodyAsString());
		} catch (Exception ex) {
			throw new RuntimeException("Error verifying transaction", ex);
		}
	}
}
