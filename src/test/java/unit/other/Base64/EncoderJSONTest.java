package unit.other.Base64;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.other.generators.RandomCodeFactory;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncoderJSONTest {
    private String uniqueCode;

    @After
    public void cleanUp() {
        SystemVariablesManager.JWTTokenCollection.remove(uniqueCode);
    }

    @Test
    public void encode() {
        RandomCodeFactory randomCodeFactory = new RandomCodeFactory();
        uniqueCode = randomCodeFactory.getUniqueCode();

        String jsonCode = SystemVariablesManager.ENCODER_JSON.encode(uniqueCode);
        String dbCode = SystemVariablesManager.ENCODER_DB.encode(uniqueCode);

        assertNotEquals(jsonCode, dbCode);
        assertEquals(uniqueCode, SystemVariablesManager.DECO4DER_DB.decode(dbCode));
        assertEquals(uniqueCode, SystemVariablesManager.DECODER_JSON.decode(jsonCode));
    }
}