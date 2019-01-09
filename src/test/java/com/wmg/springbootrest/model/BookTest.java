package com.wmg.springbootrest.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class BookTest {

    private Book book;
    private Book.BookBuilder bookBuilder;

    @Before
    public void setUp() throws Exception {

        bookBuilder = Book.builder()
                .id(1L)
                .author("Test Author Name")
                .price(20.0f)
                .title("Test Title");

        book = bookBuilder.build();
    }

    @After
    public void tearDown() throws Exception {
        book = null;
        bookBuilder = null;
    }

    @Test
    public void testAllDefaults() {
        Assertions.assertPojoMethodsFor(Book.class).thoroughly().areWellImplemented();
    }

    @Test
    public void testBuilder() {
        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getTitle()).isEqualTo("Test Title");
        assertThat(book.getAuthor()).isEqualTo("Test Author Name");
        assertThat(book.getPrice()).isEqualTo(20.0f);
    }

    @Test
    public void testToString() {
        Book book = Book.builder().build();
        assertThat(book.toString()).isNotBlank().isNotEmpty();
        assertThat(book.toString()).isEqualTo("Book(id=null, title=null, author=null, price=0.0)");
    }

    @Test
    public void testBuilderToString() {
        assertNotNull(bookBuilder.toString());
    }

    @Test
    public void testEquals() {
        Book book1 = Book.builder()
                .id(1L)
                .author("Test Author Name")
                .price(20.0f)
                .title("Test Title")
                .build();

        assertEquals(true, book.equals(book1));
        assertThat(book).isEqualToComparingFieldByField(book1);
    }

}