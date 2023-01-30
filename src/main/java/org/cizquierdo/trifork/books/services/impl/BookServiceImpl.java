package org.cizquierdo.trifork.books.services.impl;

import org.cizquierdo.trifork.books.exceptions.BookDoesNotExistException;
import org.cizquierdo.trifork.books.exceptions.BookNullValuesException;
import org.cizquierdo.trifork.books.models.Book;
import org.cizquierdo.trifork.books.repositories.BookRepository;
import org.cizquierdo.trifork.books.services.BookService;
import org.cizquierdo.trifork.books.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implements orchestration business logic to manage the persistence of books in the system.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Get a list of all books created in the database.
     * @return  List of {@link Book} objects.
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll(/*Sort.by(Sort.Direction.ASC, "seatNumber")*/);
    }

    /**
     * Create a new book in the database.
     * @param book  information about book to create.
     * @return      {@link Book} object created with its ID.
     */
    @Override
    public Book save(Book book) {
        if (!hasNullEmptyLessOrEqualsThanZeroValues(book)) {
            book.setLastUpdated(new Date());
            return bookRepository.save(book);
        } else {
            throw new BookNullValuesException(Constants.BAD_REQUEST_NULL_VALUES_MESSAGE);
        }
    }

    /**
     * Get a book from database if exists.
     * @param id    Book identifier for the search.
     * @return      {@link Book} Information for the specified ID.
     */
    @Override
    public Book findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        return book.orElseThrow(() -> new BookDoesNotExistException(Constants.BOOK_DOES_NOT_EXIST_MESSAGE));
    }

    /**
     * Update information for a specific book if exists in the database.
     * @param book  Book information to update, including the ID of the existent book.
     */
    @Override
    public void update(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getId());

        if (existingBook.isPresent()) {
            Book bookToUpdate = new Book();
            bookToUpdate.setId(book.getId());
            bookToUpdate.setTitle(book.getTitle() != null ? book.getTitle() : existingBook.get().getTitle());
            bookToUpdate.setAuthor(book.getAuthor() != null ? book.getAuthor() : existingBook.get().getAuthor());
            bookToUpdate.setPrice(book.getPrice() != null ? book.getPrice() : existingBook.get().getPrice());
            bookToUpdate.setLastUpdated(new Date());

            if (!hasEmptyLessOrEqualsThanZeroValues(bookToUpdate)) {
                bookRepository.save(bookToUpdate);
            } else {
                throw new BookNullValuesException(Constants.BAD_REQUEST_EMPTY_VALUES_MESSAGE);
            }
        } else {
            throw new BookDoesNotExistException(Constants.BOOK_DOES_NOT_EXIST_MESSAGE);
        }
    }

    /**
     * Delete a specific book from database.
     * @param id    Book identifier for the searching.
     */
    @Override
    public void delete(long id) {
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new BookDoesNotExistException(Constants.BOOK_DOES_NOT_EXIST_MESSAGE));
        bookRepository.deleteById(id);
    }

    private boolean hasNullEmptyLessOrEqualsThanZeroValues(Book book) {
        if ( (book.getTitle() != null) && (book.getAuthor() != null) && (book.getPrice() != null)
                && !(book.getTitle().equals("")) && !(book.getAuthor().equals("")) && (book.getPrice() > 0) ) {
            return false;
        } else {
            return true;
        }
    }

    private boolean hasEmptyLessOrEqualsThanZeroValues(Book book) {
        if ( !(book.getTitle().equals("")) && !(book.getAuthor().equals("")) && (book.getPrice() > 0) ) {
            return false;
        } else {
            return true;
        }
    }

}
