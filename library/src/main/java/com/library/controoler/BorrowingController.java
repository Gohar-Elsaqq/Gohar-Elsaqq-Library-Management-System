package com.library.controoler;

import com.library.dto.BorrowingRecordDTO;
import com.library.entity.BorrowingRecord;
import com.library.exception.ResourceNotFoundException;
import com.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/book/{bookId}/patron/{patronId}")
    public BorrowingRecordDTO borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingService.borrowBook(bookId, patronId);
    }
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowingRecordDTO returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingService.returnBook(bookId, patronId);
    }
}
