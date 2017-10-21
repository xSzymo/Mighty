package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Image;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    @Override
    LinkedList<Image> findAll();
    Image findById(long id);
}