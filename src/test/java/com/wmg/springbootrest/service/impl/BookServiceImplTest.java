package com.wmg.springbootrest.service.impl;

import com.wmg.springbootrest.exception.BookNotFoundException;
import com.wmg.springbootrest.model.Book;
import com.wmg.springbootrest.repository.BookRepository;
import com.wmg.springbootrest.service.BookService;
import junit.framework.TestCase;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookServiceImplTest extends TestCase {

    private Book book;
    private List<Book> bookList;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Before
    public void setUp() throws Exception {
        book = Book.builder()
                .id(1L)
                .title("Test Title")
                .author("Test Author")
                .price(20.0f)
                .build();

        bookList = Lists.newArrayList(book);
    }

    @After
    public void tearDown() throws Exception {
        book = null;
        bookList = null;
    }

    @Test
    public void test_find_all() {
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> found = bookService.listAll();

        assertThat(found.size()).isEqualTo(1);
    }

    @Test
    public void test_find_by_id_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Book found = bookService.getById(anyLong());

        assertThat(found).isEqualToComparingFieldByField(book);
    }

    @Test(expected = BookNotFoundException.class)
    public void test_find_by_id_not_found() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        bookService.getById(anyLong());

        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    public void test_save() {
        when(bookRepository.save(book)).thenReturn(book);

        Book persisted = bookService.save(book);

        assertThat(persisted.getId()).isPositive();
    }

    @Test
    public void test_update() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book updated = bookService.update(anyLong(), book);

        assertThat(updated.getId()).isEqualTo(book.getId());
        assertThat(updated).isEqualToComparingFieldByField(book);
    }

    @Test
    public void test_update_when_book_not_found() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updated = bookService.update(anyLong(), book);

        assertThat(updated.getId()).isEqualTo(book.getId());
        assertThat(updated).isEqualToComparingFieldByField(book);
    }

    @Test
    public void test_patch() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book patched = bookService.patch(anyLong(), book);

        assertThat(patched.getId()).isEqualTo(book.getId());
        assertThat(patched).isEqualToComparingFieldByField(book);
    }

    @Test(expected = BookNotFoundException.class)
    public void test_patch_when_book_not_found() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);

        Book patched = bookService.patch(anyLong(), book);

        assertThat(patched.getId()).isEqualTo(book.getId());
        assertThat(patched).isEqualToComparingFieldByField(book);
    }

    @Test
    public void test_delete_by_id() {
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.delete(anyLong());

        verify(bookRepository, times(1)).deleteById(anyLong());
    }

}