package org.cizquierdo.trifork.books.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

/**
 * Model class that represents a book object and maps to the database table "books". The fields are described below:
 * <ul>
 *     <li>{@link Book#id}: Book identifier, it's unique, and autoincremental.</li>
 *     <li>{@link Book#title}: Name of the book, it's a string value.</li>
 *     <li>{@link Book#author}: Name of the book author, it's a string value.</li>
 *     <li>{@link Book#price}: Book price in Euros, it's numeric value with two decimals.</li>
 *     <li>{@link Book#lastUpdated}: Last book update date & time.</li>
 * </ul>
 *
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Double price;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_updated")
    private Date lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && title.equals(book.title) && author.equals(book.author) && price.equals(book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, price);
    }

    public Book clone() {
       return new Book(this.id, this.title, this.author, this.price, this.lastUpdated);
    }

}
