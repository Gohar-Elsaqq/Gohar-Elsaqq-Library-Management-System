package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronDTO {
    private Long id;
    @NotNull(message = "Name is required ")
    private String name;
    @NotNull(message = "ContactInformation is required ")
    @NotBlank
    private ContactInfoDTO contactInformation;

}