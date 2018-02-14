package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.GuildRepository;
import game.mightywarriors.data.tables.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class GuildService {
    @Autowired
    private GuildRepository repository;
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    public void save(Guild guild) {
        if (guild != null) {
            if (guild.getChat() != null)
                chatService.save(guild.getChat());

            guild.getInvites().forEach(requestService::save);
            repository.save(guild);
        }
    }

    public void save(Collection<Guild> guilds) {
        guilds.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Guild find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Guild find(String name) {
        try {
            return repository.findByName(name);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Guild find(Guild guild) {
        try {
            return find(guild.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Guild> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        Guild guild = repository.findById(id);
        if (guild != null) {
            chatService.delete(guild.getChat());
            guild.getUsers().forEach(user -> user.setGuild(null));
            guild.getUsers().forEach(userService::save);

            repository.deleteById(id);
        }
    }

    public void delete(String name) {
        try {
            delete(find(name));
        } catch (NullPointerException e) {

        }
    }

    public void delete(Guild guild) {
        try {
            delete(guild.getId());
        } catch (NullPointerException e) {

        }
    }

    public void deleteAll() {
        delete(findAll());
    }

    public void delete(Collection<Guild> guilds) {
        guilds.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}
