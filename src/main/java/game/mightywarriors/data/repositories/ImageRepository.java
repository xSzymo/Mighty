package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    HashSet<Image> findAll();

    Image findById(long id);

    void deleteById(long id);

    Image findByUrl(String url);
}