package com.evgeniy638.documents.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}
