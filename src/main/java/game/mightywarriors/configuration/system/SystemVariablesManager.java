package game.mightywarriors.configuration.system;

import game.mightywarriors.other.Base64.DecoderDB;
import game.mightywarriors.other.Base64.DecoderJSON;
import game.mightywarriors.other.Base64.EncoderDB;
import game.mightywarriors.other.Base64.EncoderJSON;

import java.util.LinkedList;

public final class SystemVariablesManager {
    public static final String SPECIAL_JWT_SECRET_KEY = "K00LINN3R";
    public static final String NAME_OF_JWT_HEADER_TOKEN = "authorization";
    public static final String NAME_OF_SECRET_WORD_BEFORE_TOKEN = "Bearer ";

    public static final String EMAIL_HOST = "smtp.gmail.com";
    public static final int EMAIL_PORT = 587;
    public static final String EMAIL_USERNAME = "Admin1234";
    public static final String EMAIL_ADDRESS = "MightyWarriorsGame@gmail.com";
    public static final String EMAIL_TRANSPORT_PROTOCOL = "smtp";
    public static final String EMAIL_SMTP_AUTH = "true";
    public static final String EMAIL_SMTP_START_TLS_ENABLE = "true";
    public static final String EMAIL_DEBUG = "false";

    public static LinkedList<String> JWTTokenCollection = new LinkedList<>();

    public static final double RATE = 0.1;
    public static final int HP_RATE = 3;

    public static final int HOW_MANY_HOURS_BETWEEN_NEXT_DRAW_ITEMS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_UPDATE_DIVISIONS = 1;

    public static final int NUMBER_ABOVE_ITEM = 5;

    public static final int NUMBER_OF_FIRST_CHAR_TO_DELETE = 6;
    public static final int NUMBER_OF_SECOND_CHAR_TO_DELETE = 14;
    public static final int NUMBER_OF_THIRD_CHAR_TO_DELETE = 16;
    public static final int NUMBER_OF_FOURTH_CHAR_TO_DELETE = 26;
    public static final int SECONDS_FOR_TOKEN_EXPIRED = 48 * 60 * 60;

    public static final DecoderDB DECO4DER_DB = new DecoderDB();
    public static final EncoderDB ENCODER_DB = new EncoderDB();
    public static final EncoderJSON ENCODER_JSON = new EncoderJSON();
    public static final DecoderJSON DECODER_JSON = new DecoderJSON();

    public static boolean RUNNING_TESTS = false;
}
