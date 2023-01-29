package org.cizquierdo.trifork.books.controllers;

import org.cizquierdo.trifork.books.exceptions.BookDoesNotExistException;
import org.cizquierdo.trifork.books.exceptions.BookNullValuesException;
import org.cizquierdo.trifork.books.models.Book;
import org.cizquierdo.trifork.books.services.BookService;
import org.cizquierdo.trifork.books.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Implements controller CRUD operations for the book resource.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    /**
     * Gets all the books created in the system inside a Response Entity object.
     * @return  {@link Book} list with the detail of each one, encapsulated in a {@link ResponseEntity} object, with
     * success code 0. If no book exists, returns an empty list.
     */
    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("[REQ] Calling GET '/api/books' operation - list()");

        List<Book> books = bookService.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        response.put("code", Constants.SUCCESS_OPERATION_CODE);
        response.put("message", Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE);
        response.put("timestamp", LocalDate.now().toString());

        LOGGER.info("[RES][HTTP 200] Successful Operation: " + books.size() + " books obtained - list()");

        return ResponseEntity.ok(response);
    }

    /**
     * Create a new Book in the system.
     * @param book  Entity object, that contains the book details, except for the ID.
     * @return      {@link Book} object created in the system, including the ID attribute, all encapsulated in a
     *              {@link ResponseEntity} object, with success code 0.
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book) {
        LOGGER.info("[REQ] Calling POST '/api/books' operation - save(" + book.toString() + ")");
        Map<String, Object> response = new HashMap<>();

        try {
            Book newBook = bookService.save(book);
            response.put("book", newBook);
            response.put("code", Constants.SUCCESS_OPERATION_CODE);
            response.put("message", Constants.SUCCESS_BOOK_CREATION_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());

            LOGGER.info("[RES][HTTP 200] Book created successfully: " + book.toString());
            return ResponseEntity.ok(response);
        } catch (BookNullValuesException e) {
            LOGGER.info("[RES][HTTP 400] Error creating book: some fields are null: " + book.toString());
            LOGGER.error(e.getMessage(), e);
            response.put("code", Constants.ERROR_OPERATION_CODE);
            response.put("message", Constants.BAD_REQUEST_NULL_VALUES_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get a specific book, existing in the system.
     * @param id    Book identifier.
     * @return      {@link Book} object encapsulated in a {@link ResponseEntity} object with success code 0. if it does
     *              not exist, it returns error code -1.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        LOGGER.info("[REQ] Calling GET '/api/books/ operation" + id + "' - findById('" + id + "')");
        Map<String, Object> response = new HashMap<>();

        try {
            Book book = bookService.findById(id);
            response.put("book", book);
            response.put("code", Constants.SUCCESS_OPERATION_CODE);
            response.put("message", Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());

            LOGGER.info("[RES][HTTP 200] Successful Operation: " + book.toString());
            return ResponseEntity.ok(response);

        } catch (BookDoesNotExistException e) {
            LOGGER.info("[RES][HTTP 200] Book with ID '" + id + "' doesn't exist");
            LOGGER.error(e.getMessage(), e);
            response.put("code", Constants.ERROR_OPERATION_CODE);
            response.put("message", Constants.BOOK_DOES_NOT_EXIST_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.ok(response);
        }

    }

    /**
     * Updates the specified information for a particular book. The fields to consider for the update must not have
     * null values.
     * @param id    Book identifier to update.
     * @param book  Information to modify in the existing book.
     * @return      Returns the success code 0, encapsulated in a {@link ResponseEntity} object.
     *              {@link ResponseEntity} object, with success code 0.
     *              If the operation was not successful the book does not exists, it returns error code -1.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        LOGGER.info("[REQ] Calling PUT '/api/books/" + id + "' operation - update(" + book.toString() + ")");
        Map<String, Object> response = new HashMap<>();

        try {
            book.setId(id);
            bookService.update(book);
            response.put("code", Constants.SUCCESS_OPERATION_CODE);
            response.put("message", Constants.SUCCESS_BOOK_UPDATE_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());

            LOGGER.info("[RES][HTTP 200] Book updated successfully: " + book.toString());
            return ResponseEntity.ok(response);
        } catch (BookNullValuesException e) {
            LOGGER.info("[RES][HTTP 400] Error updating book: some fields are null: " + book.toString());
            LOGGER.error(e.getMessage(), e);
            response.put("code", Constants.ERROR_OPERATION_CODE);
            response.put("message", Constants.BAD_REQUEST_NULL_VALUES_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.badRequest().body(response);
        } catch (BookDoesNotExistException e) {
            LOGGER.info("[RES][HTTP 200] Book with ID '" + id + "' doesn't exist to update");
            LOGGER.error(e.getMessage(), e);
            response.put("code", Constants.ERROR_OPERATION_CODE);
            response.put("message", Constants.BOOK_DOES_NOT_EXIST_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Delete a specified book in the system.
     * @param id    Book identifier to delete.
     * @return      Returns the success code 0, encapsulated in a {@link ResponseEntity} object. If the operation was
     * not successful the book does not exists, it returns error code -1.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        LOGGER.info("[REQ] Calling DELETE '/api/books/" + id + "' operation - delete(" + id + ")");
        Map<String, Object> response = new HashMap<>();

        try {
            bookService.delete(id);
            response.put("code", Constants.SUCCESS_OPERATION_CODE);
            response.put("message", Constants.SUCCESS_BOOK_DELETE_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());

            LOGGER.info("[RES][HTTP 200] Book with ID '" + id + "' deleted successfully ");
            return ResponseEntity.ok(response);
        } catch (BookDoesNotExistException e) {
            LOGGER.info("[RES][HTTP 200] Book with ID '" + id + "' doesn't exist to delete");
            LOGGER.error(e.getMessage(), e);
            response.put("code", Constants.ERROR_OPERATION_CODE);
            response.put("message", Constants.BOOK_DOES_NOT_EXIST_MESSAGE);
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.ok(response);
        }
    }

}
