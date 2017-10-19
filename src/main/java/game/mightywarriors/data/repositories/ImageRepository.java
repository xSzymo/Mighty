package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Image;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
}