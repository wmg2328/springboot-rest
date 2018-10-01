package com.wmg.springbootrest;

import com.wmg.springbootrest.model.Book;
import com.wmg.springbootrest.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SetupDatabase {

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(Book.builder().title("Origin").author("Dan Brown").price(10).build());
            bookRepository.save(Book.builder().title("Digital Fortress").author("Dan Brown").price(20).build());
            bookRepository.save(Book.builder().title("The Art of Computer Programming").author("Donald Knuth").price(30).build());
        };
    }
}
