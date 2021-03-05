package com.ssafy.trip.help;

import javax.validation.constraints.NotBlank;

public class AuthSearchRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String name;
    


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
