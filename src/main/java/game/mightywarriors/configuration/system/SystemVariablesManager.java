package game.mightywarriors.configuration.system;

import game.mightywarriors.other.Base64.DecoderDB;
import game.mightywarriors.other.Base64.DecoderJSON;
import game.mightywarriors.other.Base64.EncoderDB;
import game.mightywarriors.other.Base64.EncoderJSON;

import java.util.LinkedList;

public class SystemVariablesManager {

    public static final String SPECIAL_JWT_SECRET_KEY = "K00LINN3R";
    public static final String NAME_OF_JWT_HEADER_TOKEN = "authorization";
    public static final int HOW_MANY_HOURS_BETWEEN_NEXT_DRAW_ITEMS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_UPDAATE_DIVISIONS = 1;
    public static final double RATE = 0.1;
    public static final int HP_RATE = 3;

    public static LinkedList<String> JWTTokenCollection = new LinkedList<>();

    public static final long MAX_PLAYERS_IN_CHALLENGER = 2;
    public static final long MAX_PLAYERS_IN_DIAMOND = 5;
    public static final long MAX_PERCENT_PLAYERS_IN_GOLD = 10;
    public static final long MAX_PERCENT_PLAYERS_IN_SILVER = 20;
    public static final long MAX_PERCENT_PLAYERS_IN_BRONZE = 30;
    public static final long MAX_PERCENT_PLAYERS_IN_WOOD = 40;

    public static final long MIN_LEVEL_FOR_CHALLENGER = 100;
    public static final long MIN_LEVEL_FOR_DIAMOND = 60;
    public static final long MIN_LEVEL_FOR_GOLD = 50;
    public static final long MIN_LEVEL_FOR_SILVER = 40;
    public static final long MIN_LEVEL_FOR_BRONZE = 30;
    public static final long MIN_LEVEL_FOR_WOOD = 10;

    public static final long MIN_POINTS_FOR_CHALLENGER = 800;
    public static final long MIN_POINTS_FOR_DIAMOND = 500;
    public static final long MIN_POINTS_FOR_GOLD = 300;
    public static final long MIN_POINTS_FOR_SILVER = 100;
    public static final long MIN_POINTS_FOR_BRONZE = 50;
    public static final long MIN_POINTS_FOR_WOOD = 10;

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
