package main.java;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Random;

public class BaseConfig {

    protected String generateRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();


        return generatedString;
    }

    protected int generateRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    protected double generateRandomDouble(int min, int max) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        int rand_int = generateRandomInt(min, max);
        Random rand = new Random();
        double rand_double = rand.nextDouble();
        return Double.parseDouble(formatter.format(rand_int + rand_double));
    }

    protected java.util.Date generateRandomDate() {
        int year = generateRandomInt(1900, 2020);
        int month = generateRandomInt(0, 11);
        int day = generateRandomInt(0, 28);

        return new Date(year - 1900, month, day);
    }

    protected boolean checkPassword(char[] correctPass, char[] password) {
        if (correctPass.length != password.length){
            return false;
        }

        for (int i = 0; i < password.length; i++){
            if (password[i] != correctPass[i]){
                return false;
            }
        }

        return true;
    }
}
