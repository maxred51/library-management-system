package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Author");
        testBook.setAvailable(true);
    }

    @Test
    void getAllBooks() {
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
    }

    @Test
    void getBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void saveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book saved = bookService.saveBook(testBook);
        assertEquals("Test Book", saved.getTitle());
    }

    @Test
    void deleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAvailableBooks() {
        List<Book> available = Arrays.asList(testBook);
        when(bookRepository.findByAvailable(true)).thenReturn(available);

        List<Book> result = bookService.getAvailableBooks();
        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    void getAvailableBooks_None() {
        when(bookRepository.findByAvailable(true)).thenReturn(Arrays.asList());

        List<Book> result = bookService.getAvailableBooks();
        assertTrue(result.isEmpty());
    }
}