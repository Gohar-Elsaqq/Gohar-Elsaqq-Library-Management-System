package com.library.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class BookDTO {
    private Long id;
    @NotNull(message = "Title is required")
    @Size(min = 3, max = 250, message = "Name must be between 3 and 250 characters")
    private String title;
    @NotNull(message = "Author is required")
    private String author;
    @NotNull(message = "PublicationYear is required")
    private int publicationYear;
    @NotNull(message = "Isbn is required")
    private String isbn;
    private List<BorrowingRecordDTO> borrowingRecords;
}