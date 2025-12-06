package com.library.management.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookTest {

    @Test
    void testEqualsAndHashCode() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Test Book");
        book1.setAuthor("Test Author");
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setId(1L);
        book2.setTitle("Test Book");
        book2.setAuthor("Test Author");
        book2.setAvailable(true);

        // Powinny być równe (Lombok @Data generuje equals/hashCode na podstawie pól)
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());

        // Zmiana pola – nie równe
        book2.setTitle("Different Title");
        assertNotEquals(book1, book2);

        // Null safety
        book2.setTitle(null);
        assertNotEquals(book1, book2);
    }

    @Test
    void testGettersAndSetters() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setAvailable(false);

        assertEquals(1L, book.getId());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(false, book.isAvailable());
    }
}