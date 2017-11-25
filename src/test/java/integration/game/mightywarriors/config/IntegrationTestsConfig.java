package integration.game.mightywarriors.config;

import game.mightywarriors.MightyWarriorsApplication;
import game.mightywarriors.configuration.system.SystemVariablesManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MightyWarriorsApplication.class)
public abstract class IntegrationTestsConfig {

    @BeforeClass
    public static void setUpBefore() {
        SystemVariablesManager.RUNNING_TESTS = true;
    }
}
