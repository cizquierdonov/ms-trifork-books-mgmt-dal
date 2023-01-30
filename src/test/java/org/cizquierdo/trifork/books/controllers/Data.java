package org.cizquierdo.trifork.books.controllers;

import org.cizquierdo.trifork.books.models.Book;

import java.text.SimpleDateFormat;
import java.util.*;

public class Data {
    public final static List<Book> BOOKS = Arrays.asList(new Book(1L, "The Art Of War", "Sun Tzu", 20.05, new Date()),
            new Book(2L, "The Holy Cow", "Tarun Chopra", 15.59, new Date()));

}
