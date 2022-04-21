package com.fof.found.carbonio.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class DonationRecord {
    private UUID userID;
    private float amount;
    private String targetCharityOrganization;
    private String sponsorCompany;
}
