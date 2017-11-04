package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ImageRepository;
import game.mightywarriors.data.tables.Image;
import game.mightywarriors.other.PictureOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository repository;
    @Autowired
    private PictureOperations pictureOperations;


    public void save(Image image) throws Exception {
        if (image == null)
            return;

        Image foundImage = repository.findByUrl(image.getUrl());
        if (foundImage != null)
            throw new Exception("delete exist image before save!");

        repository.save(image);
    }

    public void save(LinkedList<Image> images) throws Exception {
        for(Image image : images) {
            Image foundImage = repository.findByUrl(image.getUrl());
            if (foundImage != null)
                throw new Exception("delete exist image before save!");

            repository.save(image);
        }
    }

    public Image findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Image findOne(Image image) {
        try {
            return findOne(image.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Image> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        try {
            deleteOperation(repository.findById(id));
        } catch (NullPointerException e) {

        } catch (EmptyResultDataAccessException e) {

        }
    }

    public void delete(Image picture) {
        try {
            delete(picture.getId());
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Image> images) {
        images.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    private void deleteOperation(Image image) {
        try {
            pictureOperations.deletePicture(image);
            repository.deleteById(image.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}