package org.cizquierdo.trifork.books.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
@Data
@NoArgsConstructor
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
    @Column(name = "last_updated", nullable = false)
    private Date lastUpdated;

}
