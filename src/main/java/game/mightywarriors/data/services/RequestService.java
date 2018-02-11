package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.RequestRepository;
import game.mightywarriors.data.tables.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RequestService {
    @Autowired
    private RequestRepository repository;

    public void save(Request request) {
        if (request != null)
            repository.save(request);
    }

    public void save(Set<Request> requests) {
        requests.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Request find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Request find(Request request) {
        try {
            return find(request.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Request> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Request request) {
        try {
            deleteOperation(request);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Request> requests) {
        requests.forEach(this::delete);
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Request request) {
        if (request.getId() == null || find(request.getId()) == null)
            return;

        repository.deleteById(request.getId());
    }
}
