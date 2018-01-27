package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface ChatRepository extends CrudRepository<Chat, Long> {
    Chat findById(long id);

    HashSet<Chat> findAll();
}
