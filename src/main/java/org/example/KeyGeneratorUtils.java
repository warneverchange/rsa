package org.example;

import java.math.BigInteger;

public class KeyGeneratorUtils {
    public static BigInteger GenerateOpenExponent(BigInteger P, BigInteger Q) {
        var M = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));
        var sequence = FermatsSequence.of(0, M);
        while (sequence.hasNext()) {
            var currentValue = sequence.next();
            if (currentValue.gcd(M).equals(BigInteger.ONE)) {
                return currentValue;
            }
        }
        return BigInteger.valueOf(-1L);
    }
}
