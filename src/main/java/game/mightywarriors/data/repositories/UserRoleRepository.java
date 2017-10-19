package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}