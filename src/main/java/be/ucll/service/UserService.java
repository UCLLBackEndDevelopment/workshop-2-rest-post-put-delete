package be.ucll.service;

import be.ucll.model.Loan;
import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private LoanService loanService;

    @Autowired
    public UserService(UserRepository userRepository, LoanService loanService) {
        this.userRepository = userRepository;
        this.loanService = loanService;
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

        if(userOptional.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }
// 2 spaties
        User user = userOptional.get();

        user.setAge(updatedUser.getAge());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
 // savind the user, maakt ene nieuwe user aan:
        // 1.  curl -s http://localhost:8080/users
        // curl -s -X PUT http://localhost:8080/users/john.doe@ucll.be -H "Content-Type: application/json" -d '{"name":"John Updated","age":26,"email":"john.doe@ucll.be","password":"newpass123"}' deze word gedupliceerd
        // 3.  curl -s http://localhost:8080/users
        return user;
    }

    public void deleteUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }
 // 2 spaties
        User user = userOptional.get();

        List<Loan> activeLoans = loanService.getLoansByUser(email, true);
        if (activeLoans != null && !activeLoans.isEmpty())
            throw new RuntimeException("User has active loans.");

// Als een persoon geen loans heeft, dan konhij niet veriwjderd worden: probeer maar eens curl -s -X DELETE http://localhost:8080/users/birgit.doe@ucll.be in de andere branch
        List<Loan> allLoans = loanService.getLoansByUser(email, false);
        if (!allLoans.isEmpty()) {
            loanService.deleteLoansByUser(email);
        }
        userRepository.delete(user);
    }
}
