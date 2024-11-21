package com.library.service;

import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.repository.BorrowingRecordRepository;
import com.library.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    //add book
    @CacheEvict(value = "saveBook", key = "#saveBook")
    public BookDTO saveBook(BookDTO bookDTO) {
        try {
            if (bookDTO.getId() != null) {
                var bookId = bookRepository.findById(bookDTO.getId());
                if (bookId.isPresent()) {
                    var bookEntity = bookMapper.toEntity(bookDTO);
                    var updatedBook = bookRepository.save(bookEntity);
                    return bookMapper.toDTO(updatedBook);
                } else {
                    throw new ResourceNotFoundException("Book with ID " + bookDTO.getId() + " not found.");
                }
            } else {
                var bookEntity = bookMapper.toEntity(bookDTO);
                var newBook = bookRepository.save(bookEntity);
                return bookMapper.toDTO(newBook);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the book: " + e.getMessage(), e);
        }
    }

    public List<Book> getOnlyOfBooks() {
        return bookRepository.findAllBooks();

    }

    public Optional<Long> countBooksByTitle(String title) {
        if(title.isEmpty()){
            throw  new  ResourceNotFoundException("please enter title");
        }
        if(bookRepository.countBooksByTitle(title).isPresent()){
            return bookRepository.countBooksByTitle(title);
        }

        return null;
    }
    @Transactional
    @Cacheable(value = "booksCache", key = "#id")
    public Optional<BookDTO> getBookById(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id)
                    .map(bookMapper::toDTO);
        } else {
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }
    }
    @CacheEvict(value = "booksCache", key = "#id")
    public String deleteBookById(Long id) throws Exception {

            if (bookRepository.existsById(id)) {
                borrowingRecordRepository.deleteByBookId(id);
                bookRepository.deleteById(id);
                return "Book with ID " + id + " deleted successfully.";
            } else {
                throw new ResourceNotFoundException("Book with ID " + id + " not found.");
            }
    }
    @Cacheable(value = "getAllBooks", key = "#root.methodName")
    public SuccessResponse<List<BookDTO>> getAllBooks() throws Exception {
        List<BookDTO> books = bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Books not found.");
        }
        return new SuccessResponse<>(
                "Books return data.",
                books
        );
    }
}