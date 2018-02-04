package unit.other.generators;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.other.generators.RandomCodeFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RandomCodeFactoryTest {
    @Test
    public void getUniqueCodeTest() {
        RandomCodeFactory objectUnderTest = new RandomCodeFactory();

        for (int i = 1; i < 5000; i++) {
            String uniqueCode = objectUnderTest.getUniqueToken();
            if (!SystemVariablesManager.JWTTokenCollection.contains(uniqueCode))
                fail("fail");

            assertEquals(i, SystemVariablesManager.JWTTokenCollection.size());
        }
    }
}
