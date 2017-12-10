package game.mightywarriors.other.Base64;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.other.generators.RandomCodeFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncoderJSONTest {

    @Test
    public void encode() {
        RandomCodeFactory randomCodeFactory = new RandomCodeFactory();
        String uniqueCode = randomCodeFactory.getUniqueCode();

        String jsonCode = SystemVariablesManager.ENCODER_JSON.encode(uniqueCode);
        String dbCode = SystemVariablesManager.ENCODER_DB.encode(uniqueCode);

        assertNotEquals(jsonCode, dbCode);
        assertEquals(uniqueCode, SystemVariablesManager.DECO4DER_DB.decode(dbCode));
        assertEquals(uniqueCode, SystemVariablesManager.DECODER_JSON.decode(jsonCode));
    }
}