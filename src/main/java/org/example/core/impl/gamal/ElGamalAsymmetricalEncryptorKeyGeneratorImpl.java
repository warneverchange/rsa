package org.example.core.impl.gamal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.example.core.AsymmetricalEncryptorKeyGenerator;
import org.example.core.utils.BigIntegerUtils;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class ElGamalAsymmetricalEncryptorKeyGeneratorImpl implements AsymmetricalEncryptorKeyGenerator<Triple<BigInteger, BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> {
    private final Random random;
    private Triple<BigInteger, BigInteger, BigInteger> publicKey;
    private Pair<BigInteger, BigInteger> privateKey;

    private static final int DEFAULT_BIT_LENGTH = 20;

    public ElGamalAsymmetricalEncryptorKeyGeneratorImpl(Random random) {
        Objects.requireNonNull(random);
        this.random = random;
        generate(DEFAULT_BIT_LENGTH);
    }

    public ElGamalAsymmetricalEncryptorKeyGeneratorImpl(int bitLength, Random random) {
        Objects.requireNonNull(random);
        this.random = random;
        generate(bitLength > 0 ? bitLength : DEFAULT_BIT_LENGTH);
    }

    @Override
    public void generate(int bitLength) {
        final BigInteger P = BigInteger.probablePrime(bitLength, random);
        final BigInteger G = BigIntegerUtils.getPrimitiveRoot(P);
        final BigInteger X = new BigInteger(P.subtract(BigInteger.ONE).bitLength(), random);
        this.privateKey = ImmutablePair.of(X, P);
        this.publicKey = ImmutableTriple.of(G.modPow(X, P), G, P);
    }

    @Override
    public Pair<BigInteger, BigInteger> getPrivateKey() {
        return privateKey;
    }

    @Override
    public Triple<BigInteger, BigInteger, BigInteger> getPublicKey() {
        return publicKey;
    }
}
