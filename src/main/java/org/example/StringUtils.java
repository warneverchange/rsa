package org.example;

import java.math.BigInteger;
import java.util.stream.Stream;

public class StringUtils {
    public static Stream<BigInteger> convertToBigIntegerStream(String value) {
        return value.chars().mapToObj(BigInteger::valueOf);
    }

    public static String convertBigIntegerStreamToString(Stream<BigInteger> inputStream) {
        return inputStream
                .map(bigIntegerCode -> {
                    try  {
                        return bigIntegerCode.intValueExact();
                    } catch (ArithmeticException ex) {
                        throw new IllegalArgumentException("Input stream must contains valid char codes");
                    }
                })
                .filter(Character::isValidCodePoint)
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString();
    }
}
