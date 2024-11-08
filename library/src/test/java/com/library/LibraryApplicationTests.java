package com.library;

import com.library.dto.BookDTO;
import com.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryApplicationTests {

	@Autowired
	private BookService bookService;

//	@Test
//	void findByIdNotFound() {
//		 Optional<BookDTO> book=bookService.getBookById(99L);
//		assertEquals("Book with ID 99 not found.",false);
//	}

	@Test
	void findById() {

		Optional<BookDTO> book = bookService.getBookById(4L);

		assertTrue(book.isPresent(), "Book with ID 4 should exist.");


		assertNotNull(book.get(), "BookDTO object should not be null.");
		System.out.println("return book  ");
	}

}
