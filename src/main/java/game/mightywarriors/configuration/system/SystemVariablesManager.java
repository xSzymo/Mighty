package game.mightywarriors.configuration.system;

import game.mightywarriors.other.Base64.DecoderDB;
import game.mightywarriors.other.Base64.DecoderJSON;
import game.mightywarriors.other.Base64.EncoderDB;
import game.mightywarriors.other.Base64.EncoderJSON;

import java.util.LinkedList;

public final class SystemVariablesManager {
    SystemVariablesManager() {
    }

    ;

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

    public final class Levels {
        public static final long ONE = 10;
        public static final long TWO = 20;
        public static final long THREE = 30;
        public static final long FOUR = 40;
        public static final long FIVE = 50;
        public static final long SIX = 60;
        public static final long SEVEN = 70;
        public static final long EIGHT = 80;
        public static final long NINE = 90;
        public static final long TEN = 100;

        public static final long ELEVEN = 100;
        public static final long TWELVE = 100;
        public static final long THIRTEEN = 100;
        public static final long FOURTEEN = 100;
        public static final long FIFTEEN = 100;
        public static final long SIXTEEN = 100;
        public static final long SEVENTEEN = 100;
        public static final long EIGHTEEN = 100;
        public static final long NINETEEN = 100;

        public static final long TWENTY = 100;
        public static final long TWENTY_ONE = 100;
        public static final long TWENTY_TWO = 100;
        public static final long TWENTY_THREE = 100;
        public static final long TWENTY_FOUR = 100;
        public static final long TWENTY_FIVE = 100;
        public static final long TWENTY_SIX = 100;
        public static final long TWENTY_SEVEN = 100;
        public static final long TWENTY_EIGHT = 100;
        public static final long TWENTY_NINE = 100;

        public static final long THIRTY = 100;
        public static final long THIRTY_ONE = 100;
        public static final long THIRTY_TWO = 100;
        public static final long THIRTY_THREE = 100;
        public static final long THIRTY_FOUR = 100;
        public static final long THIRTY_FIVE = 100;
        public static final long THIRTY_SIX = 100;
        public static final long THIRTY_SEVEN = 100;
        public static final long THIRTY_EIGHT = 100;
        public static final long THIRTY_NINE = 100;

        public static final long FORTY = 100;
        public static final long FORTY_ONE = 100;
        public static final long FORTY_TWO = 100;
        public static final long FORTY_THREE = 100;
        public static final long FORTY_FOUR = 100;
        public static final long FORTY_FIVE = 100;
        public static final long FORTY_SIX = 100;
        public static final long FORTY_SEVEN = 100;
        public static final long FORTY_EIGHT = 100;
        public static final long FORTY_NINE = 100;

        public static final long FIFTY = 100;
        public static final long FIFTY_ONE = 100;
        public static final long FIFTY_TWO = 100;
        public static final long FIFTY_THREE = 100;
        public static final long FIFTY_FOUR = 100;
        public static final long FIFTY_FIVE = 100;
        public static final long FIFTY_SIX = 100;
        public static final long FIFTY_SEVEN = 100;
        public static final long FIFTY_EIGHT = 100;
        public static final long FIFTY_NINE = 100;

        public static final long SIXTY = 100;
        public static final long SIXTY_ONE = 100;
        public static final long SIXTY_TWO = 100;
        public static final long SIXTY_THREE = 100;
        public static final long SIXTY_FOUR = 100;
        public static final long SIXTY_FIVE = 100;
        public static final long SIXTY_SIX = 100;
        public static final long SIXTY_SEVEN = 100;
        public static final long SIXTY_EIGHT = 100;
        public static final long SIXTY_NINE = 100;

        public static final long SEVENTY = 100;
        public static final long SEVENTY_ONE = 100;
        public static final long SEVENTY_TWO = 100;
        public static final long SEVENTY_THREE = 100;
        public static final long SEVENTY_FOUR = 100;
        public static final long SEVENTY_FIVE = 100;
        public static final long SEVENTY_SIX = 100;
        public static final long SEVENTY_SEVEN = 100;
        public static final long SEVENTY_EIGHT = 100;
        public static final long SEVENTY_NINE = 100;

        public static final long EIGHTY = 100;
        public static final long EIGHTY_ONE = 100;
        public static final long EIGHTY_TWO = 100;
        public static final long EIGHTY_THREE = 100;
        public static final long EIGHTY_FOUR = 100;
        public static final long EIGHTY_FIVE = 100;
        public static final long EIGHTY_SIX = 100;
        public static final long EIGHTY_SEVEN = 100;
        public static final long EIGHTY_EIGHT = 100;
        public static final long EIGHTY_NINE = 100;

        public static final long NINETY = 100;
        public static final long NINETY_ONE = 100;
        public static final long NINETY_TWO = 100;
        public static final long NINETY_THREE = 100;
        public static final long NINETY_FOUR = 100;
        public static final long NINETY_FIVE = 100;
        public static final long NINETY_SIX = 100;
        public static final long NINETY_SEVEN = 100;
        public static final long NINETY_EIGHT = 100;
        public static final long NINETY_NINE = 100;

        public static final long ONE_HUNDRED = 1000;
    }
}
