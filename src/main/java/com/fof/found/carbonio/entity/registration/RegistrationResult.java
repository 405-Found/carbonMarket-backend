package com.fof.found.carbonio.entity.registration;

import lombok.Data;

@Data
public class RegistrationResult {
    private boolean isRegistered;
    private String responseInformation;

    public RegistrationResult(boolean isRegistered, String responseInformation) {
        this.isRegistered = isRegistered;
        this.responseInformation = responseInformation;
    }
}
