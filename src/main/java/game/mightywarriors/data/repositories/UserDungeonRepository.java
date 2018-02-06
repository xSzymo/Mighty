package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.UserDungeon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface UserDungeonRepository extends CrudRepository<UserDungeon, Long> {
    UserDungeon findById(long id);

    UserDungeon findByDungeonId(long id);

    HashSet<UserDungeon> findAll();

    void deleteById(long id);
}
