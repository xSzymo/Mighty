package unit.game.mightywarriors.other.generators;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.other.generators.RandomCodeFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import unit.game.mightywarriors.config.Unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Category(Unit.class)
public class RandomCodeFactoryTest {
    @Test
    public void getUniqueCodeTest() {
        RandomCodeFactory objectUnderTest = new RandomCodeFactory();

        for (int i = 1; i < 5000; i++) {
            String uniqueCode = objectUnderTest.getUniqueCode();
            if (!SystemVariablesManager.JWTTokenCollection.contains(uniqueCode))
                fail("fail");

            assertEquals(i, SystemVariablesManager.JWTTokenCollection.size());
        }
    }
}
