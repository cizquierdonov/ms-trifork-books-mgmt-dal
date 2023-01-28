package org.cizquierdo.trifork.books.repositories;

import org.cizquierdo.trifork.books.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
