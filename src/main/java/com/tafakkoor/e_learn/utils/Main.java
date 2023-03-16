package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.enums.Progress;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.services.ImageService;
import com.tafakkoor.e_learn.services.ScheduleService;
import java.math.BigInteger;

import java.math.BigInteger;

public class Main {
    private final AuthUserRepository authUserRepository;

    public Main(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public static void main(String[] args) {

    }
        public static BigInteger convertToBigInteger(String number) {
            try {
                double doubleValue = Double.parseDouble(number);
                long longValue = (long) doubleValue;
                if (doubleValue == (double) longValue) {
                    return BigInteger.valueOf(longValue);
                } else {
                    String[] parts = number.split("E");
                    BigInteger coefficient = new BigInteger(parts[0].replace(".", ""));
                    BigInteger exponent = new BigInteger(parts[1]);
                    return coefficient.multiply(BigInteger.TEN.pow(exponent.intValue()));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid input string. Must be in scientific notation.");
            }
        }


}
