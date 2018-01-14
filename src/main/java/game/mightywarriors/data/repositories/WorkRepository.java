package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface WorkRepository extends CrudRepository<Work, Long> {
    Work findById(long id);

    HashSet<Work> findByNickname(String nickname);

    HashSet<Work> findAll();
}