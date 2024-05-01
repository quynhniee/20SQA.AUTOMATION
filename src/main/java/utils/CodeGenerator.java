package utils;

import java.util.Random;

public class CodeGenerator {
    public static String generateCode() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code = "";

        for (int i = 0; i < 2; i++) {
            code += alphabet.charAt(random.nextInt(alphabet.length()));
        }

        for (int i = 0; i < 3; i++) {
            code += Integer.toString(random.nextInt(10));
        }

        return code;
    }
}