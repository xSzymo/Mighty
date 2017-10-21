package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    LinkedList<User> findAll();
}