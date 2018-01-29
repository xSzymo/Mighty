package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.AdminRepository;
import game.mightywarriors.data.repositories.MessageRepository;
import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository repository;
    @Autowired
    private ChatService chatService;

    public void save(Admin admin) {
        if (admin != null)
            repository.save(admin);
    }

    public void save(Set<Admin> admins) {
        admins.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Admin find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Admin find(Admin admin) {
        try {
            return find(admin.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Admin> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Admin admin) {
        try {
            deleteOperation(admin);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Admin> admins) {
        admins.forEach(this::delete);
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Admin admin) {
        if (admin.getId() == null || find(admin.getId()) == null)
            return;

        repository.deleteById(admin.getId());
    }
}
