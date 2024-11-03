package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {
    private Long id;

    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private List<BorrowingRecordDTO> borrowingRecords;
}