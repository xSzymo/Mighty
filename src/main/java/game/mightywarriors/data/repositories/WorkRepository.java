package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface WorkRepository extends CrudRepository<Work, Long> {
    Work findById(long id);

    HashSet<Work> findByNickname(String nickname);

    HashSet<Work> findAll();
}