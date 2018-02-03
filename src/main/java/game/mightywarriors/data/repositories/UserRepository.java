package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Inventory;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);

    HashSet<User> findAll();

    void deleteById(long id);

    User findByLogin(String login);

    User findByEMail(String email);

    User findByShop(Shop shop);

    User findByCodeToEnableAccount(String code);

    User findByInventory(Inventory inventory);

    @Query(value = "SELECT u.codeToEnableAccount FROM User u")
    Set<String> findAllCodesToEnableAccount();
}