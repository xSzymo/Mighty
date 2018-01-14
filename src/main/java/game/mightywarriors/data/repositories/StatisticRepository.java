package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface StatisticRepository extends CrudRepository<Statistic, Long> {
    HashSet<Statistic> findAll();

    Statistic findById(long id);

    void deleteById(long id);
}