package game.mightywarriors.other.Base64;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class DecoderJSON {
    public String decode(String text) {
        text = StringUtils.newStringUtf8(Base64.decodeBase64(text));
        StringBuilder textToConvert = new StringBuilder(text);
        textToConvert.deleteCharAt(SystemVariablesManager.NUMBER_OF_FIRST_CHAR_TO_DELETE);
        textToConvert.deleteCharAt(SystemVariablesManager.NUMBER_OF_SECOND_CHAR_TO_DELETE - 1);
        textToConvert.deleteCharAt(SystemVariablesManager.NUMBER_OF_THIRD_CHAR_TO_DELETE - 2);
        textToConvert.deleteCharAt(SystemVariablesManager.NUMBER_OF_FOURTH_CHAR_TO_DELETE - 3);

        return textToConvert.toString();
    }
}
