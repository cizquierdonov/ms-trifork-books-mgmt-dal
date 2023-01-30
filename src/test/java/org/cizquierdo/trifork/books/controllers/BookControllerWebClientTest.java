package org.cizquierdo.trifork.books.controllers;

import org.cizquierdo.trifork.books.models.Book;
import org.cizquierdo.trifork.books.util.Constants;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerWebClientTest {
    @Autowired
    private WebTestClient client;

    @Test
    @Order(1)
    void testFindAll() {
        client.get().uri("/api/books").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.books").isArray()
                .jsonPath("$.books").value(hasSize(2))
                .jsonPath("$.books[0].id").isEqualTo(1)
                .jsonPath("$.books[0].title").isEqualTo("The Art Of War")
                .jsonPath("$.books[0].author").isEqualTo("Sun Tzu")
                .jsonPath("$.books[0].price").isEqualTo(20.05)
                .jsonPath("$.books[0].lastUpdated").exists()
                .jsonPath("$.books[1].id").isEqualTo(2)
                .jsonPath("$.books[1].title").isEqualTo("The Holy Cow")
                .jsonPath("$.books[1].author").isEqualTo("Tarun Chopra")
                .jsonPath("$.books[1].price").isEqualTo(15.59)
                .jsonPath("$.books[1].lastUpdated").exists();
    }

    @Test
    @Order(2)
    void testSave() {
        // given
        Book book = new Book(null, "1984", "George Orwell", 19.97, null);

        // when
        client.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                // then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_CREATION_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.book.id").isEqualTo(3)
                .jsonPath("$.book.title").isEqualTo(book.getTitle())
                .jsonPath("$.book.author").isEqualTo(book.getAuthor())
                .jsonPath("$.book.price").isEqualTo(book.getPrice())
                .jsonPath("$.book.lastUpdated").exists();
    }

    @Test
    @Order(3)
    void testFindById1() {
        // given
        Long bookId = 3L;

        // when
        client.get().uri("/api/books/" + bookId).exchange()
                // then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.book.id").isEqualTo(3)
                .jsonPath("$.book.title").isEqualTo("1984")
                .jsonPath("$.book.author").isEqualTo("George Orwell")
                .jsonPath("$.book.price").isEqualTo(19.97)
                .jsonPath("$.book.lastUpdated").exists();
    }

    @Test
    @Order(4)
    void testUpdate() {
        // given
        Book book = new Book(3L, "1984", "George Orwell", 26.41, null);

        // when
        client.put().uri("/api/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                // then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_UPDATE_MESSAGE)
                .jsonPath("$.timestamp").exists();
    }

    @Test
    @Order(5)
    void testFindById2() {
        // given
        Long bookId = 3L;

        // when
        client.get().uri("/api/books/" + bookId).exchange()
                // then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.book.id").isEqualTo(3)
                .jsonPath("$.book.title").isEqualTo("1984")
                .jsonPath("$.book.author").isEqualTo("George Orwell")
                .jsonPath("$.book.price").isEqualTo(26.41)
                .jsonPath("$.book.lastUpdated").exists();
    }

    @Test
    @Order(6)
    void testFindAll2() {
        client.get().uri("/api/books").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.books").isArray()
                .jsonPath("$.books").value(hasSize(3))
                .jsonPath("$.books[0].id").isEqualTo(1)
                .jsonPath("$.books[0].title").isEqualTo("The Art Of War")
                .jsonPath("$.books[0].author").isEqualTo("Sun Tzu")
                .jsonPath("$.books[0].price").isEqualTo(20.05)
                .jsonPath("$.books[0].lastUpdated").exists()
                .jsonPath("$.books[1].id").isEqualTo(2)
                .jsonPath("$.books[1].title").isEqualTo("The Holy Cow")
                .jsonPath("$.books[1].author").isEqualTo("Tarun Chopra")
                .jsonPath("$.books[1].price").isEqualTo(15.59)
                .jsonPath("$.books[1].lastUpdated").exists()
                .jsonPath("$.books[2].id").isEqualTo(3)
                .jsonPath("$.books[2].title").isEqualTo("1984")
                .jsonPath("$.books[2].author").isEqualTo("George Orwell")
                .jsonPath("$.books[2].price").isEqualTo(26.41)
                .jsonPath("$.books[2].lastUpdated").exists();
    }

    @Test
    @Order(7)
    void testDelete() {
        Long bookId = 3L;

        client.delete().uri("/api/books/" + bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_DELETE_MESSAGE)
                .jsonPath("$.timestamp").exists();

        client.get().uri("/api/books").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.SUCCESS_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.SUCCESS_BOOK_LIST_OR_GET_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.books").isArray()
                .jsonPath("$.books").value(hasSize(2));

        client.get().uri("/api/books/" + bookId).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(Constants.ERROR_OPERATION_CODE)
                .jsonPath("$.message").isEqualTo(Constants.BOOK_DOES_NOT_EXIST_MESSAGE)
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.book").doesNotExist();
    }


}