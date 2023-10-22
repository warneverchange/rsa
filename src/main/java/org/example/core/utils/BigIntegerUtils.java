package org.example.core.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BigIntegerUtils {
    public static BigInteger pow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0)) result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    public static BigInteger getPrimitiveRoot(BigInteger module) {
        List<BigInteger> fact = new ArrayList<>();
        BigInteger phi = module.subtract(BigInteger.ONE);
        BigInteger n = phi.add(BigInteger.ZERO);
        for(BigInteger i = BigInteger.TWO; i.multiply(i).compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
            if (n.mod(i).compareTo(BigInteger.ZERO) == 0) {
                fact.add(i);
                while (n.mod(i).compareTo(BigInteger.ZERO) == 0) {
                    n = n.divide(i);
                }
            }
        }
        if (n.compareTo(BigInteger.ONE) > 0) {
            fact.add(n);
        }
        for(BigInteger res = BigInteger.TWO; res.compareTo(module) < 0; res = res.add(BigInteger.ONE)) {
            boolean ok = true;
            for(int i = 0; i < fact.size() && ok; i++) {
                ok = res.modPow(phi.divide(fact.get(i)), module).compareTo(BigInteger.ONE) != 0;
            }
            if (ok) {
                return res;
            }
        }
        return BigInteger.ONE.negate();
    }

    public static BigInteger generateSmallestRandomCoprime(BigInteger value, Random random) {
        var primitiveRoot = getPrimitiveRoot(value);
        var randomPower = new BigInteger(value.subtract(BigInteger.ONE).bitLength(), random);
        return primitiveRoot.modPow(randomPower, value);
    }
}
