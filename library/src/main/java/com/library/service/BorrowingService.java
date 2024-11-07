package com.library.service;


import com.library.dto.BorrowingRecordDTO;
import com.library.entity.Book;
import com.library.entity.BorrowingRecord;
import com.library.entity.Patron;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.BorrowingRecordMapper;
import com.library.repository.BookRepository;
import com.library.repository.BorrowingRecordRepository;
import com.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowingRecordMapper borrowingRecordMapper;
    public BorrowingRecordDTO borrowBook(Long bookId, Long patronId) {

        Optional<BorrowingRecord> existingRecord = borrowingRecordRepository.findByPatronAndBook(patronId, bookId);
        if (existingRecord.isPresent()) {
            throw new IllegalStateException("This book has already been borrowed by the patron.");
        }
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found"));
        // Create a new borrowing record and save it to the database
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setReturnDate(null);
        BorrowingRecord savedBorrowingRecord = borrowingRecordRepository.save(borrowingRecord);

        return borrowingRecordMapper.toDTO(savedBorrowingRecord);
    }

    public BorrowingRecordDTO returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("No active borrowing record found for this book and patron"));

        borrowingRecord.setReturnDate(LocalDate.now());

        // Save the updated borrowing record
        BorrowingRecord updatedBorrowingRecord = borrowingRecordRepository.save(borrowingRecord);

        return borrowingRecordMapper.toDTO(updatedBorrowingRecord);
    }
}