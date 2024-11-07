package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BorrowingRecordDTO {
    private Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private PatronDTO patron;
}