package com.library.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BorrowingRecordDTO {
    private Long id;

    @FutureOrPresent(message = "The return date must be in the future or today.")
    private LocalDate returnDate;

    @NotBlank
    @PastOrPresent(message ="The date of the borrowing must be in the past or today.")
    private LocalDate borrowDate;

    private PatronDTO patron;
}