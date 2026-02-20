package be.ucll.model;

import java.util.Objects;

public class Book extends Publication {

    private String author;
    private String isbn;

    public Book(String title, String author, String isbn, int pubYear, int availableCopies) {
        super(title, pubYear, availableCopies);
        setAuthor(author);
        setIsbn(isbn);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new RuntimeException("Author is required");
        }

        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new RuntimeException("ISBN is required");
        }

        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "Book{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, isbn);
    }
}
