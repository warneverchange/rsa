package org.example;


import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class AsymmetricalEncryptorKeyGeneratorImpl implements AsymmetricalEncryptorKeyGenerator<Pair<BigInteger, BigInteger>> {
    public final int DEFAULT_BIT_LENGTH = 20;
    private Pair<BigInteger, BigInteger> privateKey;
    private Pair<BigInteger, BigInteger> publicKey;
    private final Random random;

    public AsymmetricalEncryptorKeyGeneratorImpl(Random random) {
        this.random = random;
        generate(DEFAULT_BIT_LENGTH);
    }

    public AsymmetricalEncryptorKeyGeneratorImpl(int bitLength, Random random) {
        Objects.requireNonNull(random);
        this.random = random;
        generate(bitLength <= 0 ? DEFAULT_BIT_LENGTH : bitLength);
    }

    public void generate(int bitLength) {
        var P = BigInteger.probablePrime(bitLength, random);
        var Q = BigInteger.probablePrime(bitLength, random);
        var N = P.multiply(Q);
        var E = KeyGeneratorUtils.GenerateOpenExponent(P, Q);
        publicKey = new MutablePair<>(E, N);
        privateKey = new MutablePair<>(E.modInverse(P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE))), N);
    }

    @Override
    public Pair<BigInteger, BigInteger> getPrivateKey() {
        return Objects.requireNonNull(privateKey);
    }

    @Override
    public Pair<BigInteger, BigInteger> getPublicKey() {
        return Objects.requireNonNull(publicKey);
    }

}
