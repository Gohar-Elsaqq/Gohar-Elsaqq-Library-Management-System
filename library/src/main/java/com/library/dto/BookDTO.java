package com.library.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min = 3, max = 250, message = "Name must be between 3 and 250 characters")
    private String title;

    @NotNull(message = "Author is required")
    @NotBlank
    private String author;

    @NotNull(message = "Isbn is required")
    @NotBlank
    private String isbn;

    @NotNull(message = "PublicationYear is required")
    @Digits(integer = 4, fraction = 0, message = "Publication year must be a 4-digit number")
    @Min(value = 1000, message = "Publication year must be greater than or equal to 1000")
    @Max(value = 9999, message = "Publication year must be less than or equal to 9999")
    private int publicationYear;

    private List<BorrowingRecordDTO> borrowingRecords;
}