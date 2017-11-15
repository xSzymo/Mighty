package game.mightywarriors.other.Base64;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class DecoderJSON {
    private static String deleteCharIntoSpecificPosition(String text, int position) {
        return text.substring(0, position) + text.substring(position + 1);
    }

    public String decode(String text) {
        text = StringUtils.newStringUtf8(Base64.decodeBase64(text));
        deleteCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_FIRST_CHAR_TO_DELETE);
        deleteCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_SECOND_CHAR_TO_DELETE);
        deleteCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_THIRD_CHAR_TO_DELETE);
        deleteCharIntoSpecificPosition(text, SystemVariablesManager.NUMBER_OF_FOURTH_CHAR_TO_DELETE);

        return text;
    }
}
