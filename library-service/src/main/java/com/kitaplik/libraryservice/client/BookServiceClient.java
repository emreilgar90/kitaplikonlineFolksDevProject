package com.kitaplik.libraryservice.client;

import com.kitaplik.libraryservice.dto.BookDto;
import com.kitaplik.libraryservice.dto.BookIdDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name="book-service", path="/v1/book")
public interface BookServiceClient {
    /**
     * Feign Client ta oluşacak hataları resillience4j ile çözümleyip log'luyoruz ve db'e kaydediyoruz.
     */
    Logger logger = LoggerFactory.getLogger(BookServiceClient.class);

    @GetMapping("/isbn/{isbn}")
    @CircuitBreaker(name = "getBookByIsbnCircuitBreaker", fallbackMethod = "getBookFallBack")
    /****resillience4j den gelen anatasyon @CircuitBreaker!***/
    ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable String isbn);

    default ResponseEntity<BookIdDto> getBookFallback(String isbn, Exception exception) {
        logger.info("Book not found by isbn " + isbn + ",returning default BookDto Object");
        return ResponseEntity.ok(new BookIdDto("default-book", "default-isbn"));
    }

    @GetMapping("/book/{BookId}")
    @CircuitBreaker(name = "getBookByIdCircuitBreaker", fallbackMethod = "getBookByIdFallBack")
    ResponseEntity<BookDto> getBookById(@PathVariable String bookId);

    default ResponseEntity<BookDto> getBookFallBack(String bookId, Exception exception) {
        logger.info("Book not found by isbn " + bookId + ",returning default BookDto Object");
        return ResponseEntity.ok(new BookDto(new BookIdDto("default-book", "default-isbn")));
    }
}
