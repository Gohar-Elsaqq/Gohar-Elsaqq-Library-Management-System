package com.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoDTO {
    @Email(message = "email is required")
    private String email;
    @NotNull(message = "Address is required ")
    private String address;
    @NotNull(message = "Phone is required ")
    private String phone;
    private String bin;
}