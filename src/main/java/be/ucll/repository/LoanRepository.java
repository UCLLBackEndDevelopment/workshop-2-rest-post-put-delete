package be.ucll.repository;

import be.ucll.model.Loan;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LoanRepository {

    public List<Loan> loans;

    public LoanRepository() {
        loans = new ArrayList<>(List.of(new Loan(new UserRepository().getUsers().get(0),
                        List.of(new PublicationRepository().getBooks().get(0)), LocalDate.now(), LocalDate.now()),
                new Loan(new UserRepository().getUsers().get(1),
                        List.of(new PublicationRepository().getBooks().get(0)),
                        LocalDate.now(),
                        LocalDate.now()),
                new Loan(new UserRepository().getUsers().get(1),
                        List.of(new PublicationRepository().getBooks().get(1)),
                        LocalDate.now(),
                        null),
                new Loan(new UserRepository().getUsers().get(0),
                        List.of(new PublicationRepository().getBooks().get(1)),
                        LocalDate.now(),
                        LocalDate.now())));
    }

    public List<Loan> getLoansByUser(String email, boolean onlyActive) {
        return loans.stream()
                .filter(loan -> loan.getUser().getEmail().equals(email))
                .filter(loan -> !onlyActive || loan.getStartDate().isBefore(LocalDate.now()) && LocalDate.now().isAfter(loan.getEndDate()))
                .toList();
    }
}
