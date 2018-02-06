package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ChatRepository;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ChatService {
    @Autowired
    private ChatRepository repository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    public void save(Chat chat) {
        if (chat == null)
            return;

        chat.getAdmins().forEach(adminService::save);
        chat.getMessages().forEach(messageService::save);
        repository.save(chat);
    }

    public void save(Set<Chat> messages) {
        messages.forEach(this::save);
    }

    public Chat find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Chat find(Chat message) {
        try {
            return find(message.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Chat> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Chat message) {
        try {
            deleteOperation(message);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Chat> messages) {
        messages.forEach(this::delete);
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Chat chat) {
        if (chat.getId() == null || find(chat.getId()) == null)
            return;

        removeChatsFromUsers(chat);
        repository.deleteById(chat.getId());
    }

    private void removeChatsFromUsers(Chat chat) {
        Iterator<User> iterator = find(chat.getId()).getUsers().iterator();
        LinkedList<Long> integers = new LinkedList<>();
        while (iterator.hasNext()) {
            try {
                integers.add(iterator.next().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Long x : integers) {
            try {
                User user = userService.find(x);
                user.getChats().remove(chat);
                chat.getUsers().remove(user);
                userService.save(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
