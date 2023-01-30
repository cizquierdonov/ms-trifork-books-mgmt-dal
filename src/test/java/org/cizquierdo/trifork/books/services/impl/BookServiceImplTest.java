package org.cizquierdo.trifork.books.services.impl;

import org.cizquierdo.trifork.books.exceptions.BookDoesNotExistException;
import org.cizquierdo.trifork.books.exceptions.BookNullValuesException;
import org.cizquierdo.trifork.books.models.Book;
import org.cizquierdo.trifork.books.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.cizquierdo.trifork.books.controllers.Data.BOOKS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void testListWithElements() throws Exception {
        // Given
        when(bookRepository.findAll()).thenReturn(BOOKS);

        // When
        List<Book> books = bookService.findAll();

        // Then
        assertEquals(BOOKS.size(), books.size());
        assertEquals(BOOKS.get(0).getId(), books.get(0).getId());
        assertEquals(BOOKS.get(0).getTitle(), books.get(0).getTitle());
        assertEquals(BOOKS.get(0).getAuthor(), books.get(0).getAuthor());
        assertEquals(BOOKS.get(0).getPrice(), books.get(0).getPrice());
        assertEquals(BOOKS.get(1).getLastUpdated(), books.get(1).getLastUpdated());
        assertEquals(BOOKS.get(1).getId(), books.get(1).getId());
        assertEquals(BOOKS.get(1).getTitle(), books.get(1).getTitle());
        assertEquals(BOOKS.get(1).getAuthor(), books.get(1).getAuthor());
        assertEquals(BOOKS.get(1).getPrice(), books.get(1).getPrice());
        assertEquals(BOOKS.get(1).getLastUpdated(), books.get(1).getLastUpdated());

        verify(bookRepository).findAll();
    }

    @Test
    void testListEmpty() throws Exception {
        List<Book> expectedList = Collections.emptyList();
        // Given
        when(bookRepository.findAll()).thenReturn(expectedList);

        // When
        List<Book> books = bookService.findAll();

        // Then
        assertEquals(0, books.size());

        verify(bookRepository).findAll();
    }


    @Test
    void testSaveSuccessful() throws Exception {
        long bookId = 3L;
        Book expectedBook = new Book(bookId, "Test Book", "Test Author", 99.99, null);

        // Given
        when(bookRepository.save(any(Book.class))).then(invocation -> {
            Book b = invocation.getArgument(0);
            b.setId(bookId);
            return b;
        });

        // When
        Book book = bookService.save(expectedBook);

        // Then
        assertNotNull(book);
        assertEquals(bookId, book.getId());
        assertEquals(expectedBook.getTitle(), book.getTitle());
        assertEquals(expectedBook.getAuthor(), book.getAuthor());
        assertEquals(expectedBook.getPrice(), book.getPrice());
        assertEquals(expectedBook.getLastUpdated(), book.getLastUpdated());

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testSaveNullEmptyValues() throws Exception {
        Book expectedBook = BOOKS.get(0).clone();

        // Given
        expectedBook.setAuthor(null);
        expectedBook.setPrice(0D);

        // When
        try {
            Book book = bookService.save(expectedBook);
            assertTrue(false);
        } catch (BookNullValuesException e) {
            // Then
            assertTrue(true);
        }
    }

    @Test
    void testFindByIdBookExists() throws Exception {
        Optional<Book> expectedBook = Optional.of(BOOKS.get(0));
        Long bookId = expectedBook.get().getId();

        // Given
        when(bookRepository.findById(anyLong())).thenReturn(expectedBook);

        // When
        Book book = bookService.findById(bookId);

        // Then
        assertNotNull(book);
        assertEquals(bookId, book.getId());
        assertEquals(expectedBook.get().getTitle(), book.getTitle());
        assertEquals(expectedBook.get().getAuthor(), book.getAuthor());
        assertEquals(expectedBook.get().getPrice(), book.getPrice());
        assertEquals(expectedBook.get().getLastUpdated(), book.getLastUpdated());

        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testFindByIdBookDoesNotExist() throws Exception {
        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        try {
            bookService.findById(3L);
            assertTrue(false);
        } catch (BookDoesNotExistException e) {
            // Then
            assertTrue(true);
        }

        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testUpdateSuccessful() throws Exception {
        Book book = new Book(1L, "Test Book", "Test Author", 99.99, null);
        Optional<Book> expectedBook = Optional.of(book);

        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(any(Book.class));

        // When
        try {
            bookService.update(book);
            // Then
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        verify(bookRepository).findById(anyLong());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testUpdateNullValues() throws Exception {
        Book book = BOOKS.get(0).clone();
        book.setTitle("");
        Optional<Book> expectedBook = Optional.of(book);

        // Given
        when(bookRepository.findById(anyLong())).thenReturn(expectedBook);

        // When
        try {
            bookService.update(book);
            // Then
            assertTrue(false);
        } catch (BookNullValuesException e) {
            assertTrue(true);
        }

        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testUpdateBookDoesNotExist() throws Exception {
        Book book = BOOKS.get(0).clone();

        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        try {
            bookService.update(book);
            // Then
            assertTrue(false);
        } catch (BookDoesNotExistException e) {
            assertTrue(true);
        }

        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testDeleteSuccessful() throws Exception {
        Long bookId = 1L;
        Optional book = Optional.of(BOOKS.get(0));

        // Given
        when(bookRepository.findById(bookId)).thenReturn(book);
        doNothing().when(bookRepository).deleteById(bookId);

        // When
        try {
            bookService.delete(bookId);
            // Then
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(bookRepository).findById(bookId);
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void testDeleteBookDoesNotExist() throws Exception {
        Long bookId = 1L;

        // Given
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When
        try {
            bookService.delete(bookId);
            // Then
            assertTrue(false);
        } catch (BookDoesNotExistException e) {
            assertTrue(true);
        }

        verify(bookRepository).findById(bookId);
    }

}