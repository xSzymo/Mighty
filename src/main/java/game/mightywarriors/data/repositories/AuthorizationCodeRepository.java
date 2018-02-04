package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.AuthorizationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface AuthorizationCodeRepository extends CrudRepository<AuthorizationCode, Long> {
    HashSet<AuthorizationCode> findAll();

    AuthorizationCode findById(long id);

    void deleteById(long id);
}