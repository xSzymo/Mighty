package game.mightywarriors.web.rest;


import game.mightywarriors.data.repositories.ImageRepository;
import game.mightywarriors.data.tables.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class Helo2 {
    @Autowired
    ImageRepository imageRepository;

    @GetMapping("image")
    public LinkedList<Image> halo() {
        return imageRepository.findAll();
    }

    @GetMapping("image/{id}")
    public Image halo1(@PathVariable("id") String id) {
        return imageRepository.findById(Long.parseLong(id));
    }


}