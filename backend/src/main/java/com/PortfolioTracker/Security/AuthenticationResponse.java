package com.PortfolioTracker.Security;

public class AuthenticationResponse {
    private final String jwt_token;

    public AuthenticationResponse(String jwt_token){
        this.jwt_token = jwt_token;
    }

    public String getJwtToken(){
        return this.jwt_token;
    }
}
