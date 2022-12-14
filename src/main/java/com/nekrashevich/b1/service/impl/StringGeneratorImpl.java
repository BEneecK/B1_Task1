package com.nekrashevich.b1.service.impl;

import com.nekrashevich.b1.service.StringGenerator;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntPredicate;

public class StringGeneratorImpl implements StringGenerator {
    private static final int DAYS_IN_YEAR = 365;
    private static final int COUNT_OF_YEARS = 5;
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @Override
    public String generate() {
        return date() + "||" + latinString() + "||" + cyrillicString() + "||" +
                integerNumber() + "||" + doubleNumber() + "||";
    }

    private String date() {
        Date date = Date.from(Instant.now().minus(Duration.ofDays(
                COUNT_OF_YEARS * DAYS_IN_YEAR)));
        long startMillis = date.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, Date.from(Instant.now()).getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(new Date(randomMillisSinceEpoch));
    }

    private String latinString() {
        int leftLimit = 65; // letter 'A'
        int rightLimit = 122; // letter 'z'

        //Символы между разными регистрами алфавита
        IntPredicate predicate = (n) -> n < 91 || n > 96;
        return randomASCIIString(leftLimit, rightLimit, predicate);
    }

    private String cyrillicString() {
        int leftLimit = 1025; // letter 'Ё'
        int rightLimit = 1105; // letter 'ё'

        //Символы между разными регистрами алфавита
        IntPredicate predicate = (n) -> n != 1104 && (n == 1025 || n >= 1040);
        return randomASCIIString(leftLimit, rightLimit, predicate);
    }

    private String randomASCIIString(int leftLimit, int rightLimit, IntPredicate predicate) {
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; ) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            if (predicate.test(randomLimitedInt)) {
                stringBuilder.append((char) randomLimitedInt);
                i++;
            }
        }
        return stringBuilder.toString();
    }

    private String integerNumber() {
        int startOfRange = 1;
        int endOfRange = 100_000_000;

        Random rand = new Random();
        int randomNum = startOfRange + rand.nextInt((endOfRange - startOfRange) / 2) * 2;

        return String.valueOf(randomNum);
    }

    private String doubleNumber() {
        int startOfRange = 1;
        int endOfRange = 20;

        Random rand = new Random();
        double randomNum = startOfRange + rand.nextDouble(endOfRange - startOfRange);


        return String.format("%.8f", randomNum);
    }
}
