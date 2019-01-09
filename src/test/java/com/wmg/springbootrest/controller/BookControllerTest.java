package com.wmg.springbootrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wmg.springbootrest.model.Book;
import com.wmg.springbootrest.service.BookService;
import junit.framework.TestCase;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;
    private List<Book> bookList;

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

    }

    @Test
    public void test_find_all_success() throws Exception {
        when(bookService.listAll()).thenReturn(bookList);

        mockMvc.perform(get("/book").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())));

        verify(bookService, times(1)).listAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void test_find_by_id_success() throws Exception {
        when(bookService.getById(anyLong())).thenReturn(book);

        mockMvc.perform(get("/book/{id}", anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(book.getTitle())));

        verify(bookService, times(1)).getById(anyLong());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void test_save_success() throws Exception {
        when(bookService.save(book)).thenReturn(book);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(book)))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).save(book);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void test_update_success() throws Exception {
        when(bookService.getById(anyLong())).thenReturn(book);
        when(bookService.update(anyLong(), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/book/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(book)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).update(anyLong(), any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void test_patch_success() throws Exception {
        when(bookService.getById(anyLong())).thenReturn(book);
        when(bookService.patch(anyLong(), any(Book.class))).thenReturn(book);

        mockMvc.perform(patch("/book/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(book)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).patch(anyLong(), any(Book.class));
        verifyNoMoreInteractions(bookService);
    }


    @Test
    public void test_delete_success() throws Exception {
        when(bookService.getById(anyLong())).thenReturn(book);
        doNothing().when(bookService).delete(anyLong());

        mockMvc.perform(delete("/book/{id}", anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).delete(anyLong());
        verifyNoMoreInteractions(bookService);
    }

    /*
   * converts a Java object into JSON representation
   */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}