package com.roczyno.springbootecommerceapi.response;



public record PaymentResponse(
		boolean status,
		String message,
		Data data
) {
	public record Data(
			String authorization_url,
			String access_code,
			String reference
	) {}
}
