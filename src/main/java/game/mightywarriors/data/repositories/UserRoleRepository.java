package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    HashSet<UserRole> findAll();

    UserRole findById(long id);

    UserRole findByRole(String role);

    void deleteById(long id);
}