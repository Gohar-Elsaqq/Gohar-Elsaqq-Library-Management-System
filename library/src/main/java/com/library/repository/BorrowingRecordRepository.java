package com.library.repository;


import com.library.entity.BorrowingRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE librarymanagementsystem.borrowing_record SET book_id = NULL WHERE book_id =:bookId", nativeQuery = true)
    void deleteByBookId(@Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM librarymanagementsystem.borrowing_record WHERE patron_id = :patronId", nativeQuery = true)
    void deleteByPatronId(@Param("patronId") Long patronId);
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);

    @Query("SELECT br FROM BorrowingRecord br WHERE br.patron.id = :patronId AND br.book.id = :bookId")
    Optional<BorrowingRecord> findByPatronAndBook(Long patronId, Long bookId);

}