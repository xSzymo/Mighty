package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.ImageRepository;
import game.mightywarriors.data.tables.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class ImagesApiController {
    @Autowired
    ImageRepository imageRepository;

    @GetMapping("images")
    public LinkedList<Image> getImages() {
        return imageRepository.findAll();
    }

    @GetMapping("images/{id}")
    public Image getImage(@PathVariable("id") String id) {
        return imageRepository.findById(Long.parseLong(id));
    }
}
