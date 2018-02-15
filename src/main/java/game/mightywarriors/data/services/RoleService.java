package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.RoleRepository;
import game.mightywarriors.data.tables.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public void save(Role image) {
        if (image != null)
            repository.save(image);
    }

    public void save(Collection<Role> images) {
        images.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Role find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Role find(String role) {
        try {
            return repository.findByRole(role);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Role find(Role image) {
        try {
            return find(image.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Role> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        if (repository.findById(id) != null)
            repository.deleteById(id);
    }

    public void delete(Role image) {
        try {
            delete(image.getId());
        } catch (NullPointerException e) {

        }
    }

    public void deleteAll() {
        delete(findAll());
    }

    public void delete(Collection<Role> addresses) {
        addresses.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}
