package com.library.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronDTO {
    private Long id;

    @Valid
    private ContactInfoDTO contactInformation;

    @NotNull(message = "Name is required ")
    @NotBlank
    @Size(min = 7, max = 250, message = "Name must be between 7 and 250 characters")
    private String name;


}