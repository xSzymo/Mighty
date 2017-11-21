package game.mightywarriors.other.generators;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RandomCodeFactoryTest {
    @Test
    public void getUniqueCodeTest() {
        RandomCodeFactory objectUnderTest = new RandomCodeFactory();

        for (int i = 1; i < 10000; i++) {
            String uniqueCode = objectUnderTest.getUniqueCode();
            if (!SystemVariablesManager.JWTTokenCollection.contains(uniqueCode))
                fail("fail");

            assertEquals(i, SystemVariablesManager.JWTTokenCollection.size());
        }
    }
}
