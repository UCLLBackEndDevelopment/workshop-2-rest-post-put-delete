package be.ucll.unit.model;

import be.ucll.model.Book;
import be.ucll.model.Publication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    @Test
    public void givenValidValues_whenBookIsCreated_thenBookIsCreatedWithThoseValues() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 4);
        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals("978-0-261-10295-2", book.getIsbn());
        assertEquals(1937, book.getPubYear());
        assertEquals(4, book.getAvailableCopies());
    }

    @Test
    public void givenInvalidTitle_whenBookIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book(null, "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 4));

        Assertions.assertEquals("Title is required", ex.getMessage());

        ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 4));

        Assertions.assertEquals("Title is required", ex.getMessage());
    }

    @Test
    public void givenInvalidAuthor_whenBookIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", null, "978-0-261-10295-2", 1937, 4));

        Assertions.assertEquals("Author is required", ex.getMessage());

        ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "", "978-0-261-10295-2", 1937, 4));

        Assertions.assertEquals("Author is required", ex.getMessage());
    }

    @Test
    public void givenInvalidISBN_whenBookIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "J.R.R. Tolkien", null, 1937, 4));

        Assertions.assertEquals("ISBN is required", ex.getMessage());

        ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "J.R.R. Tolkien", "", 1937, 4));

        Assertions.assertEquals("ISBN is required", ex.getMessage());
    }

    @Test
    public void givenInvalidYear_whenBookIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", -1937, 4));

        Assertions.assertEquals("Publication year must be a positive integer", ex.getMessage());

        ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", Year.now().getValue() + 1, 4));

        Assertions.assertEquals("Publication year cannot be in the future", ex.getMessage());
    }

    @Test
    public void givenInvalidAvailableCopies_whenBookIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, -4));

        Assertions.assertEquals("Publication must have at least one copy", ex.getMessage());
    }

    @Test
    public void givenValidBookWith2AvailableCopies_whenBookIsLent_thenAvailableCopiesIsDecreasedBy1() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 2);
        Publication lendedBook = book.lendPublication();
        assertEquals(1, book.getAvailableCopies());
        assertEquals(book, lendedBook);
    }

    @Test
    public void givenBookWith0AvailableCopies_whenBookIsLent_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 0).lendPublication());

        Assertions.assertEquals("Publication must have at least one copy", ex.getMessage());
    }

    @Test
    public void givenBookWith2AvailableCopies_whenBookIsReturned_thenAvailableCopiesIsIncreasedBy1() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 2);
        book.lendPublication();
        book.returnPublication();
        assertEquals(2, book.getAvailableCopies());
    }
}
