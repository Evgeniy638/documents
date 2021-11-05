package com.evgeniy638.documents.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileDTO {
    private List<String> institutionTitles;
    private List<String> groupTitles;
    private List<String> usernames;
    private List<MultipartFile> files;
}
