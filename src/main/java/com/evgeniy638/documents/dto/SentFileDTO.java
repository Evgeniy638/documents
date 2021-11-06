package com.evgeniy638.documents.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SentFileDTO {
    private UUID uuid;
    private String url;
    private String date;
    private String name;
    private String listInstitute;
    private String listGroups;
    private String listUsers;
}
