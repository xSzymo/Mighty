package game.mightywarriors.web.rest.api.test;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.security.JSONLoginObject;
import game.mightywarriors.web.json.objects.security.JSONTokenObject;
import game.mightywarriors.web.rest.api.security.authorization.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
public class TestController {
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginController login;

    private String user1 = "bober";
    private String user2 = "szymson";
    private boolean loadNewUsers = true;

    @GetMapping
    public TestDataProvider test(HttpServletRequest request) {
        TestDataProvider testDataProvider = new TestDataProvider();
        testDataProvider.user = request.getRemoteUser();
        testDataProvider.addr = request.getRemoteAddr();
        testDataProvider.host = request.getRemoteHost();
        testDataProvider.port = request.getRemotePort();
        testDataProvider.time = new Timestamp(System.currentTimeMillis());

        try {
            testDataBase();
            testDataProvider.dataBaseAccess = true;
            testDI();
            testDataProvider.DIAccess = true;
            testLogin();
            testDataProvider.loginAccess = true;
        } catch (Exception e) {
            testDataProvider.thrownException = e;
            return testDataProvider;
        }
        return testDataProvider;
    }

    private void testDataBase() throws Exception {
        if (userService.findAll().size() == 0)
            throw new Exception("There are not data in DB");
    }

    private void testDI() {
        User myUser;
        User myUser1;

        if (loadNewUsers) {
            myUser = new User(user1, user1, user1);
            userService.save(myUser);
            myUser1 = new User(user2, user2, user2);
            userService.save(myUser1);
            loadNewUsers = false;
        }

        myUser = userService.find(user1);
        myUser1 = userService.find(user2);

        fightCoordinator.fight(myUser, myUser1);
    }

    private void testLogin() throws Exception {
        JSONTokenObject generate = login.generate(new JSONLoginObject(user1, user1));
        if (generate.id == 0 || generate.token == null || generate.token.equals(""))
            throw new Exception("User can't login");
    }

    public class TestDataProvider {
        public Timestamp time;
        public String user;
        public String addr;
        public String host;
        public long port;
        public boolean dataBaseAccess = false;
        public boolean DIAccess = false;
        public boolean loginAccess = false;
        public Exception thrownException;
    }
}
