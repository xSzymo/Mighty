package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Long> {
    LinkedList<Statistic> findAll();

    Statistic findById(long id);

    void deleteById(long id);
}