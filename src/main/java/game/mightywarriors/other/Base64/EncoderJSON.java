package game.mightywarriors.other.Base64;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.other.generators.RandomCodeFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class EncoderJSON {
    private final RandomCodeFactory randomCodeFactory = new RandomCodeFactory();

    private static String insertCharIntoSpecificPosition(String text, int position, char myChar) {
        return text.substring(0, position) + myChar + text.substring(position, text.length());
    }

    public String encode(String text) {
        text = insertCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_FIRST_CHAR_TO_DELETE, randomCodeFactory.getUniqueChar());
        text = insertCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_SECOND_CHAR_TO_DELETE, randomCodeFactory.getUniqueChar());
        text = insertCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_THIRD_CHAR_TO_DELETE, randomCodeFactory.getUniqueChar());
        text = insertCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_FOURTH_CHAR_TO_DELETE, randomCodeFactory.getUniqueChar());

        return Base64.encodeBase64String(StringUtils.getBytesUtf8(text));
    }
}
