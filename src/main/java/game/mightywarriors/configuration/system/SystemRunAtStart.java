package game.mightywarriors.configuration.system;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.LinkedList;

@Configuration
public class SystemRunAtStart {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void runAtStart() {
        LinkedList<User> all = userService.findAll();
        all.forEach(x -> SystemVariablesManager.JWTTokenCollection.add(x.getTokenCode()));
    }
}
