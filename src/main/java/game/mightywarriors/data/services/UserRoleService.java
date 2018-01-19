package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.UserRoleRepository;
import game.mightywarriors.data.tables.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class UserRoleService {
    @Autowired
    private UserRoleRepository repository;

    public void save(UserRole image) {
        if (image != null)
            repository.save(image);
    }

    public void save(Collection<UserRole> images) {
        images.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public UserRole find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public UserRole find(String role) {
        try {
            return repository.findByRole(role);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public UserRole find(UserRole image) {
        try {
            return find(image.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<UserRole> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        if (repository.findById(id) != null)
            repository.deleteById(id);
    }

    public void delete(UserRole image) {
        try {
            delete(image.getId());
        } catch (NullPointerException e) {

        }
    }

    public void deleteAll() {
        delete(findAll());
    }

    public void delete(Collection<UserRole> addresses) {
        addresses.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}
