package org.cizquierdo.trifork.books.exceptions;

/**
 * Custom Exception to use when a book information has null values in creation operations.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
public class BookNullValuesException extends RuntimeException {
    public BookNullValuesException(String message) {
        super(message);
    }
}