package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.LinkedList;

@Repository
@Transactional
public interface MessageRepository extends CrudRepository<Message, Long> {
    Message findById(long id);

    LinkedList<Message> findAll();

    void deleteById(Long id);
}