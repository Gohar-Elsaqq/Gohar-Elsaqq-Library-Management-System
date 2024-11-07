package com.library.service;

import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BookDTO> getAllBooks() throws Exception {
        try {
            return bookRepository.findAll()
                    .stream()
                    .map(bookMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }
    public List<Book> getOnlyOfBooks() {
        return bookRepository.findAllBooks();

    }

    public Optional<BookDTO> getBookById(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id)
                    .map(bookMapper::toDTO);
        } else {
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }
    }

    public String deleteBookById(Long id) throws Exception {
        try {
            if (bookRepository.existsById(id)) {
                borrowingRecordRepository.deleteByBookId(id);
                bookRepository.deleteById(id);
                return "Book with ID " + id + " deleted successfully.";
            } else {
                throw new ResourceNotFoundException("Book with ID " + id + " not found.");
            }
        } catch (Exception exception) {
            throw new Exception("An error occurred while deleting the book: " + exception.getMessage());
        }
    }
}