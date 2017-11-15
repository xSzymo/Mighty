package game.mightywarriors.other.Base64;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class EncoderDB {
    public String encode(String text) {
        return Base64.encodeBase64String(StringUtils.getBytesUtf8(text));
    }
}
