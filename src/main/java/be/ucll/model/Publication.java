package be.ucll.model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Publication {

    private String title;
    private int pubYear;
    private int availableCopies;

    public Publication(String title, int pubYear, int availableCopies) {
        setTitle(title);
        setPubYear(pubYear);
        setAvailableCopies(availableCopies);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new RuntimeException("Title is required");
        }

        this.title = title;
    }

    public int getPubYear() {
        return pubYear;
    }

    public void setPubYear(int pubYear) {
        if (pubYear < 0) {
            throw new RuntimeException("Publication year must be a positive integer");
        }

        if (pubYear > LocalDate.now().getYear()) {
            throw new RuntimeException("Publication year cannot be in the future");
        }

        this.pubYear = pubYear;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 1) {
            throw new RuntimeException("Publication must have at least one copy");
        }

        this.availableCopies = availableCopies;
    }

    public Publication lendPublication() {
        if (getAvailableCopies() == 0) {
            throw new RuntimeException("No available copies left for publication");
        }

        availableCopies--;
        return this;
    }

    public void returnPublication() {
        availableCopies++;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", pubYear=" + pubYear +
                ", availableCopies=" + availableCopies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return pubYear == that.pubYear && availableCopies == that.availableCopies && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, pubYear, availableCopies);
    }
}
