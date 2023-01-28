package org.cizquierdo.trifork.books.exceptions;

/**
 * Custom Exception to use when a book doesn't exist in searching operations.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
public class BookDoesNotExistException extends RuntimeException {
    public BookDoesNotExistException(String message) {
        super(message);
    }
}
