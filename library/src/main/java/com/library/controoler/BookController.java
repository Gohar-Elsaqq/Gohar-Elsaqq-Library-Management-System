package com.library.controoler;

import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;
    //add book
    @PostMapping
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) throws Exception {
        return bookService.saveBook(bookDTO);
    }


    @GetMapping
    public List<BookDTO> getAllBooks() throws Exception {
        return bookService.getAllBooks();
    }
    @GetMapping("/all")
    public List<Book> getOnlyOfBooks() {
        return bookService.getOnlyOfBooks();
    }
    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    @PutMapping()
    public BookDTO updateBook(@Valid @RequestBody BookDTO bookDTO)  {
//            bookDTO.setId(id);
        return bookService.saveBook(bookDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) throws Exception {
        String result = bookService.deleteBookById(id);
        return ResponseEntity.ok(result);
    }
}