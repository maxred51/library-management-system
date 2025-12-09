package com.library.management;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.UserRepository;
import com.library.management.service.BookService;
import com.library.management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LibraryIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private Book testBook;
    private User testUser;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        userRepository.deleteAll();

        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Author");
        testBook.setAvailable(true);
        testBook = bookService.saveBook(testBook);

        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setRole("USER");
        testUser = userService.saveUser(testUser);
    }

    @Test
    void testDatabaseConnectionAndSaveBook() {
        List<Book> books = bookService.getAllBooks();
        assertThat(books).isNotEmpty();
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }

    @Test
    void testFindBookById() {
        Optional<Book> bookOpt = bookService.getBookById(testBook.getId());
        assertTrue(bookOpt.isPresent());
        assertEquals("Test Book", bookOpt.get().getTitle());
    }

    @Test
    void testBorrowBook() {
        Book borrowedBook = bookService.borrowBook(testUser.getId(), testBook.getId());
        assertFalse(borrowedBook.isAvailable());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                bookService.borrowBook(testUser.getId(), testBook.getId()));
        assertEquals("Book already borrowed", exception.getMessage());
    }

    @Test
    void testChangeUserPassword() {
        User updatedUser = userService.changePassword(testUser.getId(), "password123", "newpass123");
        assertEquals("newpass123", updatedUser.getPassword());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userService.changePassword(testUser.getId(), "wrongOldPass", "anotherpass"));
        assertEquals("Old password incorrect", exception.getMessage());
    }

    @Test
    void testDeleteBook() {
        bookService.deleteBook(testBook.getId());
        assertFalse(bookService.getBookById(testBook.getId()).isPresent());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(testUser.getId());
        assertFalse(userService.getUserById(testUser.getId()).isPresent());
    }
}

