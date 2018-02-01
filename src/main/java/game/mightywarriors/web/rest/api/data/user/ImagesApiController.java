package game.mightywarriors.web.rest.api.data.user;


import game.mightywarriors.data.services.ImageService;
import game.mightywarriors.data.tables.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ImagesApiController {
    @Autowired
    private ImageService service;

    @GetMapping("images")
    public Set<Image> getImages() {
        return service.findAll();
    }

    @GetMapping("images/{id}")
    public Image getImage(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
