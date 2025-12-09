package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 1. Stworzono najpierw test bez funkcji BorrowBook (RED)
// 2. Dodano metodę BorrowBook do BookService (GREEN)
// 3. Dokonano refaktoryzacji kodu (REFACTOR)

@ExtendWith(MockitoExtension.class)
class BookServiceTddTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void borrowBook_ShouldSetBookUnavailable() {
        Book book = new Book();
        book.setId(1L);
        book.setAvailable(true);

        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.borrowBook(1L, 1L);

        assertFalse(result.isAvailable());
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookUnavailable() {
        Book book = new Book();
        book.setId(1L);
        book.setAvailable(false);

        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.borrowBook(1L, 1L));
    }
}
