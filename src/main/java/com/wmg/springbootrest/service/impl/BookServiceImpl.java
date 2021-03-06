package com.wmg.springbootrest.service.impl;

import com.wmg.springbootrest.exception.BookNotFoundException;
import com.wmg.springbootrest.model.Book;
import com.wmg.springbootrest.repository.BookRepository;
import com.wmg.springbootrest.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
        Optional<Book> fetched = bookRepository.findById(id);
        return fetched.orElseThrow(() -> BookNotFoundException.instance(String.format("Book with id %s not found", id.toString())));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, Book book) {

        return bookRepository.findById(id).map(fetched -> {
            BeanUtils.copyProperties(book, fetched);
            fetched.setId(id);
            return bookRepository.save(fetched);
        }).orElseGet(() -> {
            book.setId(id);
            return bookRepository.save(book);
        });
    }

    @Override
    public Book patch(Long id, Book domainObject) {
        return bookRepository.findById(id).map(fetched -> {

            // TODO This should ne replaced with JSON Patch (https://tools.ietf.org/html/rfc6902)
            if (domainObject.getAuthor() != null) {
                fetched.setAuthor(domainObject.getAuthor());
            }

            if (domainObject.getTitle() != null) {
                fetched.setTitle(domainObject.getTitle());
            }

            if (domainObject.getPrice() != 0.0f) {
                fetched.setPrice(domainObject.getPrice());
            }
            return bookRepository.save(fetched);
        }).orElseThrow(() -> BookNotFoundException.instance(String.format("Book with id %s not found", id.toString())));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
