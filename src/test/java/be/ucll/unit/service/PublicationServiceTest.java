package be.ucll.unit.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.Book;
import be.ucll.model.Publication;
import be.ucll.repository.PublicationRepository;
import be.ucll.service.PublicationService;

public class PublicationServiceTest {

    private PublicationService publicationService;
    private PublicationRepository publicationRepository;

    @BeforeEach
    public void setUp() {
        publicationRepository = new PublicationRepository();
        publicationService = new PublicationService(publicationRepository);
    }


    @Test
    public void givenBooksInRepo_whenSearchForPublicationsOfTypeBook_thenBooksAreReturned() {
        List<Publication> books = publicationService.filterPublicationsByTitleAndType("", "Book");

        assertEquals(2, books.size());
        assertTrue(books.contains(new Book("The Catcher in the Rye", "J.D. Salinger", "0123456", 1951, 5)));
    }

    @Test
    public void givenPublicationsInRepo_whenSearchingForPublicationWithTitle_thenPublicationsAreReturned() {
        List<Publication> publications = publicationService.filterPublicationsByTitleAndType("th", "");
        assertEquals(2, publications.size());

        List<Publication> publicationsTH = publicationService.filterPublicationsByTitleAndType("TH", "");
        assertEquals(2, publicationsTH.size());

    }

    @Test
    public void givenPublicationsInRepo_whenSearchingForPublicationWithTitle_thenEmptyListIsReturned() {
        List<Publication> publications = publicationService.filterPublicationsByTitleAndType("X", "");
        assertEquals(0, publications.size());
    }

    @Test
    public void givenPublicationsInRepo_whenSearchingForPublicationWithTitleAndOfType_thenPublicationsAreReturned() {
        List<Publication> publications = publicationService.filterPublicationsByTitleAndType("Th", "Magazine");
        assertEquals(1, publications.size());
    }

    @Test
    public void givenNoPublicationsInRepo_whenSearchingForPublicationWithTitleAndOfType_thenEmptyListIsReturned() {
        publicationRepository.setBooks(List.of());
        publicationRepository.setMagazines(List.of());

        List<Publication> publications = publicationService.filterPublicationsByTitleAndType("Th", "Book");
        assertEquals(0, publications.size());
    }

    @Test
    public void givenPublicationsInRepo_whenSearchingForPublicationsWithMinimalNumberOfCopies_thenPublicationsAreReturned() {
        List<Publication> publications = publicationService.getPublicationsWithMinimalNumberOfCopies(4);
        assertEquals(2, publications.size());
    }

    @Test
    public void givenNoPublicationsInRepo_whenSearchingForPublicationWithMinimalNumberOfCopies_thenEmptyListIsReturned() {
        publicationRepository.setBooks(List.of());
        publicationRepository.setMagazines(List.of());

        List<Publication> publications = publicationService.getPublicationsWithMinimalNumberOfCopies(4);
        assertEquals(0, publications.size());
    }
}
