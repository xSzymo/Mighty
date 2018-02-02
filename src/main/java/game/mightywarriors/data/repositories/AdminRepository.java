package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.LinkedList;

@Repository
@Transactional
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findById(long id);

    LinkedList<Admin> findAll();

    void deleteById(Long id);
}