package be.ucll.service;

import be.ucll.model.Loan;
import be.ucll.model.User;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private LoanRepository loanRepository;

    @Autowired
    public UserService(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    public List<User> getUsersByName(String name) {
        if (name == null || name.isBlank()) {
            return getAllUsers();
        };

        List<User> result = userRepository.getUsersByName(name);
        if (result.isEmpty()) {
            throw new RuntimeException("No users found with the specified name");
        }

        return result;
    }

    public List<User> getAllAdultUsers() {
        return userRepository.usersOlderThan(18);
    }

    public List<User> getUsersBetweenAge(int min, int max) {
        if (min > max) {
            throw new RuntimeException("Minimum age cannot be greater than maximum age");
        }

        if (max - min < 0 || max - min > 150) {
            throw new RuntimeException("Invalid age range. Age must be between 0 and 150.");
        }

        return userRepository.getUsersBetweenAge(min, max);
    }

    public User addUser(User user) {
        if (userRepository.userExists(user.getEmail())) {
            throw new RuntimeException("User already exists.");
        }
        return userRepository.save(user);
    }

    public User updateUser(String email, User updatedUser) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }

        User user  = userOptional.get();

        user.setAge(updatedUser.getAge());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());

        return user;
    }

    public void deleteUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }

        User user  = userOptional.get();

        List<Loan> activeLoans = loanRepository.getLoansByUser(email, true);
        if (activeLoans != null && !activeLoans.isEmpty())
            throw new RuntimeException("User has active loans.");

        loanRepository.deleteLoansByUserEmail(email);
        userRepository.delete(user);
    }
}
