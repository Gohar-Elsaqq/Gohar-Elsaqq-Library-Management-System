package com.library;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class LibraryApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LibraryApplication.class, args);
		System.out.println("Library Application started");
	}
}