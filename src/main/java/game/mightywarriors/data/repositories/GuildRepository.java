package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface GuildRepository extends CrudRepository<Guild, Long> {
    Guild findById(long id);

    Guild findByName(String name);

    HashSet<Guild> findAll();

    void deleteById(Long id);
}