package be.ucll.controller;

import be.ucll.model.Loan;
import be.ucll.model.User;
import be.ucll.service.LoanService;
import be.ucll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private UserService userService;
    private LoanService loanService;

    @Autowired
    public UserRestController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) String name) {
        return userService.getUsersByName(name);
    }

    @GetMapping("/adults")
    public List<User> getAdults() {
        return userService.getAllAdultUsers();
    }

    @GetMapping("age/{min}/{max}")
    public List<User> getUsersBetweenAge(@PathVariable int min, @PathVariable int max) {
        return userService.getUsersBetweenAge(min, max);
    }

    @GetMapping("/{email}/loans")
    public List<Loan> getLoansByUser(@PathVariable("email") String email,
                                     @RequestParam(value = "onlyActive", required = false) Boolean onlyActive) {

        return loanService.getLoansByUser(email, onlyActive);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{email}")
    public User updateUser(@PathVariable("email") String email, @RequestBody User user) {
        return userService.updateUser(email, user);
    }

    @DeleteMapping("/{email}/loans")
    public String deleteLoansByUser(@PathVariable("email") String email) {
        loanService.deleteLoansByUser(email);
        return "Loans of user successfully deleted.";
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable("email") String email) {
        userService.deleteUser(email);
        return "User successfully deleted.";
    }

}
