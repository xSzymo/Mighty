package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.DivisionRepository;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.other.enums.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class DivisionService {
    @Autowired
    private DivisionRepository repository;

    public void save(Division division) {
        if (division != null)
            repository.save(division);
    }

    public void save(Collection<Division> divisions) {
        divisions.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Division find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Division find(Division division) {
        try {
            return find(division.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Division find(League league) {
        try {
            return repository.findByLeague(league);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Division> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        if (repository.findById(id) != null)
            repository.deleteById(id);
    }

    public void delete(Division division) {
        try {
            delete(division.getId());
        } catch (NullPointerException e) {

        }
    }

    public void deleteAll() {
        delete(findAll());
    }

    public void delete(Collection<Division> divisions) {
        divisions.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}
