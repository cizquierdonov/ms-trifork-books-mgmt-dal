package org.cizquierdo.trifork.books.services;

import org.cizquierdo.trifork.books.models.Book;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookService {
    public List<Book> findAll();
    public Book save(Book book);
    public Book findById(Long id);
    public void update(Book book);
    public void delete(long id);

}
