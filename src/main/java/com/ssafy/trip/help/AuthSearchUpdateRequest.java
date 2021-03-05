package com.ssafy.trip.help;

import javax.validation.constraints.NotBlank;

public class AuthSearchUpdateRequest {

    
    @NotBlank
    private String code;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank
    private String password;

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


    
}
