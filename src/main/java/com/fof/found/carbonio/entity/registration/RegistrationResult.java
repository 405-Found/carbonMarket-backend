package com.fof.found.carbonio.entity.registration;

import lombok.Data;

@Data
public class RegistrationResult {
    private boolean isRegistered;
    private String responseInformation;
    private String token;

    public RegistrationResult(boolean isRegistered, String responseInformation,String token) {
        this.isRegistered = isRegistered;
        this.responseInformation = responseInformation;
        this.token = token;
    }
}
