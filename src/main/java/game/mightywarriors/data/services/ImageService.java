package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ImageRepository;
import game.mightywarriors.data.tables.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository repository;

    public void save(Image image) {
        if (image != null)
            if (image.getUrl() != null)
                repository.save(image);
    }

    public void save(Collection<Image> images) {
        images.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
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

    public Iterable<Image> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void delete(Image image) {
        try {
            delete(image.getId());
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Image> addresses) {
        addresses.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}