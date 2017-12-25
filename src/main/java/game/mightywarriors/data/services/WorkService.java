package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.WorkRepository;
import game.mightywarriors.data.tables.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

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

    public Work findOne(String nickname) {
        try {
            return repository.findByNickname(nickname);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Work findOne(Work work) {
        try {
            return findOne(work.getNickname());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Work> findAll() {
        return repository.findAll();
    }

    public void delete(String nickname) {
        if (repository.findByNickname(nickname) != null)
            repository.deleteById(nickname);
    }

    public void delete(Work work) {
        try {
            delete(work.getNickname());
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
