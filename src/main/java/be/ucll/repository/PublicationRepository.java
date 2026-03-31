package be.ucll.repository;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PublicationRepository {

    private List<Book> books;

    private List<Magazine> magazines;

    public PublicationRepository() {
        books = List.of(
                new Book("The Catcher in the Rye", "J.D. Salinger", "0123456", 1951, 5),
                new Book("1984", "George Orwell", "012587", 1949, 2));

        magazines = List.of(
                new Magazine("Mibelle", "DPG Media", "90123456", 1960, 3),
                new Magazine("Gistheren", "DPG Media", "954789", 1925, 4));
    }

    public List<Publication> getBooks() {
        return books.stream().map(book -> (Publication) book).toList();
    }

    public List<Publication> getMagazines() {
        return magazines.stream().map(magazine -> (Publication) magazine).toList();
    }

    public List<Publication> getPublications() {
        List<Publication> publications = new ArrayList<>();
        publications.addAll(books);
        publications.addAll(magazines);
        return publications;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }

    public List<Publication> filterPublicationsByTitleAndType(String title, String type) {
        if ((type == null || type.isBlank()) && (title == null || title.isBlank())) {
            return getPublications();
        }

        if (type == null || type.isBlank()) {
            return getPublications().stream()
                    .filter((p) -> p.getTitle().toLowerCase().contains(title.toLowerCase().strip()))
                    .toList();
        }

        if (title == null || title.isBlank()) {
            if (type.equals("Book")) {
                return getBooks();
            } else {
                return getMagazines();
            }
        } else {
            if (type.equals("Book")) {
                return getBooks().stream()
                        .filter((p) -> p.getTitle().toLowerCase().contains(title.toLowerCase().strip()))
                        .toList();
            } else {
                return getMagazines().stream()
                        .filter((p) -> p.getTitle().toLowerCase().contains(title.toLowerCase().strip()))
                        .toList();
            }
        }
    }

    public List<Publication> getPublicationsWithMinimalNumberOfCopies(int numberOfCopies) {
        return getPublications().stream()
                .filter(publication -> publication.getAvailableCopies() >= numberOfCopies)
                .toList();
    }
}
