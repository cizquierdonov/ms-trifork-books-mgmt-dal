package org.cizquierdo.trifork.books.services;

import org.cizquierdo.trifork.books.models.Book;

import java.util.List;

public interface BookService {
    public List<Book> list();
    public Book save(Book book);
    public Book findById(Long id);
    public void update(Long id, Book book);
    public void delete(long id);

}
