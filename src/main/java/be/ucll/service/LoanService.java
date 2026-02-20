package be.ucll.service;

import be.ucll.model.Loan;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private UserRepository userRepository;
    private LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<Loan> getLoansByUser(String email, boolean onlyActive) {
        if (!userRepository.userExists(email)) {
            throw new RuntimeException("User not found.");
        }

        return loanRepository.getLoansByUser(email, onlyActive);
    }

    public void deleteLoansByUser(String email) {
        List<Loan> allLoans = getLoansByUser(email, false);
        if (allLoans.isEmpty()) {
            throw new RuntimeException("User has no loans.");
        }

        List<Loan> loans = getLoansByUser(email, true);
        if (!loans.isEmpty())
            throw new RuntimeException("User has active loans.");

        loanRepository.deleteLoansByUserEmail(email);
    }
}
