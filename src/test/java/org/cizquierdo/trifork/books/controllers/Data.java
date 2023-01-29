package org.cizquierdo.trifork.books.controllers;

import org.cizquierdo.trifork.books.models.Book;

import java.text.SimpleDateFormat;
import java.util.*;

public class Data {
    public final static List<Book> BOOKS;
    public final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 2023);
        cal1.set(Calendar.MONTH, 0);
        cal1.set(Calendar.DAY_OF_MONTH, 27);
        cal1.set(Calendar.HOUR_OF_DAY, 00);
        cal1.set(Calendar.MINUTE, 59);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal1.setTimeZone(TimeZone.getTimeZone("GMT"));

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2023);
        cal2.set(Calendar.MONTH, 0);
        cal2.set(Calendar.DAY_OF_MONTH, 28);
        cal2.set(Calendar.HOUR_OF_DAY, 10);
        cal2.set(Calendar.MINUTE, 15);
        cal2.set(Calendar.SECOND, 27);
        cal2.set(Calendar.MILLISECOND, 0);
        cal2.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date d = cal1.getTime();
        BOOKS = Arrays.asList(new Book(1L, "The Art Of War", "Sun Tzu", 20.05, cal1.getTime()),
                new Book(2L, "The Holy Cow", "Tarun Chopra", 15.59, cal2.getTime()));
    }
}
