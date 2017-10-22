package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Override
    LinkedList<UserRole> findAll();

    UserRole findById(long id);
}