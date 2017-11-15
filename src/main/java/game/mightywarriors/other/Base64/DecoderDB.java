package game.mightywarriors.other.Base64;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class DecoderDB {
    public String decode(String text) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(text));
    }
}
