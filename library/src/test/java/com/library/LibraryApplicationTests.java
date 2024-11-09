package com.library;

import com.library.dto.BookDTO;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryApplicationTests {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@Test
	void findByIdNotFound() {
		 Optional<BookDTO> book=bookService.getBookById(99L);
		assertEquals("Book with ID 99 not found.",false);
	}

	@Test
	void findById() {

		Optional<BookDTO> book = bookService.getBookById(4L);

		assertTrue(book.isPresent(), "Book with ID 4 should exist.");


		assertNotNull(book.get(), "BookDTO object should not be null.");
		System.out.println("return book  ");
	}



	@Test
	public void testSaveBook() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("Clean Code");
		bookDTO.setAuthor("Robert C. Martin");
		bookDTO.setIsbn("978-0132350884");
		bookDTO.setPublicationYear(2003);

		BookDTO savedBook = bookService.saveBook(bookDTO);

		assertNotNull(savedBook.getId());
		assertEquals("Clean Code", savedBook.getTitle());
		assertEquals("Robert C. Martin", savedBook.getAuthor());
		assertEquals("978-0132350884", savedBook.getIsbn());
		System.out.println("save book ");
		bookRepository.deleteById(savedBook.getId());
		System.out.println(savedBook);
	}
}
