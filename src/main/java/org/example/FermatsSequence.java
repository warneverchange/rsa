package org.example;

import java.math.BigInteger;
import java.util.Iterator;

public class FermatsSequence implements Iterator<BigInteger> {
    private int currentStep;
    private final BigInteger borderValue;


    private FermatsSequence(int startNumber, BigInteger borderValue) {
        this.currentStep = startNumber;
        this.borderValue = borderValue;
    }

    public static FermatsSequence of(int startNumber, BigInteger borderValue) {
        if (startNumber < 0) {
            throw new IllegalArgumentException("Argument must be positive or equals zero");
        }
        return new FermatsSequence(startNumber, borderValue);
    }

    private BigInteger calculateNextValue(int number) {
        return BigIntegerUtils.pow(
                        BigInteger.TWO,
                        BigInteger.valueOf(2)
                                .pow(number)
                )
                .add(BigInteger.ONE);
    }

    @Override
    public boolean hasNext() {
        int compareResult = calculateNextValue(currentStep + 1)
                .compareTo(borderValue);
        return compareResult < 0;
    }

    @Override
    public BigInteger next() {
        return calculateNextValue(currentStep++);
    }
}
