package be.ucll.controller;

import be.ucll.model.Publication;
import be.ucll.service.PublicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
public class PublicationRestController {

    private PublicationService publicationService;

    public PublicationRestController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public List<Publication> filterPublicationsByTitleAndType(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type) {

        return publicationService.filterPublicationsByTitleAndType(title, type);
    }

    @GetMapping("stock/{availableCopies}")
    public List<Publication> getPublicationsWithMinimalNumberOfCopies(@PathVariable int availableCopies) {
        return publicationService.getPublicationsWithMinimalNumberOfCopies(availableCopies);
    }

}
