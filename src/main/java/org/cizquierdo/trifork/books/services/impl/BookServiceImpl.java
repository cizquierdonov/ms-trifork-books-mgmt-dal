package org.cizquierdo.trifork.books.services.impl;

import org.cizquierdo.trifork.books.models.Book;
import org.cizquierdo.trifork.books.repositories.BookRepository;
import org.cizquierdo.trifork.books.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements orchestration business logic to manage the persistence of books in the system.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    /**
     * Get a list of all books created in the database.
     * @return  List of {@link Book} objects.
     */
    @Override
    public List<Book> list() {
        return null;
    }

    /**
     * Create a new book in the database.
     * @param book  information about book to create.
     * @return      {@link Book} object created with its ID.
     */
    @Override
    public Book save(Book book) {
        return null;
    }

    /**
     * Get a book from database if exists.
     * @param id    Book identifier for the search.
     * @return      {@link Book} Information for the specified ID.
     */
    @Override
    public Book findById(Long id) {
        return null;
    }

    /**
     * Update information for a specific book if exists in the database.
     * @param id    Book identifier for the searching.
     * @param book  Book information to update.
     */
    @Override
    public void update(Long id, Book book) {

    }

    /**
     * Delete a specific book from database.
     * @param id    Book identifier for the searching.
     */
    @Override
    public void delete(long id) {

    }
}
