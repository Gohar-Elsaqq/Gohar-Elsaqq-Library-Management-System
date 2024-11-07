package com.library;

import com.library.dto.BookDTO;
import com.library.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LibraryApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LibraryApplication.class, args);
		System.out.println("Library Application started");
	}
}