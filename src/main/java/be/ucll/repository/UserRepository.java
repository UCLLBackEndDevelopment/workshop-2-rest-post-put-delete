package be.ucll.repository;

import be.ucll.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>(List.of(
                new User("John Doe", 25, "john.doe@ucll.be", "john1234"),
                new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234"),
                new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"),
                new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234"),
                new User("Birgit Doe", 18, "birgit.doe@ucll.be", "birgit1234")
        ));
    }

    public List<User> getUsers() {
        return users;
    }

    // For Unit Testing
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> usersOlderThan(int age) {
        List<User> result = new ArrayList<>();

        for (User user : users) {
            if (user.getAge() >= age) {
                result.add(user);
            }
        }

        return result;
    }

    public List<User> getUsersBetweenAge(int min, int max) {
        List<User> result = new ArrayList<>();

        for (User user : users) {
            if (user.getAge() >= min && user.getAge() <= max) {
                result.add(user);
            }
        }

        return result;
    }

    public List<User> getUsersByName(String name) {
        List<User> result = new ArrayList<>();

        for (User user : users) {
            if (user.getName().contains(name)) {
                result.add(user);
            }
        }

        return result;
    }

    public boolean userExists(String email) {
        return !users.stream().filter(user -> user.getEmail().equals(email)).toList().isEmpty();
    }

    public User save(User user) {
        this.users.add(user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public void delete(User user) {
        users.removeIf(u -> u.getEmail().equals(user.getEmail()));
    }
}
