package org.cizquierdo.trifork.books.util;

import java.text.SimpleDateFormat;

/**
 * Defines all used constants in the application.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
public class Constants {

    public static final String CONTROLLERS_BASE_PACKAGE = "org.cizquierdo.trifork.books.controllers";
    public final static int SUCCESS_OPERATION_CODE = 0;
    public final static int ERROR_OPERATION_CODE = -1;
    public final static String SUCCESS_BOOK_CREATION_MESSAGE = "The Book was created successfully.";
    public final static String SUCCESS_BOOK_LIST_OR_GET_MESSAGE = "Successful operation.";
    public final static String SUCCESS_BOOK_UPDATE_MESSAGE = "Book updated successfully.";
    public final static String SUCCESS_BOOK_DELETE_MESSAGE = "Book deleted successfully.";
    public final static String BOOK_DOES_NOT_EXIST_MESSAGE = "There is no book for the ID.";
    public final static String BAD_REQUEST_NULL_VALUES_MESSAGE = "Some of the fields are null, empty, less or equals than zero.";
    public final static String BAD_REQUEST_EMPTY_VALUES_MESSAGE = "Some of the fields are empty, less or equals than zero.";

    public final static SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssXXX");

}
