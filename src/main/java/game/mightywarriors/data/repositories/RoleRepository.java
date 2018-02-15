package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {
    HashSet<Role> findAll();

    Role findById(long id);

    Role findByRole(String role);

    void deleteById(long id);
}