package game.mightywarriors.configuration.system;

import game.mightywarriors.other.Base64.DecoderDB;
import game.mightywarriors.other.Base64.DecoderJSON;
import game.mightywarriors.other.Base64.EncoderDB;
import game.mightywarriors.other.Base64.EncoderJSON;

import java.util.LinkedList;

public class SystemVariablesManager {

    public static final String SPECIAL_JWT_SECRET_KEY = "K00LINN3R";
    public static final String NAME_OF_JWT_HEADER_TOKEN = "Authorization";

    public static final int NUMBER_OF_FIRST_CHAR_TO_DELETE = 6;
    public static final int NUMBER_OF_SECOND_CHAR_TO_DELETE = 14;
    public static final int NUMBER_OF_THIRD_CHAR_TO_DELETE = 16;
    public static final int NUMBER_OF_FOURTH_CHAR_TO_DELETE = 26;
    public static final int SECONDS_FOR_TOKEN_EXPIRED = 48*60*60;
    public static final DecoderDB DECO4DER_DB = new DecoderDB();
    public static final EncoderDB ENCODER_DB = new EncoderDB();

    public static final EncoderJSON ENCODER_JSON = new EncoderJSON();
    public static final DecoderJSON DECODER_JSON = new DecoderJSON();
    public static LinkedList<String> JWTTokenCollection = new LinkedList<>();
}
