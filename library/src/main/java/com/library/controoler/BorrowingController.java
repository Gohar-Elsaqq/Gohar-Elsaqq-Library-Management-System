package com.library.controoler;

import com.library.dto.BorrowingRecordDTO;
import com.library.entity.BorrowingRecord;
import com.library.exception.ResourceNotFoundException;
import com.library.service.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowingRecordDTO returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingService.returnBook(bookId, patronId);
    }
    @PostMapping("/book/{bookId}/patron/{patronId}")
    public BorrowingRecordDTO borrowBook(@Valid @PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingService.borrowBook(bookId, patronId);
    }

}
