package be.ucll.unit.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.Loan;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;
import be.ucll.service.LoanService;

public class LoanServiceTest {

    private LoanService loanService;
    private LoanRepository loanRepository;

    @BeforeEach
    public void setUp() {
        loanRepository = new LoanRepository();
        loanService = new LoanService(loanRepository, new UserRepository());
    }

    @Test
    public void givenRepoWithLoansForUser_whenSearchingForLoans_thenLoanIsReturned() {
        List<Loan> result = loanService.getLoansByUser("jane.toe@ucll.be", false);

        assertEquals(2, result.size());
        assertTrue(result.contains(loanRepository.loans.get(1)));
        assertTrue(result.contains(loanRepository.loans.get(2)));
    }

    @Test
    public void givenUserAndOnlyActive_whenGetLoansByUser_thenOnlyActiveLoansByUserAreReturned() {
        List<Loan> result = loanService.getLoansByUser("jane.toe@ucll.be", true);

        assertEquals(0, result.size());
    }

    @Test
    public void givenUserAndNoActiveLoans_whenGetLoansByUser_thenLoansByUserAreReturned() {
        List<Loan> result = loanService.getLoansByUser("john.doe@ucll.be", true);

        assertEquals(0, result.size());
    }

    @Test
    public void givenNonExistingUser_whenGetLoansByUser_thenAnExceptionIsThrown() {
        Exception ex = assertThrows(RuntimeException.class,
                () -> loanService.getLoansByUser("NonExistingUser", false));

        assertEquals("User not found.", ex.getMessage());
    }
}