package game.mightywarriors.other.generators;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.AuthorizationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomCodeFactory {
    @Autowired
    private UserService userService;

    public String getUniqueToken() {
        boolean is = true;
        String code;

        do {
            code = coder();
            for (String x : SystemVariablesManager.JWTTokenCollection)
                if (x.equals(code))
                    is = false;
        } while (!is);

        SystemVariablesManager.JWTTokenCollection.add(code);
        return code;
    }

    public String getUniqueCodeToEnableAccount() {
        boolean is = true;
        String code;

        do {
            code = coder();
            for (String x : userService.findAllCodesToEnableAccount())
                if (x != null)
                    if (x.equals(code))
                        is = false;
        } while (!is);

        return code;
    }

    public String getUniqueCodeToAuthorizeChangeUserOperation() {
        boolean is = true;
        String code;

        do {
            code = coder();
            for (AuthorizationCode x : userService.findAllAuthorizationCodes())
                if (x != null)
                    if (x.getAuthorizationCode().equals(code))
                        is = false;
        } while (!is);

        return code;
    }

    public char getUniqueChar() {
        return coder().charAt(3);
    }

    private static String coder() {
        String[] tab = tab();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < tab.length; i++)
            code.append(tab[i]);

        return code.toString();
    }

    private static String[] tab() {
        String[] tab = new String[32];
        for (int i = 0; i < tab.length; i++)
            tab[i] = getUniqueToken(randomNumber(1, 60));
        return tab;
    }

    private static int randomNumber(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private static String getUniqueToken(int number) {
        if (number == 1)
            return "a";
        if (number == 2)
            return "A";
        if (number == 3)
            return "b";
        if (number == 4)
            return "B";
        if (number == 5)
            return "c";
        if (number == 6)
            return "C";
        if (number == 7)
            return "d";
        if (number == 8)
            return "D";
        if (number == 9)
            return "e";
        if (number == 10)
            return "E";
        if (number == 11)
            return "f";
        if (number == 12)
            return "F";
        if (number == 13)
            return "g";
        if (number == 14)
            return "G";
        if (number == 15)
            return "h";
        if (number == 16)
            return "H";
        if (number == 17)
            return "j";
        if (number == 18)
            return "J";
        if (number == 19)
            return "k";
        if (number == 20)
            return "K";
        if (number == 21)
            return "l";
        if (number == 22)
            return "L";
        if (number == 23)
            return "m";
        if (number == 24)
            return "M";
        if (number == 25)
            return "n";
        if (number == 26)
            return "N";
        if (number == 27)
            return "o";
        if (number == 28)
            return "O";
        if (number == 29)
            return "p";
        if (number == 30)
            return "P";
        if (number == 31)
            return "q";
        if (number == 32)
            return "Q";
        if (number == 33)
            return "r";
        if (number == 34)
            return "R";
        if (number == 35)
            return "s";
        if (number == 36)
            return "S";
        if (number == 37)
            return "t";
        if (number == 38)
            return "T";
        if (number == 39)
            return "u";
        if (number == 40)
            return "U";
        if (number == 41)
            return "v";
        if (number == 42)
            return "V";
        if (number == 43)
            return "w";
        if (number == 44)
            return "W";
        if (number == 45)
            return "x";
        if (number == 46)
            return "X";
        if (number == 47)
            return "y";
        if (number == 48)
            return "Y";
        if (number == 49)
            return "z";
        if (number == 50)
            return "Z";
        if (number == 51)
            return "1";
        if (number == 52)
            return "2";
        if (number == 53)
            return "3";
        if (number == 54)
            return "4";
        if (number == 55)
            return "5";
        if (number == 56)
            return "6";
        if (number == 57)
            return "7";
        if (number == 58)
            return "8";
        if (number == 59)
            return "9";
        if (number == 60)
            return "Y";
        return "@";
    }
}

