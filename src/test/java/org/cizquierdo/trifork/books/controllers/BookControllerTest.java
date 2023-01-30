package org.cizquierdo.trifork.books.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cizquierdo.trifork.books.exceptions.BookDoesNotExistException;
import org.cizquierdo.trifork.books.exceptions.BookNullValuesException;
import org.cizquierdo.trifork.books.models.Book;
import org.cizquierdo.trifork.books.services.BookService;
import org.cizquierdo.trifork.books.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.cizquierdo.trifork.books.controllers.Data.BOOKS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testListWithElements() throws Exception {
        // Given
        when(bookService.findAll()).thenReturn(BOOKS);

        // When
        mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.SUCCESS_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.books[0].id").value(BOOKS.get(0).getId()))
                .andExpect(jsonPath("$.books[0].title").value(BOOKS.get(0).getTitle()))
                .andExpect(jsonPath("$.books[0].author").value(BOOKS.get(0).getAuthor()))
                .andExpect(jsonPath("$.books[0].price").value(BOOKS.get(0).getPrice()))
                .andExpect(jsonPath("$.books[0].lastUpdated").exists())
                .andExpect(jsonPath("$.books[1].id").value(BOOKS.get(1).getId()))
                .andExpect(jsonPath("$.books[1].title").value(BOOKS.get(1).getTitle()))
                .andExpect(jsonPath("$.books[1].author").value(BOOKS.get(1).getAuthor()))
                .andExpect(jsonPath("$.books[1].price").value(BOOKS.get(1).getPrice()))
                .andExpect(jsonPath("$.books[1].lastUpdated").exists());

        verify(bookService).findAll();
    }

    @Test
    void testListEmpty() throws Exception {
        // Given
        when(bookService.findAll()).thenReturn(new ArrayList<>());

        // When
        mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.SUCCESS_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.books", hasSize(0)));

        verify(bookService).findAll();
    }


    @Test
    void testSaveSuccessful() throws Exception {
        Book newBook = BOOKS.get(0).clone();
        long bookId = 3L;
        // Given
        when(bookService.save(any())).then(invocation -> {
            Book b = invocation.getArgument(0);
            b.setId(bookId);
            return b;
        });

        // When
        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.SUCCESS_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_BOOK_CREATION_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.book").exists())
                .andExpect(jsonPath("$.book.id", is((int) bookId)))
                .andExpect(jsonPath("$.book.title", is(newBook.getTitle())))
                .andExpect(jsonPath("$.book.author", is(newBook.getAuthor())))
                .andExpect(jsonPath("$.book.price", is(newBook.getPrice())))
                .andExpect(jsonPath("$.book.lastUpdated").exists());

        verify(bookService).save(any());
    }

    @Test
    void testSaveNullValues() throws Exception {
        Book newBook = BOOKS.get(0);
        newBook.setAuthor(null);
        long bookId = 3L;
        // Given
        when(bookService.save(any())).thenThrow(BookNullValuesException.class);

        // When
        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.ERROR_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.BAD_REQUEST_NULL_VALUES_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.book").doesNotExist());

        verify(bookService).save(any());
    }

    @Test
    void testFindByIdBookExists() throws Exception {
        when(bookService.findById(1L)).thenReturn(BOOKS.get(0));

        // When
        mvc.perform(get("/api/books/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.SUCCESS_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.book.id").value(BOOKS.get(0).getId()))
                .andExpect(jsonPath("$.book.title").value(BOOKS.get(0).getTitle()))
                .andExpect(jsonPath("$.book.author").value(BOOKS.get(0).getAuthor()))
                .andExpect(jsonPath("$.book.price").value(BOOKS.get(0).getPrice()))
                .andExpect(jsonPath("$.book.lastUpdated").exists());

        verify(bookService).findById(1L);
    }

    @Test
    void testFindByIdBookDoesNotExist() throws Exception {
        when(bookService.findById(anyLong())).thenThrow(BookDoesNotExistException.class);

        // When
        mvc.perform(get("/api/books/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.ERROR_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.BOOK_DOES_NOT_EXIST_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.book").doesNotExist());

        verify(bookService).findById(anyLong());
    }

    @Test
    void testUpdateSuccessful() throws Exception {
        Book newBook = BOOKS.get(0).clone();
        // Given
        doNothing().when(bookService).update(any(Book.class));

        // When
        mvc.perform(put("/api/books/"+newBook.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.SUCCESS_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_BOOK_UPDATE_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists());

        verify(bookService).update(any(Book.class));
    }

    @Test
    void testUpdateEmptyValues() throws Exception {
        Book newBook = BOOKS.get(0).clone();
        newBook.setTitle("");

        doThrow(BookNullValuesException.class).when(bookService).update(any(Book.class));

        // When
        mvc.perform(put("/api/books/" + 1L).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.ERROR_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.BAD_REQUEST_EMPTY_VALUES_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists());

        verify(bookService).update(any(Book.class));
    }

    @Test
    void testUpdateBookDoesNotExist() throws Exception {
        Book newBook = BOOKS.get(0).clone();
        Long incorrectBookId = 4L;
        newBook.setId(incorrectBookId);

        doThrow(BookDoesNotExistException.class).when(bookService).update(any(Book.class));

        // When
        mvc.perform(put("/api/books/" + incorrectBookId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.ERROR_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.BOOK_DOES_NOT_EXIST_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists());

        verify(bookService).update(any(Book.class));
    }

    @Test
    void testDeleteSuccessful() throws Exception {
        Long bookId = 1L;
        // Given
        doNothing().when(bookService).delete(bookId);

        // When
        mvc.perform(delete("/api/books/" + bookId).contentType(MediaType.APPLICATION_JSON))
                        //.content(objectMapper.writeValueAsString(newBook)))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.SUCCESS_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_BOOK_DELETE_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists());

        verify(bookService).delete(bookId);
    }

    @Test
    void testDeleteBookDoesNotExist() throws Exception {
        Long bookId = 1L;

        doThrow(BookDoesNotExistException.class).when(bookService).delete(bookId);

        // When
        mvc.perform(delete("/api/books/" + bookId).contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(Constants.ERROR_OPERATION_CODE))
                .andExpect(jsonPath("$.message").value(Constants.BOOK_DOES_NOT_EXIST_MESSAGE))
                .andExpect(jsonPath("$.timestamp").exists());

        verify(bookService).delete(bookId);
    }
}