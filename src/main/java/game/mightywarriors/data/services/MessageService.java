package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MessageRepository;
import game.mightywarriors.data.tables.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository repository;
    @Autowired
    private ChatService chatService;

    public void save(Message message) {
        if (message != null)
            repository.save(message);
    }

    public void save(List<Message> messages) {
        messages.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Message find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Message find(Message message) {
        try {
            return find(message.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Message> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Message message) {
        try {
            deleteOperation(message);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Message> messages) {
        messages.forEach(this::delete);
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Message message) {
        if (message.getId() == null || find(message.getId()) == null)
            return;

        repository.deleteById(message.getId());
    }
}
