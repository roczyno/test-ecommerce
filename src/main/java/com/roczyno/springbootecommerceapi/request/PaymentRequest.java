package com.roczyno.springbootecommerceapi.request;



public record PaymentRequest(
		String email,
		String amount
) {
}
