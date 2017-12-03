package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ImageRepository;
import game.mightywarriors.data.tables.Image;
import game.mightywarriors.other.todo.PictureOperations;
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
    @Autowired
    private ItemService itemService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ChampionService championService;


    public void save(Image image) {
        if (image == null)
            return;

        repository.save(image);
    }

    public void save(Collection<Image> images) {
        for (Image image : images) {
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

    public Image findByUrl(String url) {
        try {
            return repository.findByUrl(url);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Image> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        try {
            Image image = repository.findById(id);
            if (image != null)
                deleteOperation(image);
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

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Image image) {
        try {
            itemService.findAll().forEach(
                    x -> {
                        if (x.getImage() != null) {
                            if (x.getImage().getId().equals(image.getId())) {
                                x.setImage(null);
                                itemService.save(x);
                            }
                        }
                    }
            );
            monsterService.findAll().forEach(
                    x -> {
                        if (x.getImage() != null) {
                            if (x.getImage().getId().equals(image.getId())) {
                                x.setImage(null);
                                monsterService.save(x);
                            }
                        }
                    }
            );
            championService.findAll().forEach(
                    x -> {
                        if (x.getImage() != null) {
                            if (x.getImage().getId().equals(image.getId())) {
                                x.setImage(null);
                                championService.save(x);
                            }
                        }
                    }
            );

            pictureOperations.deletePicture(image);
            repository.deleteById(image.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}