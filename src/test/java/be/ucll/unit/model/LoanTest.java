package be.ucll.unit.model;

import be.ucll.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class LoanTest {

    private User validUser;
    private Book validBook, bookWithNoCopies;
    private Magazine validMagazine;
    private List<Publication> validPublications, listWithBookWithNoCopies;
    private LocalDate validStartDate, validEndDate;

    @BeforeEach
    public void setUp() {
        validUser = new User("Amelia", 15, "amelia@ucll.be", "amelia1234");
        validBook = new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 4);
        validMagazine = new Magazine("National Geographic", "National Geographic Society", "978-1-4262-0034-5", 1888, 4);
        validPublications = List.of(validBook, validMagazine);

        bookWithNoCopies = new Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-2", 1937, 1);
        bookWithNoCopies.lendPublication();

        listWithBookWithNoCopies = List.of(bookWithNoCopies, validMagazine);

        validStartDate = LocalDate.now().minusYears(2);
        validEndDate = validStartDate.plusDays(21);
    }

    @Test
    public void givenValidInput_whenLoanIsCreated_thenAllFieldsHaveCorrectValues() {
        Loan loan = new Loan(validUser, validPublications, validStartDate, validEndDate);
        Assertions.assertEquals(validUser, loan.getUser());
        Assertions.assertEquals(validPublications, loan.getPublications());
        Assertions.assertEquals(validStartDate, loan.getStartDate());
        Assertions.assertEquals(validEndDate, loan.getEndDate());
        Assertions.assertEquals(3, validBook.getAvailableCopies());
        Assertions.assertEquals(3, validMagazine.getAvailableCopies());
    }

    @Test
    public void givenListWithBookWithNoCopies_whenLoanIsCreated_thenErrorIsThrownAndMagazineIsNotRented() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Loan(validUser, listWithBookWithNoCopies, validStartDate, validEndDate));
        Assertions.assertEquals("Unable to lend publication. No copies available for The Hobbit", ex.getMessage());
        Assertions.assertEquals(4, validMagazine.getAvailableCopies());
        Assertions.assertEquals(0, bookWithNoCopies.getAvailableCopies());
    }

    @Test
    public void givenInvalidEndOrStartDate_whenLoanIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Loan(validUser, validPublications, null, validEndDate));

        Assertions.assertEquals("Start date is required", ex.getMessage());

        ex = Assertions.assertThrows(RuntimeException.class,
                () -> new Loan(validUser, validPublications, LocalDate.now().plusDays(1), validEndDate));

        Assertions.assertEquals("Start date cannot be in the future", ex.getMessage());
    }

    @Test
    public void givenEmptyListOfPublication_whenLoanIsCreated_thenErrorIsThrown() {
        Exception ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> new Loan(validUser, List.of(), validStartDate, validEndDate));

        Assertions.assertEquals("List is required", ex.getMessage());
    }

    @Test
    public void givenValidInput_whenLoanIsCreated_thenTheNumberOfAvailableCopiesIsDecreased() {
        // given
        Assertions.assertEquals(4, validBook.getAvailableCopies());
        Assertions.assertEquals(4, validMagazine.getAvailableCopies());
        // when
        Loan loan = new Loan(validUser, validPublications, validStartDate, validEndDate);
        // then
        Assertions.assertEquals(3, validBook.getAvailableCopies());
        Assertions.assertEquals(3, validMagazine.getAvailableCopies());
    }

    @Test
    public void givenLoanWithPublication_whenPublicationsAreReturned_thenAvailableCopiesAreIncreased() {
        // given
        Loan loan = new Loan(validUser, validPublications, validStartDate, validEndDate);
        Assertions.assertEquals(3, validBook.getAvailableCopies());
        Assertions.assertEquals(3, validMagazine.getAvailableCopies());
        // when
        loan.returnPublications();
        // then
        Assertions.assertEquals(4, validBook.getAvailableCopies());
        Assertions.assertEquals(4, validMagazine.getAvailableCopies());
    }
}
