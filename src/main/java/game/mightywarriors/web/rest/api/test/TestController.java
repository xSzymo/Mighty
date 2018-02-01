package game.mightywarriors.web.rest.api.test;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.security.JSONLoginObject;
import game.mightywarriors.web.json.objects.security.JSONTokenObject;
import game.mightywarriors.web.rest.api.security.authorization.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

@RestController
public class TestController {
    @Autowired
    private FightCoordinator fightCoordinator;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private LoginController login;

    private String DATABASE_URL = "";
    private String DATABASE_USER_NAME = "";
    private String DATABASE_USER_PASSWORD = "";
    private String DATABASE_DRIVER = "";

    private String propFileName = "application.properties";
    private InputStream inputStream;
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

        try {
            testDataBaseAuthorization();
            testDataProvider.dataBaseAccess = true;
            testDataProvider = testDI(testDataProvider);
            testDataProvider.DIAccess = true;
            testLogin();
            testDataProvider.loginAccess = true;
        } catch (Exception e) {
            e.setStackTrace(new StackTraceElement[]{});
            return new TestDataProvider(e);
        }

        return testDataProvider;
    }

    private void testDataBaseAuthorization() throws Exception {
        loadDataBaseProperties();
        Class.forName(DATABASE_DRIVER);
        Connection con = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_USER_PASSWORD);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from USERS");

        int i = 0;
        while (rs.next())
            i++;

        if (i == 0)
            throw new Exception("There are not data in DB");

        con.close();
    }

    private TestDataProvider testDI(TestDataProvider testDataProvider) {
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

        testDataProvider.fight = new FightInformer(fightCoordinator.fight(myUser, myUser1));
        return testDataProvider;
    }

    private void testLogin() throws Exception {
        JSONTokenObject generate = login.generate(new JSONLoginObject(user1, user1));
        if (generate.id == 0 || generate.token == null || generate.token.equals(""))
            throw new Exception("User can't login");
    }

    private void loadDataBaseProperties() {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            }

            DATABASE_URL = prop.getProperty("spring.datasource.url");
            DATABASE_USER_NAME = prop.getProperty("spring.datasource.username");
            DATABASE_USER_PASSWORD = prop.getProperty("spring.datasource.password");
            DATABASE_DRIVER = prop.getProperty("spring.datasource.driver-class-name");

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public class TestDataProvider {
        public String user;
        public String addr;
        public String host;
        public long port;
        public boolean dataBaseAccess = false;
        public boolean DIAccess = false;
        public boolean loginAccess = false;
        public Exception thrownException;
        public FightInformer fight;

        public TestDataProvider() {

        }

        public TestDataProvider(Exception e) {
            this.thrownException = e;
        }
    }

    public class FightInformer {
        public FightResult fightResult;

        public FightInformer(FightResult fightResult) {
            this.fightResult = fightResult;
        }
    }
}
