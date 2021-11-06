package com.evgeniy638.documents.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FileInfoDTO {
    private UUID uuid;
    private String name;
    private String url;
    private String date;
    private String creator;
}
