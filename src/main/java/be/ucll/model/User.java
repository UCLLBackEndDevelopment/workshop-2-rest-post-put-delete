package be.ucll.model;

import java.util.Objects;

public class User {

    private String name;
    private int age;
    private String email;
    private String password;

    public User(String name, int age, String email, String password) {
        setName(name);
        setAge(age);
        setEmail(email);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Name is required");
        }

        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 101) {
            throw new RuntimeException("Age must be a positive integer between 0 and 101");
        }

        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains(".") || !email.contains("@")) {
            throw new RuntimeException("E-mail must be a valid email format");
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }

        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email, password);
    }
}
