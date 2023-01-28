package org.cizquierdo.trifork.books.controllers;

import org.cizquierdo.trifork.books.models.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Implements controller CRUD operations for the book resource.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
@RestController
@RequestMapping("/books")
public class BookController {

    /**
     * Gets all the books created in the system inside a Response Entity object.
     * @return  {@link Book} list with the detail of each one, encapsulated in a {@link ResponseEntity} object, with
     * success code 0. If no book exists, returns an empty list.
     */
    @GetMapping("/")
    public ResponseEntity<?> list() {
        return null;
    }

    /**
     * Create a new Book in the system.
     * @param book  Entity object, that contains the book details, except for the ID.
     * @return      {@link Book} object created in the system, including the ID attribute, all encapsulated in a
     *              {@link ResponseEntity} object, with success code 0.
     */
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Book book) {
        return null;
    }

    /**
     * Get a specific book, existing in the system.
     * @param id    Book identifier.
     * @return      {@link Book} object encapsulated in a {@link ResponseEntity} object with success code 0. if it does
     *              not exist, it returns error code -1.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return null;
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
        return null;
    }

    /**
     * Delete a specified book in the system.
     * @param id    Book identifier to delete.
     * @return      Returns the success code 0, encapsulated in a {@link ResponseEntity} object. If the operation was
     * not successful the book does not exists, it returns error code -1.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return null;
    }

}
