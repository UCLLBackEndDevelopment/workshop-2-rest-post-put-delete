package be.ucll;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main( String[] args ) {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
