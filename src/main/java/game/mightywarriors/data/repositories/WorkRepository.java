package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface WorkRepository extends CrudRepository<Work, Long> {
    Work findById(long id);

    List<Work> findByNickname(String nickname);

    LinkedList<Work> findAll();
}