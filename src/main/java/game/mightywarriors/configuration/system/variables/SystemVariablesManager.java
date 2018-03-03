package game.mightywarriors.configuration.system.variables;

import game.mightywarriors.other.base64.DecoderDB;
import game.mightywarriors.other.base64.DecoderJSON;
import game.mightywarriors.other.base64.EncoderDB;
import game.mightywarriors.other.base64.EncoderJSON;

import java.util.LinkedList;
import java.util.regex.Pattern;

public final class SystemVariablesManager {
    /**
     * check also ChampionTavern class and checkCanBuyChampion method which defines when user can buy champion
     */
    public static final long MINIMUM_GOLD_FOR_FIRST_CHAMPION = 10;
    public static final long MINIMUM_GOLD_FOR_SECOND_CHAMPION = 300;
    public static final long MINIMUM_GOLD_FOR_THIRD_CHAMPION = 1000;
    public static final long MINIMUM_LEVEL_FOR_FIRST_CHAMPION = 0;
    public static final long MINIMUM_LEVEL_FOR_SECOND_CHAMPION = 5;
    public static final long MINIMUM_LEVEL_FOR_THIRD_CHAMPION = 10;

    public static final String SPECIAL_JWT_SECRET_KEY = "K00LINN3R";
    public static final String NAME_OF_JWT_HEADER_TOKEN = "authorization";
    public static final String NAME_OF_SECRET_WORD_BEFORE_TOKEN = "Bearer ";

    public static final String EMAIL_HOST = "smtp.gmail.com";
    public static final int EMAIL_PORT = 587;
    public static final String EMAIL_USERNAME = "MightyWarriorsGame@gmail.com";
    public static final String EMAIL_PASSWORD = "Admin1234";
    public static final String EMAIL_TRANSPORT_PROTOCOL = "smtp";
    public static final String EMAIL_SMTP_AUTH = "true";
    public static final String EMAIL_SMTP_START_TLS_ENABLE = "true";
    public static final String EMAIL_DEBUG = "false";

    public static final String EMAIL_TEST_USERNAME = "testtest396@wp.pl";
    public static final String EMAIL_TEST_PASSWORD = "Admin1234";

    public static final int REGISTRATION_MINIMUM_PASSWORD_CHARS = 6;
    public static final int REGISTRATION_MAXIMUM_PASSWORD_CHARS = 24;
    public static final int REGISTRATION_MINIMUM_PASSWORD_UPPER_CASE = 1;
    public static final int REGISTRATION_MINIMUM_PASSWORD_LOWER_CASE = 4;
    public static final int REGISTRATION_MINIMUM_PASSWORD_DIGIT_CHARS = 1;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final int MAX_USERS_IN_GUILD = 30;

    public static final int EMAIL_REGISTRATION_CODE_EXPIRATION_TIME = 15;
    public static final int EMAIL_LOGIN_CODE_EXPIRATION_TIME = 15;
    public static final int EMAIL_PASSWORD_CODE_EXPIRATION_TIME = 15;
    public static final int EMAIL_EMAIL_CODE_EXPIRATION_TIME = 15;

    public static final String EMAIL_REGISTRATION_SUBJECT = "Mighty authentication code";
    public static final String EMAIL_REGISTRATION_MESSAGE = "Hello, welcome warrior ! Here is your authentication code : ";
    public static final String EMAIL_LOGIN_SUBJECT = "Mighty authentication code";
    public static final String EMAIL_LOGIN_MESSAGE = "Hello, welcome warrior ! Here is your authentication code : ";
    public static final String EMAIL_PASSWORD_SUBJECT = "Mighty authentication code";
    public static final String EMAIL_PASSWORD_MESSAGE = "Hello, welcome warrior ! Here is your authentication code : ";
    public static final String EMAIL_EMAIL_SUBJECT = "Mighty authentication code";
    public static final String EMAIL_EMAIL_MESSAGE = "Hello, welcome warrior ! Here is your authentication code : ";

    public static final String EMAIL_REMINDER_LOGIN_MESSAGE = "Hello, welcome warrior ! Here is your password: ";
    public static final String EMAIL_REMINDER_PASSWORD_MESSAGE = "Hello, welcome warrior ! Here is your login: ";

    public static final String INFORMATION_OF_DELETED_MESSAGE = "Message was deleted";

    public static LinkedList<String> JWTTokenCollection = new LinkedList<>();

    public static final int HOW_MANY_ITEMS_FOR_ONE_CHAMPION = 10;
    public static final int LAST_MESSAGES = 10;
    public static final int GOLD_FOR_STATISTIC_RATE = 10;
    public static final double PERCENT_FOR_SOLD_ITEM = 0.5;

    public static final double RATE = 0.1;
    public static final int HP_RATE = 3;

    public static final int NUMBER_ABOVE_ITEM = 5;
    public static final int MISSION_MONSTER_LEVEL_UNDER_CHAMPION_LEVEL = 3;

    public static final int HOW_MANY_MINUTES_BLOCK_ARENA_FIGHT = 1;
    public static final int ARENA_POINTS = 3;
    public static final int DUNGEON_POINTS = 1;

    public static final int GOLD_FROM_WORK = 10;

    public static final int MAX_FLOORS_PER_DUNGEON = 10;
    public static final int CHAMPION_LEVEL_FOR_FIRST_DUNGEON = 3;

    public static final int HOW_MANY_HOURS_TO_DELETE_USERS_WITH_EXPIRED_TOKENS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_NEXT_DRAW_ITEMS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_UPDATE_DIVISIONS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_REFRESH_MISSION_POINTS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_REFRESH_ARENA_POINTS = 1;
    public static final int HOW_MANY_HOURS_BETWEEN_REFRESH_DUNGEON_POINT = 1;

    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_1_AND_10 = 15;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_11_AND_20 = 14;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_21_AND_30 = 13;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_31_AND_40 = 12;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_41_AND_50 = 11;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_51_AND_60 = 10;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_61_AND_70 = 9;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_71_AND_80 = 8;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_81_AND_90 = 7;
    public static final int POINTS_MISSIONS_BETWEEN_LEVEL_91_AND_100 = 6;

    public static final int DEFAULT_STRENGTH_FOR_NEW_CHAMPION = 1;
    public static final int DEFAULT_INTELLIGENCE_FOR_NEW_CHAMPION = 1;
    public static final int DEFAULT_VITALITY_FOR_NEW_CHAMPION = 3;
    public static final int DEFAULT_CRITICAL_CHANCE_FOR_NEW_CHAMPION = 0;
    public static final int DEFAULT_ARMOR_FOR_NEW_CHAMPION = 1;
    public static final int DEFAULT_MAGIC_RESIST_FOR_NEW_CHAMPION = 1;

    public static final int NUMBER_OF_FIRST_CHAR_TO_DELETE = 6;
    public static final int NUMBER_OF_SECOND_CHAR_TO_DELETE = 14;
    public static final int NUMBER_OF_THIRD_CHAR_TO_DELETE = 16;
    public static final int NUMBER_OF_FOURTH_CHAR_TO_DELETE = 26;
    public static final int SECONDS_FOR_TOKEN_EXPIRED = 48 * 60 * 60;

    public static final DecoderDB DECO4DER_DB = new DecoderDB();
    public static final EncoderDB ENCODER_DB = new EncoderDB();
    public static final EncoderJSON ENCODER_JSON = new EncoderJSON();
    public static final DecoderJSON DECODER_JSON = new DecoderJSON();

    public static boolean INSERT_EXAMPLE_DATA = true;
}
