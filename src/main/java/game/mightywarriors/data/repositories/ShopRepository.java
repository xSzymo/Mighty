package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long> {
    HashSet<Shop> findAll();

    Shop findById(long id);

    void deleteById(long id);
}