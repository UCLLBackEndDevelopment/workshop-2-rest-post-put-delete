package be.ucll.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Loan {

    private User user;
    private List<Publication> publications;
    private LocalDate startDate;
    private LocalDate endDate;

    public Loan(User user, List<Publication> publications, LocalDate startDate) {
        setUser(user);
        setStartDate(startDate);
        setEndDate(startDate.plusDays(21));
        setPublications(publications);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            throw new RuntimeException("User is required");
        }

        this.user = user;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        if (publications == null || publications.isEmpty()) {
            throw new RuntimeException("List is required");
        }

        // Making sure that there is no issue for all publications of the loan
        // Otherwise we could already have decreased availability for some publications before encountering an error
        for (Publication publication : publications) {
            if (publication.getAvailableCopies() == 0) {
                throw new RuntimeException("Unable to lend publication. No copies available for " + publication.getTitle());
            }
        }

        this.publications = new ArrayList<>(publications); // make a **new** list with the same publications
        for (Publication publication : publications) {
            publication.lendPublication();
        }
    }

    public void returnPublications() {
        for (Publication publication : publications) {
            publication.returnPublication();
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new RuntimeException("Start date is required");
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Start date cannot be in the future");
        }

        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "user=" + user +
                ", publications=" + publications +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(user, loan.user) && Objects.equals(publications, loan.publications) && Objects.equals(startDate, loan.startDate) && Objects.equals(endDate, loan.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, publications, startDate, endDate);
    }
}
