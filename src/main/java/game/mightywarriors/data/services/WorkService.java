package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.WorkRepository;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class WorkService {
    @Autowired
    private WorkRepository repository;

    public void save(Work work) {
        if (work != null)
            saveOperation(work);
    }

    public void save(Collection<Work> works) {
        works.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Work work) {
        try {
            repository.save(work);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Work> find(String nickname) {
        try {
            return repository.findByNickname(nickname);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Work find(Work work) {
        try {
            return repository.findById((long) work.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Work find(Champion champion) {
        try {
            return repository.findByChampion(champion);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Work find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Work> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void delete(Work work) {
        try {
            delete(work.getId());
        } catch (NullPointerException e) {

        }
    }

    public void deleteAll() {
        delete(findAll());
    }

    public void delete(Collection<Work> works) {
        works.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}
