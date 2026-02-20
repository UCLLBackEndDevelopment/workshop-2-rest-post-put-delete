package be.ucll.service;

import be.ucll.model.Publication;
import be.ucll.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    private PublicationRepository publicationRepository;

    @Autowired
    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<Publication> filterPublicationsByTitleAndType(String title, String type) {
        if (type == null || type.isBlank() || type.equals("Book") || type.equals("Magazine")) {
            return publicationRepository.filterPublicationsByTitleAndType(title, type);
        }

        throw new RuntimeException("Invalid type provided");
    }

    public List<Publication> getPublicationsWithMinimalNumberOfCopies(int numberOfCOpies) {
        return publicationRepository.getPublicationsWithMinimalNumberOfCopies(numberOfCOpies);
    }
}
