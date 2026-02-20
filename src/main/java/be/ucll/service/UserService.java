package be.ucll.service;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
